package com.teamtwo.controllers;

import java.io.IOException;

import com.teamtwo.data_model.BugHubDataModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;

public class MainPageController implements BugHubController{

	private BugHubDataModel model;

	public void loadModel(BugHubDataModel model) {
		this.model = model;
	}
	
	public void switchToProjectForm(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot(model.getNode("PROJECT_FORM"));
	}

	public void switchToProjectDirectory(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot(model.getNode("PROJECT_DIRECTORY"));
	}
}