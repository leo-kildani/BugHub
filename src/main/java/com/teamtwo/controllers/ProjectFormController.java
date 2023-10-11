package com.teamtwo.controllers;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.entity.Project;
import com.teamtwo.util.IdGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Objects;

public class ProjectFormController
{
    @FXML
    private TextField projectNameField;
    @FXML
    private DatePicker startingDate;
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private LocalDate todaysDate;

    private BugHubDataAccess dao;

    private Parent mainMenu;
    
    @FXML
    public void initialize() {
        todaysDate = LocalDate.now();
        startingDate.setValue(todaysDate);    
    }

    public void loadDao(BugHubDataAccess dao) {
        this.dao = dao;
    }

    public void loadPages(Parent mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void clearForm(ActionEvent e) {
        projectNameField.clear();
        startingDate.setValue(null);
        descriptionArea.clear();
    }

    public void saveForm(ActionEvent e) {
        int id = IdGenerator.generateId();
        while (Objects.nonNull(dao.getProject(id)))
            id = IdGenerator.generateId();
        String projectName = projectNameField.getText();
        String projectDescription = descriptionArea.getText();
        LocalDate startDate = startingDate.getValue();
        dao.addProject(new Project(id, projectName, projectDescription, startDate));
        clearForm(e);
    }

    public void cancelForm(ActionEvent e) {
        Scene scene = ((Node)e.getSource()).getScene();
        scene.setRoot(mainMenu);
    }
}