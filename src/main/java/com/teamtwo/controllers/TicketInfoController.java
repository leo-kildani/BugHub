package com.teamtwo.controllers;

import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.entity.Comment;
import com.teamtwo.entity.Project;
import com.teamtwo.entity.Ticket;
import com.teamtwo.util.IdGenerator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicketInfoController implements BugHubController, Initializable {
	
	@FXML
	private Label ticketTitle;
	
	@FXML
	private Label ticketDate;
	
	@FXML
	private Label characterCount;
	
	@FXML
	private TextArea ticketDescr;
	
	private Ticket ticket;
	
	@FXML
	private ListView<GridPane> commentList;
	
	@FXML
	private LocalDate todaysDate;
	
	@FXML
	private DatePicker startingDate;
	
	@FXML
	private LocalTime time;
	
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private TextField commentNameField;
	
	private BugHubDataModel model;
	
	public void loadModel(BugHubDataModel model) {
		this.model = model;
	}

    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(model.getNode("MAIN_MENU"));
    }
	
	public void switchToProjectForm(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot(model.getNode("PROJECT_FORM"));
	}
	
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
		this.ticketTitle.setText(ticket.getTitle());
		this.ticketDescr.setText(ticket.getDescr());
		this.ticketDate.setText("Date created: " + ticket.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		this.characterCount.setText(ticket.getDescr().length() + "/256");
		
		this.commentList.getItems().clear();
		for(Comment c : ticket.getComments().values()) {
			commentList.getItems().add(createCommentListCell(c));
		}
	}
	
    public void clearForm(ActionEvent e) {
        commentNameField.clear();
        startingDate.setValue(null);
        descriptionArea.clear();
    }
	
	public void saveComment(ActionEvent e) {
        int id = IdGenerator.generateId();
        while (Objects.nonNull(model.getDao().getComment(this.ticket, id)))
            id = IdGenerator.generateId();
        String commentName = commentNameField.getText();
        String commentDescription = descriptionArea.getText();
        LocalDate startDate = startingDate.getValue();
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Credentials");
        alert.setContentText("Do not leave any textfields empty before saving");
        
        if(commentName.isEmpty() || commentDescription.isEmpty()) {
        	alert.showAndWait();
        } else {
            Comment c = new Comment(id, commentName, commentDescription, this.ticket.getId());

            model.getDao().addComment(this.ticket, c);

            clearForm(e);
        }
	}
	
	private GridPane createCommentListCell(Comment comment) {
		GridPane cell = new GridPane();
		cell.setMaxSize(commentList.getPrefWidth(), Math.floor(commentList.getPrefHeight() / 3));
		cell.setPadding(new Insets(0, 15, 0, 0));
		
		ColumnConstraints colC = new ColumnConstraints();
		colC.setPercentWidth(100);
		RowConstraints regData = new RowConstraints();
		regData.setVgrow(Priority.NEVER);
		regData.setValignment(VPos.CENTER);
		RowConstraints heavyData = new RowConstraints();
		heavyData.setVgrow(Priority.ALWAYS);
		heavyData.setValignment(VPos.CENTER);
		RowConstraints hiddenData = new RowConstraints();
		
		cell.getColumnConstraints().add(colC);
		cell.getRowConstraints().addAll(hiddenData, regData, heavyData, regData);
		
		Label commentId = new Label(String.valueOf(comment.getId()));
		commentId.setVisible(false);
		cell.add(commentId, 0, 0);
		Label commentTitle = new Label(comment.getTitle());
		cell.add(commentTitle, 0, 0);
		Label commentDescr = new Label(comment.getDescr());
		cell.add(commentDescr, 0, 0);
		Label commentDate = new Label(comment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
		GridPane.setHalignment(commentDate, HPos.RIGHT);
		cell.add(commentDate, 0, 0);
		
		return cell;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		commentList.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
			GridPane selectedCell = commentList.getSelectionModel().getSelectedItem();
			if (selectedCell != null) {
				commentList.getSelectionModel().clearSelection();
				mouseEvent.consume();
			}
		});
		
		todaysDate = LocalDate.now();
		startingDate.setValue(todaysDate);
		time = LocalTime.now();
	}
}
