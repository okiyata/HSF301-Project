package hsf301.fe.controller.student;

import hsf301.fe.controller.CustomSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Appointment;
import pojo.Student;
import service.appointment.AppointmentService;
import service.appointment.AppointmentServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingHistoryController {
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> idColumn;
    @FXML
    private TableColumn<Appointment, String> mentorNameColumn;
    @FXML
    private TableColumn<Appointment, String> skillRequestedColumn;
    @FXML
    private TableColumn<Appointment, Integer> feeColumn;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Appointment, String> statusColumn;
    @FXML
    private Label txtConfirm;

    private AppointmentService appointmentService;
    private CustomSession session;

    public BookingHistoryController() {
        this.appointmentService = new AppointmentServiceImpl();
        this.session = CustomSession.getInstance();
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        mentorNameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMentor().getMentorName()));
        skillRequestedColumn.setCellValueFactory(new PropertyValueFactory<>("skillRequested"));
        feeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getMentor().getBookingFee()).asObject());
        dateColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadAppointmentTable();
    }

    private void loadAppointmentTable() {
        Student student = (Student) session.getProperties().get("user");
        int groupId = student.getProjectGroup().getGroupID();

        List<Appointment> appointments = appointmentService.getAppointmentsByGroupId(groupId);
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(appointments);
        appointmentTable.setItems(appointmentList);
    }
}
