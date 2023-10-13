package com.teamtwo.application;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.access.BugHubDataAccessFileImpl;
import com.teamtwo.data_model.BugHubDataModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

	private BugHubDataAccess dao;
    private BugHubDataModel model;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws IOException {
        this.dao = new BugHubDataAccessFileImpl();
        this.model = new BugHubDataModel(dao);
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
    public void stop() {
        dao.close();
    }
}