package com.teamtwo.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

	
    public static void main(String[] args) {
        launch(args);
    }

    // This method runs javafx application
    @Override
    public void start(Stage mainStage) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        Parent root = FXMLLoader.load(Objects.requireNonNull(classLoader.getResource("fxml/Main.fxml")));
        Scene mainScene = new Scene(root);
        String mainPageCss = Objects.requireNonNull(classLoader.getResource("css/application.css")).toExternalForm();
        mainScene.getStylesheets().add(mainPageCss);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}