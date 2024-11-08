package hsf301.fe.controller.admin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CreateAccount {

    @FXML
    public void handleCreateMentor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hsf301/fe/view/admin/AdminUI.fxml"));
        Parent adminScreen = loader.load();
        
        AdminController adminController = loader.getController();
        
        Parent createAccountScreen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/admin/CreateAccountMentor.fxml"));
        
        adminController.getContentPane().getChildren().clear(); 
        adminController.getContentPane().getChildren().add(createAccountScreen); 

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(adminScreen);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void handleCreateStudent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hsf301/fe/view/admin/AdminUI.fxml"));
        Parent adminScreen = loader.load();
        
        AdminController adminController = loader.getController();
        
        Parent createAccountScreen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/admin/CreateAccountStudent.fxml"));
        
        adminController.getContentPane().getChildren().clear(); 
        adminController.getContentPane().getChildren().add(createAccountScreen); 

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(adminScreen);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

}
