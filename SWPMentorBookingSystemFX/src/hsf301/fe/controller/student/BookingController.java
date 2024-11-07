package hsf301.fe.controller.student;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Appointment;
import pojo.Mentor;
import pojo.ProjectGroup;
import pojo.Student;
import service.appointment.AppointmentService;
import service.appointment.AppointmentServiceImpl;
import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;
import service.projectGroup.ProjectGroupService;
import service.projectGroup.ProjectGroupServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class BookingController {
    @FXML
    private TableView<Mentor> mentorTable;
    @FXML
    private TableColumn<Mentor, Integer> idColumn;
    @FXML
    private TableColumn<Mentor, String> nameColumn;
    @FXML
    private TableColumn<Mentor, String> skillsColumn;
    @FXML
    private TableColumn<Mentor, String> availabilityColumn;
    @FXML
    private TableColumn<Mentor, Integer> feeColumn;
    @FXML
    private TextField txtSkillRequest;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtTime;
    @FXML
    private Label txtConfirm;
    @FXML
    private Button btnBook;

    private MentorService mentorService;
    private ProjectGroupService projectGroupService;
    private AppointmentService appointmentService;
    private ObservableList<Mentor> availableMentors;
    private CustomSession session;

    public BookingController() {
        mentorService = new MentorServiceImpl();
        projectGroupService = new ProjectGroupServiceImpl();
        appointmentService = new AppointmentServiceImpl();
        session = CustomSession.getInstance();
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("mentorID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("mentorName"));
        skillsColumn.setCellValueFactory(new PropertyValueFactory<>("skills"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingFee"));

        loadAvailableMentors();

        txtSkillRequest.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMentorsBySkill(newValue);
        });
    }

    private void loadAvailableMentors() {
        List<Mentor> mentors = mentorService.getAvailableMentors();
        availableMentors = FXCollections.observableArrayList(mentors);
        mentorTable.setItems(availableMentors);
    }

    private void filterMentorsBySkill(String skill) {
        List<Mentor> filteredMentors = availableMentors.stream()
                .filter(mentor -> mentor.getSkills().toLowerCase().contains(skill.toLowerCase()))
                .collect(Collectors.toList());
        mentorTable.setItems(FXCollections.observableArrayList(filteredMentors));
    }

    @FXML
    private void handleBook(ActionEvent event) {
        Mentor selectedMentor = mentorTable.getSelectionModel().getSelectedItem();
        if (selectedMentor == null) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Booking Error", "Please select a mentor to book!");
            return;
        }

        LocalDate date = txtDate.getValue();
        String timeText = txtTime.getText();
        String skillRequested = txtSkillRequest.getText();

        if (date == null || timeText.isEmpty() || skillRequested.isEmpty()) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Input Error", "Please select a date, enter a time, and specify the skill required.");
            return;
        }

        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(timeText, timeFormatter);

            LocalDateTime bookingDateTime = LocalDateTime.of(date, time);

            if (bookingDateTime.isBefore(LocalDateTime.now())) {
                AlertController.showAlert(Alert.AlertType.WARNING, "Invalid Date/Time", "Booking time must be in the future.");
                return;
            }
            
            Student student = (Student) session.getProperties().get("user");
            
            int walletPoints = student.getProjectGroup().getWalletPoints();
            double bookingFee = selectedMentor.getBookingFee();
            
            if (walletPoints < bookingFee) {
                AlertController.showAlert(Alert.AlertType.WARNING, "Insufficient Points", "You do not have enough points to book this mentor.");
                return;
            }
            
            ProjectGroup group = projectGroupService.findGroupByStudentId(student.getStudentID());

            Appointment appointment = new Appointment();
            appointment.setMentor(selectedMentor);
            appointment.setProjectGroup(group);
            appointment.setDateTime(bookingDateTime);
            appointment.setSkillRequested(skillRequested);
            appointment.setFee(selectedMentor.getBookingFee());
            appointment.setStatus("AWAIT_APPROVAL");

            appointmentService.save(appointment);

            txtConfirm.setText("Booking confirmed for " + selectedMentor.getMentorName() + " at " + bookingDateTime);
        } catch (DateTimeParseException e) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Invalid Time Format", "Please enter the time in HH:mm format.");
        }
    }

}
