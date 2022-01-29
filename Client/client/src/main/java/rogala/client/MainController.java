package rogala.client;

import rogala.client.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
//import org.apache.commons.lang3.StringUtils;
import javafx.stage.FileChooser.ExtensionFilter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static rogala.client.StartController.users;

public class MainController extends Thread implements Initializable {
	
    @FXML public Label clientName;
    @FXML public TextField msgField;
    @FXML public TextArea msgRoom;
    @FXML ImageView profilePicture;
    
    public boolean toggleChat = false, toggleProfile = false;

    BufferedReader reader;
    PrintWriter writer;
    Socket socket;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientName.setText(StartController.username);
        profilePicture.setImage(new Image("file:/C:/Users/gmrog/eclipse-workspace/SocketMVC/Client/client/src/main/java/resources/client/profile.png"));
        connectSocket();
    }

    public void connectSocket() {
        try {
            socket = new Socket("localhost", 9001);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for(int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(StartController.username + ":")) {
                    continue;
                } else if(fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                msgRoom.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSendEvent(MouseEvent event) {
        send();
    }
    
    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    public void send() {
        String msg = msgField.getText();
        writer.println(StartController.username + ": " + msg);
        msgRoom.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        msgRoom.appendText("Me: " + msg + "\n");
        msgField.setText("");

        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }
    
    
    @FXML
    public void handleUploadButton() {
    	Stage stage = new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.png"));
    	fileChooser.setTitle("Wybierz zdjęcie");
    	File file = fileChooser.showOpenDialog(stage);
    	System.out.println(file.toURI().toString());
//    	Image image = new Image(file.toURI().toString());
    	profilePicture.setImage(new Image(file.toURI().toString()));
    	
    }
}