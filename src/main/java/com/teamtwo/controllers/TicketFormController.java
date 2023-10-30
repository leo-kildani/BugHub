package com.teamtwo.controllers;

import java.io.IOException;
import java.util.Objects;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;
import com.teamtwo.util.IdGenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TicketFormController implements BugHubController{
    @FXML
    private ChoiceBox<Project> projectList;
	
	@FXML
    private TextField ticketNameField;
    
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private ChoiceBox<String> statusList;
    
    @FXML
    private ChoiceBox<String> priorityList;
    
    @FXML
    private TextField firstLine;
    
    @FXML
    private TextField lastLine;
    

    private BugHubDataModel model;
    
    public void initialize() {
        ObservableList<String> statusCol = FXCollections.observableArrayList();
        statusCol.addAll("New", "In progress", "On hold", "Closed");
        statusList.setItems(statusCol);
        
        ObservableList<String> priorityCol = FXCollections.observableArrayList();
        priorityCol.addAll("Low", "In Medium", "High", "Urgent");
        priorityList.setItems(priorityCol);
        
        //ObservableList<Project> projectCol = FXCollections.observableArrayList();
        //projectCol.addAll(model.getDao().getProjects());
        //projectList.setItems(projectCol);
    
    }
    
    public void loadModel(BugHubDataModel model) {
        this.model = model;
        
    }

    public void clearForm(ActionEvent e) {
    	ticketNameField.clear();
        projectList.setValue(null);
        statusList.setValue(null);
        priorityList.setValue(null);
        descriptionArea.clear();
        firstLine.clear();
        lastLine.clear();
    }

    /*
     * This method will save all the contents given by the user for the project they want to save.
     * If there is a missing name or description, the user will be prompted to fill in those fields.
     */
    public void saveForm(ActionEvent e) {
    	Project project = projectList.getSelectionModel().getSelectedItem();
    	int id = IdGenerator.generateId();
//        while (Objects.nonNull(model.getDao().getTicket(project,id)))
//            id = IdGenerator.generateId();
        String ticketName = ticketNameField.getText();
        String ticketDescription = descriptionArea.getText();
        
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Credentials");
        alert.setContentText("Do not leave any textfields empty before saving");
        
        if(project == null || ticketName.isEmpty() || ticketDescription.isEmpty()) {
        	alert.showAndWait();
        } else {
            Ticket ticket = new Ticket(id, ticketName, ticketDescription, project.getId());
            model.getDao().addTicket(project, ticket);
            clearForm(e);
         }
    }

    public void cancelForm(ActionEvent e) throws IOException {
        Scene scene = ((Node)e.getSource()).getScene();
        scene.setRoot(model.getNode("MAIN_MENU"));
    }
}