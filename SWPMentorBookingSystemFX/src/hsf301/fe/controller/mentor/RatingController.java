package hsf301.fe.controller.mentor;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Appointment;
import pojo.Mentor;
import pojo.Rating;
import service.appointment.AppointmentService;
import service.appointment.AppointmentServiceImpl;
import service.rating.RatingService;
import service.rating.RatingServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RatingController {
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> IDColumn;
    @FXML
    private TableColumn<Appointment, String> dateColumn;
    @FXML
    private TableColumn<Appointment, String> mentorNameColumn;
    @FXML
    private TableColumn<Appointment, String> groupNameColumn;
    @FXML
    private TextField txtRating;
    @FXML
    private TextArea txtFeedback;
    @FXML
    private Button btnSend;

    private CustomSession session;
    private AppointmentService appointmentService;
    private RatingService ratingService;
    private Appointment selectedAppointment;

    public RatingController() {
        this.appointmentService = new AppointmentServiceImpl();
        this.ratingService = new RatingServiceImpl();
        this.session = CustomSession.getInstance();
    }

    @FXML
    private void initialize() {
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        dateColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        mentorNameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getMentor().getMentorName()));
        groupNameColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProjectGroup().getGroupName()));

        loadAppointments();

        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedAppointment = newSelection;
        });
    }

    private void loadAppointments() {
        Mentor mentor = (Mentor) session.getProperties().get("user");
        List<Appointment> appointments = appointmentService.findFinishedAppointmentsWithoutMentorRating(mentor.getMentorID());
        ObservableList<Appointment> observableAppointments = FXCollections.observableArrayList(appointments);
        appointmentTable.setItems(observableAppointments);
    }

    @FXML
    private void handleSend(ActionEvent event) {
        if (selectedAppointment == null) {
            AlertController.showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an appointment to rate.");
            return;
        }

        String ratingText = txtRating.getText();
        String feedback = txtFeedback.getText();
        
        if (ratingText.isEmpty() || feedback.isEmpty()) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Incomplete Information", "Please enter both rating and feedback.");
            return;
        }

        try {
            int ratingScore = Integer.parseInt(ratingText);
            if (ratingScore < 1 || ratingScore > 5) {
                AlertController.showAlert(Alert.AlertType.WARNING, "Invalid Rating", "Rating must be between 1 and 5.");
                return;
            }

            Rating rating = new Rating();
            rating.setRating(ratingScore);
            rating.setFeedback(feedback);
            rating.setRatingType("MENTOR");
            rating.setAppointment(selectedAppointment);
            ratingService.save(rating);

            AlertController.showAlert(Alert.AlertType.INFORMATION, "Successful!", "Rating sent successfully.");
            loadAppointments();

        } catch (NumberFormatException e) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Invalid Rating", "Please enter a valid integer for the rating.");
        }
    }
}
