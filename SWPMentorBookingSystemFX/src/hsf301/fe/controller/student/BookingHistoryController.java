package hsf301.fe.controller.student;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
    private Button btnCancel;

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
        
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnCancel.setDisable(!"AWAIT_APPROVAL".equals(newSelection.getStatus()));
            }
        });
    }

    private void loadAppointmentTable() {
        Student student = (Student) session.getProperties().get("user");
        int groupId = student.getProjectGroup().getGroupID();

        List<Appointment> appointments = appointmentService.getAppointmentsByGroupId(groupId);
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList(appointments);
        appointmentTable.setItems(appointmentList);
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            AlertController.showAlert(AlertType.WARNING, "No Selection", "Please select an appointment to cancel.");
            return;
        }

        if (!"AWAIT_APPROVAL".equals(selectedAppointment.getStatus())) {
            AlertController.showAlert(AlertType.WARNING, "Cannot Cancel", "Only appointments with status 'AWAIT_APPROVAL' can be canceled.");
            return;
        }

        selectedAppointment.setStatus("CANCELED");
        appointmentService.update(selectedAppointment);
        
        AlertController.showAlert(AlertType.INFORMATION, "Canceled", "Appointment has been successfully canceled.");
        loadAppointmentTable();
    }
}
