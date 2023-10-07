package com.teamtwo.application;

import com.teamtwo.userInterface.ProjectPage;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.access.BugHubDataAccessFileImpl;
import com.teamtwo.userInterface.MainMenu;

public class Main extends Application {

	private BugHubDataAccess dao;
	
	private Parent mainMenu, projectPage;
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
    	dao = new BugHubDataAccessFileImpl();
    	projectPage = new ProjectPage();
    	mainMenu = new MainMenu();
    }

    // This method runs javafx application
    @Override
    public void start(Stage mainStage) throws Exception {
        MainMenu mainMenu = new MainMenu();
        Scene mainScene = new Scene(mainMenu);
        mainScene.getStylesheets().add(mainMenu.getCss());
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}