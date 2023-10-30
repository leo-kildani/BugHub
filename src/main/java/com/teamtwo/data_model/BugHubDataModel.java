package com.teamtwo.data_model;

import com.teamtwo.controllers.BugHubController;
import com.teamtwo.service.BugHubService;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class BugHubDataModel {

    private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    private final BugHubService service;

    public BugHubDataModel(BugHubService service) {
        this.service = service;
        for (MODEL m: MODEL.values()) {
            getController(m.toString(), BugHubController.class).loadModel(this);
        }
    }

    public BugHubService getService() {
        return service;
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
        TICKET_FORM("fxml/TicketForm.fxml"),
    	TICKET_PROFILE("fxml/TicketProfile.fxml"),
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
