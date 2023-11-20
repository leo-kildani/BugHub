package com.teamtwo.controllers;

import com.teamtwo.entity.Project;
import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.util.IdGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class ProjectFormController extends AbstractBugHubController {
    @FXML
    private TextField projectNameField;
    @FXML
    private DatePicker startingDate;
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private LocalDate todaysDate;

    private boolean isEditable;

    private Integer editProjectId;
    
    private int nameCharLimit = 64;
    private int descriptionCharLimit =256;
    
    @FXML
    public void initialize() {
        todaysDate = LocalDate.now();
        startingDate.setValue(todaysDate);
        descriptionArea.setWrapText(true);
        isEditable = false;
        editProjectId = null;
    }

    public void loadModel(BugHubDataModel model) {
        this.model = model;
    }

    public void clearForm() {
        projectNameField.clear();
        startingDate.setValue(null);
        descriptionArea.clear();
    }

    private void clearEditingForm() {
        clearForm();
        isEditable = false;
        editProjectId = null;
    }

    /*
     * This method will save all the contents given by the user for the project they want to save.
     * If there is a missing name or description, the user will be prompted to fill in those fields.
     */
    public void saveForm(ActionEvent e) {
        String projectName = projectNameField.getText();
        String projectDescription = descriptionArea.getText();
        LocalDate startDate = startingDate.getValue();
        
        // Missing credentials alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        
        
        if(projectName.isEmpty() || projectDescription.isEmpty() || startDate == null) {
        	alert.setTitle("Error");
            alert.setHeaderText("Missing Credentials");
            alert.setContentText("Do not leave any textfields empty before saving");
        	alert.showAndWait();  
        }
        else if (projectName.length() > nameCharLimit|| projectDescription.length() > descriptionCharLimit){
        	alert.setTitle("Character Limit Exceeded");
            alert.setHeaderText("Character limits exceeded.");
            alert.setContentText("Name: " + nameCharLimit + " characters, Description: " + descriptionCharLimit + " characters.");
            alert.showAndWait();
        }
        else {
            Project p;
            if (isEditable) {
                p = model.getService().getProject(editProjectId);
                p.setTitle(projectName);
                p.setDescr(projectDescription);
                p.setDatetime(startDate.atStartOfDay());

                model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class).updateProjectCell(p);
                switchToProjectProfile(e, p);
            } else {
                int id = IdGenerator.generateId();
                while (model.getService().getProject(id) != null)
                    id = IdGenerator.generateId();

                p = new Project(id, projectName, projectDescription, startDate.atStartOfDay());

                model.getService().addProject(p);

                model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class)
                        .getProjectTable()
                        .getItems()
                        .add(p);
            }
            model.getController("TICKET_FORM", TicketFormController.class).updateProjectList();
            clearEditingForm();
        }
    }

    public void setProjectToEdit(Project p) {
        isEditable = true;
        projectNameField.setText(p.getTitle());
        startingDate.setValue(p.getDatetime().toLocalDate());
        descriptionArea.setText(p.getDescr());
        editProjectId = p.getId();
    }
}