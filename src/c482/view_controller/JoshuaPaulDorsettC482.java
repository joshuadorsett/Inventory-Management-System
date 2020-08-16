package c482.view_controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** @author Joshua Paul Dorsett
 * 
 * this class opens the JavaFX application and navigates to the main page
 */
public class JoshuaPaulDorsettC482 extends Application {
    
    /**
     * main method which launches application
     */
    public static void main(String[] args) { launch(args); }
    
    /**
     * start method which navigates to main page
     */
    @Override
    public void start(Stage stage) throws Exception{        
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}