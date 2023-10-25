package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ProjectProfileController implements BugHubController, Initializable {

    @FXML
    private Label projectTitle;

    @FXML
    private Label projectDate;

    @FXML
    private Label characterCount;

    @FXML
    private TextArea projectDescr;

    @FXML
    private Label savingDescrHelpLabel;
    private Project project;

    private BugHubDataModel model;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        projectDescr.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                savingDescrHelpLabel.setVisible(true);
                projectDescr.setEditable(true);
            }
        });
        projectDescr.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && projectDescr.isEditable()) {
                savingDescrHelpLabel.setVisible(false);
                projectDescr.setEditable(false);
                characterCount.setText(projectDescr.getText().length() + "/256");
                project.setDescr(projectDescr.getText());
            }
        });
    }

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
        this.projectDescr.setText(project.getDescr());
        this.characterCount.setText(project.getDescr().length() + "/256");
    }

    public Project getProject() {
        return project;
    }
}
