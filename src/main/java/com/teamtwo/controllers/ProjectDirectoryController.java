package com.teamtwo.controllers;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.entity.Project;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class ProjectDirectoryController {

    @FXML
    private TableView<Project> projectTable;

    @FXML
    private TableColumn<Project, Integer> projectId;

    @FXML
    private TableColumn<Project, String> projectTitle;

    @FXML
    private TableColumn<Project, LocalDate> projectDate;

    @FXML
    private TableColumn<Project, Integer> projectTicketCount;

    private BugHubDataAccess dao;

    private Parent mainMenu, projectPage;

    public void loadDao(BugHubDataAccess dao) {
        this.dao = dao;
        this.projectId.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        this.projectTitle.setCellValueFactory(new PropertyValueFactory<Project, String>("title"));
        this.projectDate.setCellValueFactory(new PropertyValueFactory<Project, LocalDate>("date"));
        this.projectTicketCount.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getTickets().size()).asObject());

        this.projectTable.setItems(FXCollections.observableList(dao.getProjects()));
        this.projectTable.setPlaceholder(new Label("Click Create Project to get started!"));
    }

    public void loadPages(Parent mainMenu, Parent projectPage) {
        this.mainMenu = mainMenu;
        this.projectPage = projectPage;
    }

    @FXML
    public void switchToProjectForm(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(projectPage);
    }

    @FXML
    public void switchToMainMenu(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(mainMenu);
    }

    @FXML
    public void deleteProject(ActionEvent event) {
        Project project = projectTable.getSelectionModel().getSelectedItem();
        dao.deleteProject(project.getId());
        projectTable.getItems().remove(project);
    }
}

