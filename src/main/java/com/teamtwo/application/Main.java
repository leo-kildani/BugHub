package com.teamtwo.application;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.access.BugHubDataAccessFileImpl;
import com.teamtwo.data_model.BugHubDataModel;
import com.teamtwo.service.BugHubService;
import com.teamtwo.service.BugHubServiceImpl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

	private BugHubDataAccess dao;
	private BugHubService service;
    private BugHubDataModel model;

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    @Override
    public void init() throws IOException {
        this.dao = new BugHubDataAccessFileImpl();
        this.service = new BugHubServiceImpl(dao);
        this.model = new BugHubDataModel(this.service);
    }

    // This method runs javafx application
    @Override
    public void start(Stage mainStage) throws Exception {
        Scene mainScene = new Scene(model.getNode("MAIN_MENU"));
        mainStage.setScene(mainScene);
        mainStage.sizeToScene();
        mainStage.setResizable(false);
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        dao.close();
    }
}