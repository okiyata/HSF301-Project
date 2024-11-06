package hsf301.fe.controller.mentor;

import java.io.IOException;

import hsf301.fe.controller.CustomSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;
import pojo.Mentor;
import pojo.Student;

public class MentorController {
    @FXML
    private StackPane contentPane;

    @FXML
    private Button profileButton;
    @FXML
    private Button appointmentButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button logoutButton;
	
	  private Button selectedButton;
	
	  private Stage stage;
	  private Scene scene;
  
    private MentorService mentorService;
    private CustomSession session;

    public MentorController() {
        mentorService = new MentorServiceImpl();
    }
	
	 @FXML
	    private void initialize() {
	        // Initialize the selected button to the profile button as the default tab
	        selectedButton = profileButton;
	        loadUI("Profile");
	        setSelectedButton(profileButton);
	    }
	
	@FXML
	public void handleLoadProfile(ActionEvent event) {
		if (selectedButton != profileButton) {
            loadUI("Profile");
            setSelectedButton(profileButton);
        }
	}
	
  @FXML
    public void handleAppointment(ActionEvent event) {
        if (selectedButton != appointmentButton) {
            loadUI("Appointments");
            setSelectedButton(appointmentButton);
        }
    }

    @FXML
    public void handleHistory(ActionEvent event) {
        if (selectedButton != historyButton) {
            loadUI("History");
            setSelectedButton(historyButton);
        }
    }
	
	@FXML
	public void handleLogout(ActionEvent event) throws IOException {
		 Parent loginScreen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/LoginUI.fxml"));
	    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	scene = new Scene(loginScreen);
	    	stage.setScene(scene);
	    	stage.centerOnScreen();
	    	stage.show();
	}
	
    private void loadUI(String uiName) {
        try {
            Parent screen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/mentor/" + uiName + ".fxml"));
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
}
