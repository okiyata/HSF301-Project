package hsf301.fe.controller.mentor;

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
    private Button btnApproved;
    @FXML
    private Button btnDenied;
    @FXML
    private Button btnFinished;
    
    
    private CustomSession session;
    private MentorService mentorService;
    private ProjectGroupService projectGroupService;
    private AlertController alertController;
    private AppointmentService appointmentService;

    public AppointmentController() {
        this.mentorService = new MentorServiceImpl();
        this.projectGroupService = new ProjectGroupServiceImpl();
        this.appointmentService = new AppointmentServiceImpl();
        session = CustomSession.getInstance();
    }

    @FXML
    public void initialize() {
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        skillRequested.setCellValueFactory(new PropertyValueFactory<>("skillRequested"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        groupName.setCellValueFactory(cellData -> {
            Appointment appointment = cellData.getValue();
            String groupName = appointment.getProjectGroup().getGroupName();
            return new SimpleStringProperty(groupName);
        });

        loadAppointments();
    }

    private void loadAppointments() {
    	Mentor mentor = (Mentor) session.getProperties().get("user");
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
    		groupTable.refresh();
    	}catch(Exception e) {
    		AlertController.showAlert(Alert.AlertType.WARNING, "Bad request!", "Vui long chon lop hoc!");
    	}
    	
    }
    
    @FXML
    public void handleDenied() {
    	try {
    		Appointment selectedAppointment = groupTable.getSelectionModel().getSelectedItem();
    		selectedAppointment.setStatus("DENIED");
    		appointmentService.update(selectedAppointment);
    		groupTable.refresh();
    	}catch(Exception e) {
    		AlertController.showAlert(Alert.AlertType.WARNING, "Bad request!", "Vui long chon lop hoc!");
    	}
    }
    
    @FXML
    public void handleFinished() {
    	try {
    		Appointment selectedAppointment = groupTable.getSelectionModel().getSelectedItem();
    		if (selectedAppointment.getStatus().equals("APPROVED")) {
    			selectedAppointment.setStatus("FINISHED");
        		appointmentService.update(selectedAppointment);
    		}else {
    			AlertController.showAlert(Alert.AlertType.WARNING, "Bad request!", "Khoa hoc nay chua duoc approved!");
    		}
    		
    		groupTable.refresh();
    	}catch(Exception e) {
    		AlertController.showAlert(Alert.AlertType.WARNING, "Bad request!", "Vui long chon lop hoc!");
    	}
    }
}
