package hsf301.fe.controller.admin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private StackPane contentPane;  

    @FXML
    private Button createAccountButton;
    @FXML
    private Button appointmentButton;
    @FXML
    private Button ratingButton;
    @FXML
    private Button logoutButton;

    private Button selectedButton; 

    private Stage stage;
    private Scene scene;

    public AdminController() {
        
    }

    @FXML
    private void initialize() {
        selectedButton = createAccountButton;
        loadUI("CreateAccount");  
        setSelectedButton(createAccountButton);  
    }

    @FXML
    public void handleCreateAccount(ActionEvent event) {
        if (selectedButton != createAccountButton) {
            loadUI("CreateAccount"); 
            setSelectedButton(createAccountButton);
        }
    }

//    @FXML
//    public void handleAppointment(ActionEvent event) {
//        if (selectedButton != appointmentButton) {
//            loadUI("Appointments");  // Tải UI Appointments
//            setSelectedButton(appointmentButton);
//        }
//    }
//
    @FXML
    public void handleRating(ActionEvent event) {
        if (selectedButton != ratingButton) {
            loadUI("Rating");  
            setSelectedButton(ratingButton);
        }
    }

    @FXML
    public void handleLogout(ActionEvent event) throws IOException {
        Parent loginScreen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/LoginUI.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loginScreen);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    private void loadUI(String uiName) {
        try {
            Parent screen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/admin/" + uiName + ".fxml"));
            contentPane.getChildren().clear();  
            contentPane.getChildren().add(screen);  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSelectedButton(Button newButton) {
        if (selectedButton != null) {
            selectedButton.setDisable(false);  
        }
        newButton.setDisable(true);  
        selectedButton = newButton;  
    }
    public StackPane getContentPane() {
        return contentPane;
    }
}
