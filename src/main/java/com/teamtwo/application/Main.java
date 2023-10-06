package com.teamtwo.application;

import com.teamtwo.userInterface.ProjectPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.teamtwo.userInterface.MainMenu;

public class Main extends Application {

	
    public static void main(String[] args) {
        launch(args);
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