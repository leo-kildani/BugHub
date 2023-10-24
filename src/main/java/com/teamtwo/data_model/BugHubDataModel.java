package com.teamtwo.data_model;

import com.teamtwo.access.BugHubDataAccess;
import com.teamtwo.controllers.BugHubController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class BugHubDataModel {

    private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    private final BugHubDataAccess dao;

    public BugHubDataModel(BugHubDataAccess dao) {
        this.dao = dao;
        for (MODEL m: MODEL.values()) {
            getController(m.toString(), BugHubController.class).loadModel(this);
        }
    }

    public BugHubDataAccess getDao() {
        return dao;
    }

    public Parent getNode(String page) {
        return MODEL.valueOf(page).node;
    }

    public <T extends BugHubController> T getController(String page, Class<T> type) {
        return type.cast(MODEL.valueOf(page).loader.getController());
    }


    private enum MODEL{
        MAIN_MENU("fxml/Main.fxml"),
        PROJECT_DIRECTORY("fxml/ProjectDirectory.fxml"),
        PROJECT_FORM("fxml/ProjectForm.fxml"),
        PROJECT_PROFILE("fxml/ProjectProfile.fxml");

        public final FXMLLoader loader;
        public final Parent node;

        MODEL(String relativePathToFXML) {
            try {
                loader = new FXMLLoader(classLoader.getResource(relativePathToFXML));
                node = loader.load();
            } catch (IOException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }
}
