package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;

public abstract class PageSwitchController implements BugHubController {

    protected BugHubDataModel model;

    @Override
    public abstract void loadModel(BugHubDataModel model);

    @FXML
    public void switchToProjectForm(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("PROJECT_FORM"));
    }
    @FXML
    public void switchToProjectForm(ActionEvent event, Project project) {
        model.getController("PROJECT_FORM", ProjectFormController.class).setProjectToEdit(project);
        switchToProjectForm(event);
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("MAIN_MENU"));
    }

    @FXML
    public void switchToEntityDirectory(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("ENTITY_DIRECTORY"));
    }

    @FXML
    public void switchToTicketProfile(ActionEvent event, Ticket t) {
        model.getController("TICKET_PROFILE", TicketProfileController.class).setTicket(t);
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("TICKET_PROFILE"));
    }

    @FXML
    public void switchToTicketForm(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("TICKET_FORM"));
    }

    @FXML
    public void switchToTicketForm(ActionEvent event, Ticket ticket) {
        model.getController("TICKET_FORM", TicketFormController.class).setTicketToEdit(ticket);
        switchToTicketForm(event);
    }

    @FXML
    public void switchToProjectProfile(ActionEvent event, Project p) {
        model.getController("PROJECT_PROFILE", ProjectProfileController.class).setProject(p);
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("PROJECT_PROFILE"));
    }
}
