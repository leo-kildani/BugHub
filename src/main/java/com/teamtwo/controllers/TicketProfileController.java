package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Ticket;
import com.teamtwo.util.IdGenerator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class TicketProfileController extends PageSwitchController implements Initializable {
	
	@FXML
	private Label ticketTitle;
	
	@FXML
	private Label ticketDate;
	
	@FXML
	private Label characterCount;
	
	@FXML
	private TextArea ticketDescr;

	@FXML
	private Label savingDescrHelpLabel;
	
	private Ticket ticket;
	
	@FXML
	private ListView<GridPane> commentList;

    @FXML
    private TextArea descriptionArea;
	
	public void loadModel(BugHubDataModel model) {
		this.model = model;
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
		this.ticketTitle.setText(ticket.getTitle());
		this.ticketDescr.setText(ticket.getDescr());
		this.ticketDate.setText("Date created: " + ticket.getDatetime().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		this.characterCount.setText(ticket.getDescr().length() + "/256");
		
		this.commentList.getItems().clear();
		for(Comment c : model.getService().getCommentsByTicketId(ticket.getId())) {
			commentList.getItems().add(createCommentListCell(c));
		}
	}
	
    public void clearForm(ActionEvent event) {
        descriptionArea.clear();
    }

	public void deleteComment() {
		GridPane commentCell = commentList.getSelectionModel().getSelectedItem();
		if (commentCell != null) {
			Label commentId = (Label) commentCell.getChildren().get(0);
			model.getService().deleteComment(Integer.parseInt(commentId.getText()));
			commentList.getItems().remove(commentCell);
			model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class).updateTicketCell(ticket);
		}
	}
	
	public void saveComment(ActionEvent event) {
        String commentDescription = descriptionArea.getText();
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Credentials");
        alert.setContentText("Do not leave any textfields empty before saving");
        
        if(commentDescription.isEmpty()) {
        	alert.showAndWait();
        } else {
        	 int id = IdGenerator.generateId();
             while (model.getService().getComment(id) != null)
                 id = IdGenerator.generateId();
            Comment c = new Comment(id, commentDescription, this.ticket.getId());
            model.getService().addComment(c);
            commentList.getItems().add(createCommentListCell(c));
			model.getController("ENTITY_DIRECTORY", EntityDirectoryController.class).updateTicketCell(ticket);
			clearForm(event);
        }
	}
	
	private GridPane createCommentListCell(Comment comment) {
		GridPane listCell = new GridPane();
		listCell.setMaxSize(commentList.getPrefWidth(), Math.floor(commentList.getPrefHeight() / 2));
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
		listCell.getRowConstraints().addAll(hiddenData, heavyData, regData);

		// make/add labels in order
		Label ticketId = new Label(String.valueOf(comment.getId()));
		ticketId.setVisible(false);
		listCell.add(ticketId, 0, 0);
		Label ticketDescr = new Label(comment.getDescr());
		ticketDescr.setWrapText(true);
		listCell.add(ticketDescr, 0, 1);
		Label ticketDate = new Label(comment.getDatetime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
		GridPane.setHalignment(ticketDate, HPos.RIGHT);
		listCell.add(ticketDate, 0, 2);

		return listCell;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		descriptionArea.setWrapText(true);
		descriptionArea.setEditable(false);
		commentList.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
			GridPane selectedCell = commentList.getSelectionModel().getSelectedItem();
			if (selectedCell != null) {
				commentList.getSelectionModel().clearSelection();
				mouseEvent.consume();
			}
		});
		Label placeholder = new Label("Create new comment under \"New Comment\" section!");
		placeholder.setWrapText(true);
		commentList.setPlaceholder(placeholder);
	}

	public void editTicket(ActionEvent event) {
		switchToTicketForm(event, ticket);
	}
}
