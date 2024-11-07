package hsf301.fe.controller.student;

import java.io.IOException;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pojo.ProjectGroup;
import pojo.Student;
import service.projectGroup.ProjectGroupService;
import service.projectGroup.ProjectGroupServiceImpl;
import service.student.StudentService;
import service.student.StudentServiceImpl;

public class StudentController {
	@FXML
	private StackPane contentPane;

	@FXML
	private Button groupButton;
	@FXML
	private Button historyButton;
	@FXML
	private Button bookingButton;
	@FXML
	private Button ratingButton;
	@FXML
	private Button logoutButton;

	private Button selectedButton;

	private StudentService studentService;
	private ProjectGroupService projectGroupService;
	private CustomSession session;

	public StudentController() {
		studentService = new StudentServiceImpl();
		projectGroupService = new ProjectGroupServiceImpl();
		session = CustomSession.getInstance();
	}

	@FXML
	private void initialize() {
		Student student = (Student) session.getProperties().get("user");

		if (studentService.hasGroup(student.getStudentID())) {
			ProjectGroup group = projectGroupService.findGroupByStudentId(student.getStudentID());

			session.getProperties().put("currentGroup", group);

			loadUI("GroupDetails");
		} else {
			loadUI("CreateOrSearchGroup");
		}

		selectedButton = groupButton;
		setSelectedButton(groupButton);
	}

	@FXML
	public void handleLoadGroup(ActionEvent event) {
		if (selectedButton != groupButton) {
			Student student = (Student) session.getProperties().get("user");
			if (studentService.hasGroup(student.getStudentID())) {
				ProjectGroup group = projectGroupService.findGroupByStudentId(student.getStudentID());
				session.getProperties().put("currentGroup", group);
				loadUI("GroupDetails");
				setSelectedButton(groupButton);
			} else {
				loadUI("CreateOrSearchGroup");
				setSelectedButton(groupButton);
			}
		}
	}

	@FXML
	public void handleLoadBookingHistory(ActionEvent event) {
		if (selectedButton != historyButton) {
			loadUI("BookingHistory");
			setSelectedButton(historyButton);
		}
	}

	@FXML
	public void handleLoadBooking(ActionEvent event) {
		if (selectedButton != bookingButton) {
	        Student student = (Student) session.getProperties().get("user");
	        ProjectGroup currentGroup = (ProjectGroup) session.getProperties().get("currentGroup");

	        if (currentGroup.getLeader() == null || currentGroup.getLeader().getStudentID() != student.getStudentID()) {
	            AlertController.showAlert(AlertType.WARNING, "Access Denied", "Only the group leader can access the booking page.");
	            return;
	        }

	        loadUI("Booking");
	        setSelectedButton(bookingButton);
	    }
	}
	
	@FXML
	public void handleLoadRating(ActionEvent event) {
		if (selectedButton != ratingButton) {
	        Student student = (Student) session.getProperties().get("user");
	        ProjectGroup currentGroup = (ProjectGroup) session.getProperties().get("currentGroup");

	        if (currentGroup.getLeader() == null || currentGroup.getLeader().getStudentID() != student.getStudentID()) {
	            AlertController.showAlert(AlertType.WARNING, "Access Denied", "Only the group leader can access the booking page.");
	            return;
	        }

	        loadUI("Rating");
	        setSelectedButton(ratingButton);
	    }
	}

	@FXML
	public void handleLogout(ActionEvent event) {
		try {
	        session.getProperties().clear();

	        Parent loginScreen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/LoginUI.fxml"));
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	Scene scene = new Scene(loginScreen);
	    	stage.setScene(scene);
	    	stage.centerOnScreen();
	    	stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private void loadUI(String uiName) {
		try {
			Parent screen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/student/" + uiName + ".fxml"));
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