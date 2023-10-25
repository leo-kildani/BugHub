package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ProjectProfileController implements BugHubController {

    @FXML
    private Label projectTitle;

    @FXML
    private Label projectDate;

    @FXML
    private Button openTicketButton;

    @FXML
    private Button deleteTicketButton;

    private Project project;

    private BugHubDataModel model;

    @Override
    public void loadModel(BugHubDataModel model) {
        this.model = model;
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("MAIN_MENU"));
    }

    public void switchToProjectForm(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("PROJECT_FORM"));
    }

    public void setProject(Project project) {
        this.project = project;
        this.projectTitle.setText(project.getTitle());
        this.projectDate.setText("Date Created: " + project.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    }

    public Project getProject() {
        return project;
    }
}
