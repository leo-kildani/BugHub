package com.teamtwo.controllers;

import java.io.IOException;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.access.BugHubDataAccessFileImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPageController {
	
	private Stage stage;
	private Parent root;
	
	private BugHubDataAccess dao;
	private Parent projectPage;
	
	public void loadDao(BugHubDataAccess dao) {
		this.dao = dao;
	}
	
	public void loadPages(Parent projectForm) {
		this.projectPage = projectForm;
	}
	
	public void switchToProj(ActionEvent event) throws IOException {
		projectPage = FXMLLoader.load(getClass().getResource("/fxml/userInterface.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(projectPage);
		stage.setScene(scene);
		stage.show();
	}
}