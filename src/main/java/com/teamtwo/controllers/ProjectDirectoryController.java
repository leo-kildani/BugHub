package com.teamtwo.controllers;

import com.teamtwo.entity.Project;
import com.teamtwo.data_model.BugHubDataModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectDirectoryController extends AbstractBugHubController implements Initializable {

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
        this.projectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.projectTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.projectDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.projectTicketCount.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getTickets().size()).asObject());
    }

    public void loadModel(BugHubDataModel model) {
        this.model = model;

        this.projectTable.setItems(FXCollections.observableList(this.model.getDao().getProjects()));
        this.projectTable.setPlaceholder(new Label("Click Create Project to get started!"));
    }

    public void updateProjectCell(Project project) {
        int idx = 0;
        while (project.getId() != projectTable.getItems().get(idx).getId()) {
            idx++;
        }
        projectTable.getItems().set(idx, project);
    }

    @FXML
    public void deleteProject(ActionEvent event) {
        Project project = projectTable.getSelectionModel().getSelectedItem();
        
        if (project != null) {
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Delete Project");
            confirmation.setHeaderText("Confirm Deletion");
            confirmation.setContentText("Are you sure you want to delete this project?");
            
            // buttons
            ButtonType confirmButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("No", ButtonData.CANCEL_CLOSE);
            confirmation.getButtonTypes().setAll(confirmButton, cancelButton);
            
            // confirmation dialogue
            Optional<ButtonType> result = confirmation.showAndWait();
            
            if (result.isPresent() && result.get() == confirmButton) {
                // user confirm deletion
                model.getDao().deleteProject(project.getId());
                projectTable.getItems().remove(project);
            }
        }
    }

    @FXML
    public void openProjectProfile(ActionEvent event) throws IOException {
        Project project = projectTable.getSelectionModel().getSelectedItem();
        if (project != null) {
            ProjectProfileController controller = model.getController("PROJECT_PROFILE", ProjectProfileController.class);
            controller.setProject(project);
            switchToProjectProfile(event);
        }
    }

    public TableView<Project> getProjectTable() {
        return projectTable;
    }

}

