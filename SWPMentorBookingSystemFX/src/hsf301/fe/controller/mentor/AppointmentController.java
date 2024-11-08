package hsf301.fe.controller.mentor;

import java.time.format.DateTimeFormatter;
import java.util.List;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Appointment;
import pojo.Mentor;
import pojo.ProjectGroup;
import service.appointment.AppointmentService;
import service.appointment.AppointmentServiceImpl;
import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;
import service.projectGroup.ProjectGroupService;
import service.projectGroup.ProjectGroupServiceImpl;

public class AppointmentController {
	@FXML
	private TableView<Appointment> groupTable;
	@FXML
	private TableColumn<Appointment, Integer> appointmentID;
	@FXML
	private TableColumn<Appointment, String> dateTime;
	@FXML
	private TableColumn<Appointment, String> skillRequested;
	@FXML
	private TableColumn<Appointment, String> status;
	@FXML
	private TableColumn<Appointment, String> groupName;
	@FXML
	private TableColumn<Appointment, Integer> fee;

	@FXML
	private Button btnAproved;
	@FXML
	private Button btnDenied;
	@FXML
	private Button btnFinished;

	private CustomSession session;
	private MentorService mentorService;
	private AppointmentService appointmentService;
	private ProjectGroupService projectGroupService;

	Mentor mentor = null;
	
	public AppointmentController() {
		this.mentorService = new MentorServiceImpl();
		this.appointmentService = new AppointmentServiceImpl();
		this.projectGroupService = new ProjectGroupServiceImpl();
		session = CustomSession.getInstance();
	}

	@FXML
	public void initialize() {
		mentor = (Mentor) session.getProperties().get("user");
		appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
		dateTime.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
		skillRequested.setCellValueFactory(new PropertyValueFactory<>("skillRequested"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		groupName.setCellValueFactory(cellData -> {
			Appointment appointment = cellData.getValue();
			String groupName = appointment.getProjectGroup().getGroupName();
			return new SimpleStringProperty(groupName);
		});
		fee.setCellValueFactory(new PropertyValueFactory<>("fee"));
		
		groupTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            String status = newSelection.getStatus();
	            btnFinished.setDisable(!"APPROVED".equals(status));
	        } else {
	            btnFinished.setDisable(true);
	        }
	    });
		groupTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	            String status = newSelection.getStatus();
	            btnAproved.setDisable("APPROVED".equals(status));
	        } else {
	        	btnAproved.setDisable(true);
	        }
	    });

		loadAppointments();
	}

	private void loadAppointments() {
		int mentorID = mentor.getMentorID();
		List<Appointment> appointments = mentorService.findAppointmentsByMentorId(mentorID);
		ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(appointments);
		groupTable.setItems(observableAppointments);
	}

	@FXML
	public void handleApproved() {
		try {
			Appointment selectedAppointment = groupTable.getSelectionModel().getSelectedItem();
			selectedAppointment.setStatus("APPROVED");
			appointmentService.update(selectedAppointment);
			loadAppointments();
			AlertController.showAlert(Alert.AlertType.CONFIRMATION, "APPROVED successful!", "Approved appointment with id " + selectedAppointment.getAppointmentID());
		} catch (Exception e) {
			AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request!", "Vui lòng chọn appointment!");
		}

	}

	@FXML
	public void handleDenied() {
		try {
			Appointment selectedAppointment = groupTable.getSelectionModel().getSelectedItem();
			selectedAppointment.setStatus("DENIED");
			appointmentService.update(selectedAppointment);
			AlertController.showAlert(Alert.AlertType.CONFIRMATION, "DENIED successful!", "Denied appointment with id " + selectedAppointment.getAppointmentID());
			loadAppointments();
		} catch (Exception e) {
			AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request!", "Vui lòng chọn appointment!");
		}
	}

	@FXML
	public void handleFinished() {
		try {
			Appointment selectedAppointment = groupTable.getSelectionModel().getSelectedItem();
			if (selectedAppointment.getStatus().equals("APPROVED")) {
				selectedAppointment.setStatus("FINISHED");
				ProjectGroup projectGroup = selectedAppointment.getProjectGroup();
				projectGroup.setWalletPoints(projectGroup.getWalletPoints()-selectedAppointment.getFee());
				projectGroupService.update(projectGroup);
				
				
				
				appointmentService.update(selectedAppointment);
				AlertController.showAlert(Alert.AlertType.CONFIRMATION, "FINISHED successful!", "Finished appointment with id " + selectedAppointment.getAppointmentID());
			} else {
				AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request!", "Appointment này chưa được APPROVE!");
			}

			loadAppointments();
		} catch (Exception e) {
			AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request!", "Vui lòng chọn appointment!");
		}
	}
}
