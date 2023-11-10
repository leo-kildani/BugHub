package com.teamtwo.controllers;

import com.teamtwo.entity.Project;
import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Ticket;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityDirectoryController extends AbstractBugHubController implements Initializable {

    @FXML
    private TableView<Project> projectTable;

    @FXML
    private TableColumn<Project, Integer> projectId;

    @FXML
    private TableColumn<Project, String> projectTitle;

    @FXML
    private TableColumn<Project, String> projectDate;

    @FXML
    private TableColumn<Project, Integer> projectTicketCount;

    @FXML
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, Integer> ticketId;

    @FXML
    private TableColumn<Ticket, String> ticketTitle;

    @FXML
    private TableColumn<Ticket, String> ticketDate;

    @FXML
    private TableColumn<Ticket, Integer> ticketProjectId;

    @FXML
    private TableColumn<Ticket, Integer> ticketCommentCount;
    
    @FXML
    private TextField entitySearchField;

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
        projectId.setCellValueFactory(new PropertyValueFactory<>("id"));
        projectTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        projectDate.setCellValueFactory(p -> new SimpleStringProperty(
                p.getValue().getDatetime().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
        projectTicketCount.setCellValueFactory(p -> new SimpleIntegerProperty(
        		model
        		.getService()
        		.getTicketsByProjectId(p.getValue().getId()).size())
        		.asObject());

        ticketTable.setRowFactory(tikTable -> {
            final TableRow<Ticket> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                final int index = row.getIndex();
                if (index >= 0 && index < ticketTable.getItems().size() && ticketTable.getSelectionModel().isSelected(index)) {
                    ticketTable.getSelectionModel().clearSelection();
                    mouseEvent.consume();
                }
            });
            return row;
        });

        ticketId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        ticketDate.setCellValueFactory(t -> new SimpleStringProperty(
                t.getValue().getDatetime().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));
        ticketProjectId.setCellValueFactory(new PropertyValueFactory<>("projectId"));
        ticketCommentCount.setCellValueFactory(t -> new SimpleIntegerProperty(
                model
                        .getService()
                        .getCommentsByTicketId(t.getValue().getId()).size())
                .asObject());
        
        this.entitySearchField.textProperty().addListener((obs, oldSearch, newSearch) -> {
        	if (newSearch.isEmpty()) {
        		projectTable.setItems(FXCollections.observableList(model.getService().getProjects()));
                ticketTable.setItems(FXCollections.observableList(model.getService().getTickets()));
        	} else {
                List<Project> foundProjects = model.getService().getProjectsByTitle(newSearch);
                List<Ticket> foundTickets = model.getService().getTicketsByTitle(newSearch);

//              add tickets of each found project
                List<Ticket> foundTicketsByProjectTitle = new ArrayList<>();
                foundProjects.forEach(p -> foundTicketsByProjectTitle.addAll(model.getService().getTicketsByProjectId(p.getId())));

                foundTickets = Stream.of(foundTickets, foundTicketsByProjectTitle)
                                .flatMap(Collection::stream)
                                        .distinct()
                                                .collect(Collectors.toList());


				projectTable.setItems(FXCollections.observableList(foundProjects));
                ticketTable.setItems(FXCollections.observableList(foundTickets));
			}
        });
    }

    public void loadModel(BugHubDataModel model) {
        this.model = model;
        this.projectTable.setItems(FXCollections.observableList(model.getService().getProjects()));
        this.ticketTable.setItems(FXCollections.observableList(model.getService().getTickets()));
        this.projectTable.setPlaceholder(new Label("Click Create Project to get started!"));
        this.ticketTable.setPlaceholder(new Label("Click Create Ticket to get started!"));
    }

    public void updateProjectCell(Project project) {
        int idx = 0;
        while (project.getId() != projectTable.getItems().get(idx).getId()) {
            idx++;
        }
        projectTable.getItems().set(idx, project);
    }

    public void updateTicketCell(Ticket ticket) {
        int idx = 0;
        while (ticket.getId() != ticketTable.getItems().get(idx).getId()) {
            idx++;
        }
        ticketTable.getItems().set(idx, ticket);
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
                model.getService().deleteProject(project.getId());
                projectTable.getItems().remove(project);
                ticketTable.setItems(FXCollections.observableList(model.getService().getTickets()));
                model.getController("TICKET_FORM", TicketFormController.class).updateProjectList();
            }
        }
    }

    @FXML
    public void deleteTicket(ActionEvent event) {
        Ticket ticket = ticketTable.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            model.getService().deleteTicket(ticket.getId());
            updateProjectCell(model.getService().getProject(ticket.getProjectId()));
            ticketTable.getItems().remove(ticket);
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

    @FXML
    public void openTicketProfile(ActionEvent event) throws IOException {
        Ticket ticket = ticketTable.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            model.getController("TICKET_PROFILE", TicketProfileController.class).setTicket(ticket);
            switchToTicketProfile(event);
        }
    }

    public TableView<Project> getProjectTable() {
        return projectTable;
    }

    public TableView<Ticket> getTicketTable() {
        return ticketTable;
    }
}

