package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;
import com.teamtwo.util.StringShortener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ProjectProfileController extends PageSwitchController implements Initializable {

    @FXML
    private Label projectTitle;

    @FXML
    private Label projectDate;

    @FXML
    private Label characterCount;

    @FXML
    private TextArea projectDescr;

    @FXML
    private ListView<GridPane> ticketList;

    private Project project;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketList.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            GridPane selectedCell = ticketList.getSelectionModel().getSelectedItem();
            if (selectedCell != null) {
                ticketList.getSelectionModel().clearSelection();
                mouseEvent.consume();
            }
        });
        Label placeholder = new Label("Click \"Create Ticket\" to get started!");
        placeholder.setWrapText(true);
        ticketList.setPlaceholder(placeholder);
    }

    @Override
    public void loadModel(BugHubDataModel model) {
        this.model = model;
    }

    @FXML
    public void editProject(ActionEvent event) {
        switchToProjectForm(event, project);
    }

    @FXML
    public void openTicket(ActionEvent event) {
        GridPane ticketCell = ticketList.getSelectionModel().getSelectedItem();
        if (ticketCell != null) {
            Label ticketId = (Label) ticketCell.getChildren().get(0);
            Ticket ticket = model.getService().getTicket(Integer.parseInt(ticketId.getText()));
            switchToTicketProfile(event, ticket);
        }
    }

    @FXML
    public void deleteTicket() {
        GridPane ticketCell = ticketList.getSelectionModel().getSelectedItem();
        if (ticketCell != null) {
            Label ticketId = (Label) ticketCell.getChildren().get(0);
            model.getService().deleteTicket(Integer.parseInt(ticketId.getText()));
            ticketList.getItems().remove(ticketCell);
            model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class).updateProjectCell(project);
        }
    }

    public void setProject(Project project) {
        this.project = project;
        this.projectTitle.setText(project.getTitle());
        this.projectDate.setText("Date Created: " + project.getDatetime().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        this.projectDescr.setText(project.getDescr());
        this.characterCount.setText(project.getDescr().length() + "/256");

        this.ticketList.getItems().clear();
        for (Ticket t: model.getService().getTicketsByProjectId(project.getId())) {
            ticketList.getItems().add(createTicketListCell(t));
        }
    }

    private GridPane createTicketListCell(Ticket ticket) {
        GridPane listCell = new GridPane();
        listCell.setMaxSize(ticketList.getPrefWidth(), Math.floor(ticketList.getPrefHeight() / 3));
        listCell.setPadding(new Insets(0, 15, 0, 0));

        // set cell constraints
        ColumnConstraints colConstraint = new ColumnConstraints();
        colConstraint.setPercentWidth(100);
        RowConstraints regData = new RowConstraints();
        regData.setVgrow(Priority.NEVER);
        regData.setValignment(VPos.CENTER);
        RowConstraints heavyData = new RowConstraints();
        heavyData.setVgrow(Priority.ALWAYS);
        heavyData.setValignment(VPos.CENTER);
        RowConstraints hiddenData = new RowConstraints(0);

        listCell.getColumnConstraints().add(colConstraint);
        listCell.getRowConstraints().addAll(hiddenData, regData, heavyData, regData);

        // make/add labels in order
        Label ticketId = new Label(String.valueOf(ticket.getId()));
        ticketId.setVisible(false);
        listCell.add(ticketId, 0, 0);
        Label ticketTitle = new Label(ticket.getTitle());
        listCell.add(ticketTitle, 0, 1);
        Label ticketDescr = new Label(StringShortener.shortenString(ticket.getDescr(), 70));
        ticketDescr.setWrapText(true);
        listCell.add(ticketDescr, 0, 2);
        Label ticketDate = new Label(ticket.getDatetime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        GridPane.setHalignment(ticketDate, HPos.RIGHT);
        listCell.add(ticketDate, 0, 3);

        return listCell;
    }
}
