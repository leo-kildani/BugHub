package com.teamtwo.application;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.access.BugHubDataAccessFileImpl;
import com.teamtwo.controllers.MainPageController;
import com.teamtwo.controllers.ProjectFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

	private BugHubDataAccess dao;

    private FXMLLoader mainMenuLoader, projectFormLoader;

    private Parent mainMenu, projectForm;

    private String mainMenuCss;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        this.dao = new BugHubDataAccessFileImpl();

        mainMenuLoader = new FXMLLoader(classLoader.getResource("fxml/Main.fxml"));
        projectFormLoader = new FXMLLoader(classLoader.getResource("fxml/ProjectForm.fxml"));

        mainMenuCss = Objects.requireNonNull(classLoader.getResource("css/application.css")).toExternalForm();

        mainMenu = (Parent) mainMenuLoader.load();
        projectForm = (Parent) projectFormLoader.load();

        MainPageController mainPageController = mainMenuLoader.getController();
        mainPageController.loadDao(dao);
        mainPageController.loadPages(projectForm);

        ProjectFormController projectFormController = projectFormLoader.getController();
        projectFormController.loadDao(dao);
        projectFormController.loadPages(mainMenu);
    }

    // This method runs javafx application
    @Override
    public void start(Stage mainStage) throws Exception {
        Scene mainScene = new Scene(mainMenu);
        mainScene.getStylesheets().add(mainMenuCss);
        mainStage.setScene(mainScene);
        mainStage.setWidth(700);
        mainStage.setHeight(500);
        mainStage.setResizable(false);
        mainStage.show();
    }

    @Override
    public void stop() {
        dao.close();
    }
}