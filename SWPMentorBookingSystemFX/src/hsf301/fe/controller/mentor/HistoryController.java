package hsf301.fe.controller.mentor;

import java.time.format.DateTimeFormatter;
import java.util.List;

import hsf301.fe.controller.CustomSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Appointment;
import pojo.Mentor;
import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;

public class HistoryController {
	
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

    private CustomSession session;
    private MentorService mentorService;
    
    public HistoryController() {
        this.mentorService = new MentorServiceImpl();
        session = CustomSession.getInstance();
    }
    
    @FXML
    public void initialize() {
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

        loadAppointments();
    }
    
    private void loadAppointments() {
    	Mentor mentor = (Mentor) session.getProperties().get("user");
        int mentorID = mentor.getMentorID(); 
        List<Appointment> appointments = mentorService.findHistoryByMentorId(mentorID);
        ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(appointments);
        groupTable.setItems(observableAppointments);
    }
}
