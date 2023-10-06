package userInterface;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class ProjectPage extends Parent 
{
    public ProjectPage() 
    {
        try 
        {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userInterface.fxml"));
            this.getChildren().add(root);
        }
        
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
