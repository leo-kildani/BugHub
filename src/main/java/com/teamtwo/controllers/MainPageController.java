package com.teamtwo.controllers;

import java.io.IOException;

import com.teamtwo.access.BugHubDataAccess;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainPageController {
	
	private BugHubDataAccess dao;
	private Parent projectPage, projectDirectory;
	
	public void loadDao(BugHubDataAccess dao) {
		this.dao = dao;
	}
	
	public void loadPages(Parent projectForm, Parent projectDirectory) {
		this.projectPage = projectForm;
		this.projectDirectory = projectDirectory;
	}
	
	public void switchToProjectForm(ActionEvent event) throws IOException {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot(projectPage);
	}

	public void switchToProjectDirectory(ActionEvent event) {
		Scene scene = ((Node) event.getSource()).getScene();
		scene.setRoot(projectDirectory);
	}
}