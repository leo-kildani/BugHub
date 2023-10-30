package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.io.IOException;

public abstract class AbstractBugHubController implements BugHubController {

    protected BugHubDataModel model;

    @Override
    public abstract void loadModel(BugHubDataModel model);

    @FXML
    public void switchToProjectForm(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("PROJECT_FORM"));
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("MAIN_MENU"));
    }

    @FXML
    public void switchToProjectDirectory(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("PROJECT_DIRECTORY"));
    }

    @FXML
    public void switchToTicketProfile(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("TICKET_PROFILE"));
    }

    @FXML
    public void switchToTicketForm(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("TICKET_FORM"));
    }

    @FXML
    public void switchToProjectProfile(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("PROJECT_PROFILE"));
    }
}
