package com.teamtwo.controllers;

import com.teamtwo.entity.Project;
import com.teamtwo.data_model.BugHubDataModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProjectDirectoryController implements Initializable, BugHubController{

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

    private BugHubDataModel model;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        projectTable.setRowFactory(projTable -> {
            final TableRow<Project> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                final int index = row.getIndex();
                if (index >= 0 && index < projectTable.getItems().size() && projectTable.getSelectionModel().isSelected(index)) {
                    projectTable.getSelectionModel().clearSelection();
                    mouseEvent.consume();
                }
            });
            return row;
        });
    }

    public void loadModel(BugHubDataModel model) {
        this.model = model;
        this.projectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.projectTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.projectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.projectTicketCount.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getTickets().size()).asObject());

        this.projectTable.setItems(FXCollections.observableList(this.model.getDao().getProjects()));
        this.projectTable.setPlaceholder(new Label("Click Create Project to get started!"));
    }

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
    public void deleteProject(ActionEvent event) {
        Project project = projectTable.getSelectionModel().getSelectedItem();
        if (project != null) {
            model.getDao().deleteProject(project.getId());
            projectTable.getItems().remove(project);
        }
    }

    public TableView<Project> getProjectTable() {
        return projectTable;
    }

}

