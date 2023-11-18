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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ProjectProfileController extends AbstractBugHubController implements Initializable {

    @FXML
    private Label projectTitle;

    @FXML
    private Label projectDate;

    @FXML
    private Label characterCount;

    @FXML
    private TextArea projectDescr;
    
    @FXML
    private TextField projTitleEditor;

    @FXML
    private Label savingDescrHelpLabel;

    @FXML
    private ListView<GridPane> ticketList;

    private Project project;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        projectDescr.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                savingDescrHelpLabel.setVisible(true);
                projectDescr.setEditable(true);
                mouseEvent.consume();
            }
        });
        projectDescr.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2 && projectDescr.isEditable()) {
                savingDescrHelpLabel.setVisible(false);
                projectDescr.setEditable(false);
                characterCount.setText(projectDescr.getText().length() + "/256");
                project.setDescr(projectDescr.getText());
                mouseEvent.consume();
            }
        });
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
    public void openTicket(ActionEvent event) throws IOException {
        GridPane ticketCell = ticketList.getSelectionModel().getSelectedItem();
        if (ticketCell != null) {
            Label ticketId = (Label) ticketCell.getChildren().get(0);
            Ticket ticket = model.getService().getTicket(Integer.parseInt(ticketId.getText()));
            model.getController("TICKET_PROFILE", TicketProfileController.class).setTicket(ticket);
            switchToTicketProfile(event);
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
    
    @FXML
    private void editTitle() {
    	if(projTitleEditor != null) {
    		project.setTitle(projTitleEditor.getText());
        	projTitleEditor.clear();
        	projectTitle.setText(project.getTitle());
        	model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class).updateProjectCell(this.project);
    	}
    }
}