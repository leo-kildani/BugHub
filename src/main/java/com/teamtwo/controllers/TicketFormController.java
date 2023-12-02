package com.teamtwo.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.AbstractEntry;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;
import com.teamtwo.util.IdGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TicketFormController extends PageSwitchController implements Initializable {
    @FXML
    private ChoiceBox<String> projectList;
	
	@FXML
    private TextField ticketNameField;
    
    @FXML
    private TextArea descriptionArea;

    private boolean isEditable;

    private Integer editTicketId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descriptionArea.setWrapText(true);
    }

    public void loadModel(BugHubDataModel model) {
        this.model = model;
        List<String> projetTitleList = model.getService().getProjects().stream()
                .map(AbstractEntry::getTitle).sorted().collect(Collectors.toList());
        ObservableList<String> projectCol = FXCollections.observableArrayList();
        projectCol.addAll(projetTitleList);
        projectList.setItems(projectCol);
    }

    @Override
    public void switchToMainMenu(ActionEvent e) {
        clearEditingForm();
        super.switchToMainMenu(e);
    }

    public void clearForm() {
    	ticketNameField.clear();
        projectList.setValue(null);
        descriptionArea.clear();
    }

    public void clearEditingForm() {
        clearForm();
        isEditable = false;
        editTicketId = null;
    }

    /*
     * This method will save all the contents given by the user for the project they want to save.
     * If there is a missing name or description, the user will be prompted to fill in those fields.
     */
    public void saveForm(ActionEvent event) {
    	String projectTitle = projectList.getSelectionModel().getSelectedItem();
        Project project = model.getService().getProjectsByTitle(projectTitle).get(0);

        String ticketName = ticketNameField.getText();
        String ticketDescription = descriptionArea.getText();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Credentials");
        alert.setContentText("Do not leave any textfields empty before saving");

        if(project == null || ticketName.isEmpty() || ticketDescription.isEmpty()) {
        	alert.showAndWait();
        } else {
            Ticket t;
            EntityDirectoryController controller = model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class);
            if (isEditable) {
                t = model.getService().getTicket(editTicketId);
                t.setTitle(ticketName);
                t.setDescr(ticketDescription);
                t.setProjectId(project.getId());
                t.setDatetime(LocalDateTime.now());
                controller.updateTicketCell(t);
                switchToTicketProfile(event, t);
            } else {
                int id = IdGenerator.generateId();
                while (model.getService().getTicket(id) != null)
                    id = IdGenerator.generateId();
                t = new Ticket(id, ticketName, ticketDescription, project.getId());
                model.getService().addTicket(t);
                controller.getTicketTable().getItems().add(t);
            }
            controller.updateProjectCell(project);
            clearEditingForm();
        }
    }

    public void updateProjectList() {
        List<String> projetTitleList = model.getService().getProjects().stream()
                .map(AbstractEntry::getTitle).sorted().collect(Collectors.toList());

        ObservableList<String> projectCol = FXCollections.observableArrayList();
        projectCol.addAll(projetTitleList);
        projectList.setItems(projectCol);
    }

    public void setTicketToEdit(Ticket t) {
        isEditable = true;
        editTicketId = t.getId();
        ticketNameField.setText(t.getTitle());
        descriptionArea.setText(t.getDescr());
        projectList.getSelectionModel().select(model
                .getService()
                .getProject(t.getProjectId())
                .getTitle());
    }
}