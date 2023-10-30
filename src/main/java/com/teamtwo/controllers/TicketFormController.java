package com.teamtwo.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TicketFormController extends AbstractBugHubController implements Initializable {
    @FXML
    private ChoiceBox<String> projectList;
	
	@FXML
    private TextField ticketNameField;
    
    @FXML
    private TextArea descriptionArea;
    
    public void loadModel(BugHubDataModel model) {
        this.model = model;
        List<String> projetTitleList = model.getDao().getProjects().stream()
                .map(AbstractEntry::getTitle).sorted().collect(Collectors.toList());

        ObservableList<String> projectCol = FXCollections.observableArrayList();
        projectCol.addAll(projetTitleList);
        projectList.setItems(projectCol);
    }

    public void clearForm(ActionEvent e) {
    	ticketNameField.clear();
        projectList.setValue(null);
        descriptionArea.clear();
    }

    /*
     * This method will save all the contents given by the user for the project they want to save.
     * If there is a missing name or description, the user will be prompted to fill in those fields.
     */
    public void saveForm(ActionEvent e) {
    	String projectTitle = projectList.getSelectionModel().getSelectedItem();
        Project project = model.getDao().getProjects().stream()
                .filter(p -> projectTitle.equals(p.getTitle())).findAny().orElse(null);

        String ticketName = ticketNameField.getText();
        String ticketDescription = descriptionArea.getText();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Credentials");
        alert.setContentText("Do not leave any textfields empty before saving");
        
        if(project == null || ticketName.isEmpty() || ticketDescription.isEmpty()) {
        	alert.showAndWait();
        } else {
            int id = IdGenerator.generateId();
            while (Objects.nonNull(model.getDao().getTicket(project,id)))
                id = IdGenerator.generateId();
            Ticket ticket = new Ticket(id, ticketName, ticketDescription, project.getId());
            model.getDao().addTicket(project, ticket);
            model.getController("PROJECT_DIRECTORY", ProjectDirectoryController.class).updateProjectCell(project);
            clearForm(e);
         }
    }

    public void updateProjectList() {
        List<String> projetTitleList = model.getDao().getProjects().stream()
                .map(AbstractEntry::getTitle).sorted().collect(Collectors.toList());

        ObservableList<String> projectCol = FXCollections.observableArrayList();
        projectCol.addAll(projetTitleList);
        projectList.setItems(projectCol);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descriptionArea.setWrapText(true);
    }
}