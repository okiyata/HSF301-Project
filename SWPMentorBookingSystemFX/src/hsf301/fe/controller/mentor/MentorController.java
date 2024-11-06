package hsf301.fe.controller.mentor;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class MentorController {
	@FXML
	private StackPane contentPane;
	
	@FXML
    private Button profileButton;
    @FXML
    private Button bookingButton;
    @FXML
    private Button transactionsButton;
    @FXML
    private Button logoutButton;
	
	private Button selectedButton;
	
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
	public void handleLoadBooking	(ActionEvent event) {
		if (selectedButton != bookingButton) {
            loadUI("Booking");
            setSelectedButton(bookingButton);
        }
	}
	
	@FXML
	public void handleTransactions(ActionEvent event) {
		if (selectedButton != transactionsButton) {
            loadUI("Transaction");
            setSelectedButton(transactionsButton);
        }
	}
	
	@FXML
	public void handleLogout(ActionEvent event) {
		Platform.exit();
	}
	
	private void loadUI (String uiName) {
		try {
			Parent screen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/customer/"+uiName+".fxml"));
			contentPane.getChildren().clear();
	        contentPane.getChildren().add(screen);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private void setSelectedButton(Button newButton) {
        if (selectedButton != null) {
            // Enable the previously selected button
            selectedButton.setDisable(false);
        }
        
        // Disable the new button and change its background color
        newButton.setDisable(true);
        
        // Update the reference to the selected button
        selectedButton = newButton;
    }
}
