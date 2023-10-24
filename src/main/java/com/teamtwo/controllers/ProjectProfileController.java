package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    public void setProject(Project project) {
        this.project = project;
        this.projectTitle.setText(project.getTitle());
        this.projectDate.setText("Date Created: " + project.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
    }

    public Project getProject() {
        return project;
    }
}
