package hsf301.fe.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	private Stage stage;
	private Scene scene;


    public void directToMentor(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/mentor/MentorUI.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.centerOnScreen();
    	stage.show();
    }
    
    public void directToStudent(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/student/StudentUI.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.centerOnScreen();
    	stage.show();
    }
}
