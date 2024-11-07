package hsf301.fe.controller.mentor;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import pojo.Mentor;
import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;

public class MentorProfileController {
	@FXML
	private TextField txtMentorID;
	@FXML
	private TextField txtMentorName;
	@FXML
	private TextField txtMentorSkill;
	@FXML
	private TextField txtMentorAvailability;
	@FXML
	private TextField txtMentorBookingFee;
	@FXML
	private ComboBox<String> txtMentorStatus;
	
	private MentorService mentorService;
	private CustomSession session;
	public MentorProfileController () {
		mentorService = new MentorServiceImpl();
		session = CustomSession.getInstance();
	}
	
	@FXML
	public void initialize () {
		Mentor mentor = (Mentor)session.getProperties().get("user");
		txtMentorID.setText(String.valueOf(mentor.getMentorID()));
		txtMentorName.setText(mentor.getMentorName());
		txtMentorSkill.setText(mentor.getSkills());
		txtMentorAvailability.setText(mentor.getAvailability());
		txtMentorBookingFee.setText(String.valueOf(mentor.getBookingFee()));
	    txtMentorStatus.getItems().addAll("AVAILABLE", "UNAVAILABLE");
	    txtMentorStatus.setValue(mentor.getStatus());
	}
	
	@FXML
	public void handleUpdate (ActionEvent event) {
	    try {
	        Mentor mentor = (Mentor) session.getProperties().get("user");

	        String mentorName = txtMentorName.getText();
	        if (mentorName != null && !mentorName.isEmpty()) {
	            String[] words = mentorName.split("\\s+");
	            StringBuilder formattedName = new StringBuilder();
	            for (String word : words) {
	                formattedName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
	            }
	            mentor.setMentorName(formattedName.toString().trim());
	        }

	        mentor.setSkills(txtMentorSkill.getText());

	        mentor.setAvailability(txtMentorAvailability.getText());

	        try {
	            mentor.setBookingFee(Integer.parseInt(txtMentorBookingFee.getText()));
	        } catch (NumberFormatException e) {
	            AlertController.showAlert(AlertType.ERROR, "Invalid Booking Fee", "Booking Fee must be a valid number.");
	            return;
	        }

	        String status = txtMentorStatus.getValue();
	        if ("AVAILABLE".equals(status) || "UNAVAILABLE".equals(status)) {
	            mentor.setStatus(status);
	        } else {
	            AlertController.showAlert(AlertType.ERROR, "Invalid Status", "Status must be either 'AVAILABLE' or 'UNAVAILABLE'.");
	            return;
	        }

	        mentor = mentorService.update(mentor);

	        session.getProperties().put("user", mentor);

	        AlertController.showAlert(AlertType.INFORMATION, "Update Successfully", "Your profile has been successfully updated");
	    } catch (Exception ex) {
	        System.err.println("Error updating mentor profile: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}

}
