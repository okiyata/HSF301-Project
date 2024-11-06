package hsf301.fe.controller.student;

import java.io.IOException;

import hsf301.fe.controller.CustomSession;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import pojo.Student;
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
	private Button logoutButton;

	private Button selectedButton;

	private StudentService studentService;
    private CustomSession session;

    public StudentController() {
        studentService = new StudentServiceImpl();
        session = CustomSession.getInstance();
    }
	
	@FXML
	private void initialize() {
		Student student = (Student) session.getProperties().get("user");
		if (studentService.hasGroup(student.getStudentID())) {
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
		        loadUI("GroupDetails");
		    } else {
		        loadUI("CreateOrSearchGroup");
		    }
		}
	}

	@FXML
	public void handleLoadGroupHistory(ActionEvent event) {
		if (selectedButton != historyButton) {
			loadUI("GroupHistory");
			setSelectedButton(historyButton);
		}
	}

	@FXML
	public void handleLoadBooking(ActionEvent event) {
		if (selectedButton != bookingButton) {
			loadUI("Booking");
			setSelectedButton(bookingButton);
		}
	}

	@FXML
	public void handleLogout(ActionEvent event) {
		Platform.exit();
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