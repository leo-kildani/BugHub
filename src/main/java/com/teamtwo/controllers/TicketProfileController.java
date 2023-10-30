package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Ticket;
import com.teamtwo.util.IdGenerator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicketProfileController extends AbstractBugHubController implements Initializable {
	
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
		this.ticketDate.setText("Date created: " + ticket.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		this.characterCount.setText(ticket.getDescr().length() + "/256");
		
		this.commentList.getItems().clear();
		for(Comment c : model.getService().getCommentsByTicketId(ticket.getId())) {
			commentList.getItems().add(createCommentListCell(c));
		}
	}
	
    public void clearForm(ActionEvent e) {
        descriptionArea.clear();
    }

	public void deleteComment(ActionEvent e) {
		GridPane commentCell = commentList.getSelectionModel().getSelectedItem();
		if (commentCell != null) {
			Label commentId = (Label) commentCell.getChildren().get(0);
			model.getService().deleteComment(Integer.parseInt(commentId.getText()));
			commentList.getItems().remove(commentCell);
		}
	}
	
	public void saveComment(ActionEvent e) {
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
            clearForm(e);
        }
	}
	
	private GridPane createCommentListCell(Comment comment) {
		GridPane listCell = new GridPane();
		listCell.setMaxSize(commentList.getPrefWidth(), Math.floor(commentList.getPrefHeight() / 3));
		listCell.setPadding(new Insets(0, 15, 0, 0));
		
		ColumnConstraints colC = new ColumnConstraints();
		colC.setPercentWidth(100);
		RowConstraints regData = new RowConstraints();
		regData.setVgrow(Priority.NEVER);
		regData.setValignment(VPos.CENTER);
		RowConstraints heavyData = new RowConstraints();
		heavyData.setVgrow(Priority.ALWAYS);
		heavyData.setValignment(VPos.CENTER);
		RowConstraints hiddenData = new RowConstraints();
		
		listCell.getColumnConstraints().add(colC);
		listCell.getRowConstraints().addAll(hiddenData, heavyData, regData);
		
		Label commentId = new Label(String.valueOf(comment.getId()));
		commentId.setVisible(false);
		listCell.add(commentId, 0, 0);
		Label commentDescr = new Label(comment.getDescr());
		commentDescr.setWrapText(true);
		listCell.add(commentDescr, 0, 1);
		Label commentDate = new Label(comment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		GridPane.setHalignment(commentDate, HPos.RIGHT);
		listCell.add(commentDate, 0, 2);
		
		return listCell;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		descriptionArea.setWrapText(true);
		ticketDescr.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
				savingDescrHelpLabel.setVisible(true);
				ticketDescr.setEditable(true);
				mouseEvent.consume();
			}
		});
		ticketDescr.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && ticketDescr.isEditable()) {
				savingDescrHelpLabel.setVisible(false);
				ticketDescr.setEditable(false);
				characterCount.setText(ticketDescr.getText().length() + "/256");
				ticket.setDescr(ticketDescr.getText());
				mouseEvent.consume();
			}
		});
		commentList.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
			GridPane selectedCell = commentList.getSelectionModel().getSelectedItem();
			if (selectedCell != null) {
				commentList.getSelectionModel().clearSelection();
				mouseEvent.consume();
			}
		});
	}
}
