package com.teamtwo.userInterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.util.Objects;

public class ProjectPage extends Parent 
{
    public ProjectPage() 
    {
        try 
        {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/userInterface.fxml")));
            this.getChildren().add(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
