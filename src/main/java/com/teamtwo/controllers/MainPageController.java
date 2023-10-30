package com.teamtwo.controllers;

import java.io.IOException;

import com.teamtwo.data_model.BugHubDataModel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;

public class MainPageController extends AbstractBugHubController{
	public void loadModel(BugHubDataModel model) {
		this.model = model;
	}
}