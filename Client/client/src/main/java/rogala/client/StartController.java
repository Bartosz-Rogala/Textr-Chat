package rogala.client;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class StartController {
    
    @FXML public TextField userName;
    
    public static String username;
    public static ArrayList<String> users = new ArrayList<String>();
    public MainController mainController = new MainController();

    public void handleJoin() {
    	System.out.println(userName.getText());
    	username = userName.getText();
    	users.add(username);
        changeWindow();
    }
    


    public void changeWindow() {
        try {
            Stage stage = (Stage) userName.getScene().getWindow();
            Parent root = FXMLLoader.load(this.getClass().getResource("/view/MainWindow.fxml"));
            stage.setScene(new Scene(root, 330, 560));
            stage.setTitle(username + "");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}