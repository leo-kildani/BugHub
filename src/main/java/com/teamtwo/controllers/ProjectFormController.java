package com.teamtwo.controllers;

import com.teamtwo.entity.Project;
import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.util.IdGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class ProjectFormController implements BugHubController{
    @FXML
    private TextField projectNameField;
    @FXML
    private DatePicker startingDate;
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private LocalDate todaysDate;

    private BugHubDataModel model;
    
    private int nameCharLimit =64;
    private int descriptionCharLimit =256;
    
    @FXML
    public void initialize() {
        todaysDate = LocalDate.now();
        startingDate.setValue(todaysDate);
    }

    public void loadModel(BugHubDataModel model) {
        this.model = model;
    }

    public void clearForm(ActionEvent e) {
        projectNameField.clear();
        startingDate.setValue(null);
        descriptionArea.clear();
    }

    /*
     * This method will save all the contents given by the user for the project they want to save.
     * If there is a missing name or description, the user will be prompted to fill in those fields.
     */
    public void saveForm(ActionEvent e) 
    {
    
        int id = IdGenerator.generateId();
        while (Objects.nonNull(model.getDao().getProject(id)))
            id = IdGenerator.generateId();
        String projectName = projectNameField.getText();
        String projectDescription = descriptionArea.getText();
        LocalDate startDate = startingDate.getValue();
        
        // Missing credentials alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        
        
        if(projectName.isEmpty() || projectDescription.isEmpty()) {
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
            Project p = new Project(id, projectName, projectDescription, startDate);

            model.getDao().addProject(p);

            model.getController("PROJECT_DIRECTORY", ProjectDirectoryController.class)
                    .getProjectTable()
                    .getItems()
                    .add(p);

            clearForm(e);
        }
        
    }

    	

    public void cancelForm(ActionEvent e) throws IOException {
        Scene scene = ((Node)e.getSource()).getScene();
        scene.setRoot(model.getNode("MAIN_MENU"));
    }
}