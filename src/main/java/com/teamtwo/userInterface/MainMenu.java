package com.teamtwo.userInterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

public class MainMenu extends Parent {

    private String css;

    public MainMenu() {
        this.css = Objects.requireNonNull(getClass().getClassLoader().getResource("css/application.css")).toExternalForm();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/Main.fxml")));
            this.getChildren().add(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCss() {
        return css;
    }
}
