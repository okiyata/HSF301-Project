package hsf301.fe.controller.admin;

import java.io.IOException;
import java.util.List;

import hsf301.fe.controller.AlertController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pojo.Account;
import pojo.Mentor;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;

public class CreateAccountMentor {
    @FXML
    private StackPane contentPane;  
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSkills;
    @FXML
    private TextField txtAvailability;
    @FXML
    private TextField txtBookingFee;
    @FXML 
    private ComboBox<String> txtRole;
    @FXML
    private TableView<Mentor> groupTable;
    @FXML
    private TableColumn<Mentor, Integer> mentorID;
    @FXML
    private TableColumn<Mentor, String> name;
    @FXML
    private TableColumn<Mentor, String> skills;
    @FXML
    private TableColumn<Mentor, String> availability;
    @FXML
    private TableColumn<Mentor, Integer> bookingFee;
    @FXML
    private TableColumn<Mentor, String> status;

    
    private AccountService accountService;
    private MentorService mentorService;

    public CreateAccountMentor() {
        accountService = new AccountServiceImpl();
        mentorService = new MentorServiceImpl();
    }
    
    private void loadMentorData() {
        List<Mentor> mentors = mentorService.findAll(); 
        groupTable.getItems().setAll(mentors); 
    }
    
    @FXML
    public void initialize() {
    	mentorID.setCellValueFactory(new PropertyValueFactory<>("mentorID"));
    	name.setCellValueFactory(new PropertyValueFactory<>("mentorName"));
    	skills.setCellValueFactory(new PropertyValueFactory<>("skills"));
    	availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    	bookingFee.setCellValueFactory(new PropertyValueFactory<>("bookingFee"));
    	status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        loadMentorData();
    }
    
    @FXML
    public void handleTurnback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hsf301/fe/view/admin/AdminUI.fxml"));
        Parent adminScreen = loader.load();
        
        AdminController adminController = loader.getController();
        
        Parent createAccountScreen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/admin/CreateAccount.fxml"));
        
        adminController.getContentPane().getChildren().clear(); 
        adminController.getContentPane().getChildren().add(createAccountScreen); 

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(adminScreen);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    
    public void handleCreate(ActionEvent event) {
        try {         
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String name = txtName.getText();
            String skills = txtSkills.getText();
            String availability = txtAvailability.getText();
            
            if (username == null || username.isEmpty()) {
                AlertController.showAlert(AlertType.ERROR, "Invalid Input", "Username cannot be empty.");
                return;
            }

            if (password == null || password.isEmpty()) {
                AlertController.showAlert(AlertType.ERROR, "Invalid Input", "Password cannot be empty.");
                return;
            }

            if (name == null || name.isEmpty()) {
                AlertController.showAlert(AlertType.ERROR, "Invalid Input", "Name cannot be empty.");
                return;
            }

            if (skills == null || skills.isEmpty()) {
                AlertController.showAlert(AlertType.ERROR, "Invalid Input", "Skills cannot be empty.");
                return;
            }

            if (availability == null || availability.isEmpty()) {
                AlertController.showAlert(AlertType.ERROR, "Invalid Input", "Availability cannot be empty.");
                return;
            }

            int bookingFee = 0;
            try {
                bookingFee = Integer.parseInt(txtBookingFee.getText());
                if (bookingFee <= 0) {
                    AlertController.showAlert(AlertType.ERROR, "Invalid Booking Fee", "Booking fee must be a positive number.");
                    return;
                }
            } catch (NumberFormatException e) {
                AlertController.showAlert(AlertType.ERROR, "Invalid Booking Fee", "Please enter a valid number for the booking fee.");
                return;
            }
            
            List<Account> oldAccounts = accountService.findAll();

            boolean usernameExists = oldAccounts.stream()
                .anyMatch(account -> account.getUsername().equals(username));
            
            if (usernameExists) {
                AlertController.showAlert(AlertType.ERROR, "Username Exists", "This username is already taken. Please choose a different one.");
                return;
            }

            if (name != null && !name.isEmpty()) {
                String[] words = name.split("\\s+");
                StringBuilder formattedName = new StringBuilder();
                for (String word : words) {
                    formattedName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
                }
                name = formattedName.toString().trim(); 
            }

            Account account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setRole("MENTOR"); 
            accountService.save(account);

            Mentor mentor = new Mentor();
            mentor.setAccount(account);
            mentor.setMentorName(name);
            mentor.setSkills(skills);
            mentor.setAvailability(availability);
            mentor.setBookingFee(bookingFee);
            mentor.setStatus("AVAILABLE");
            mentorService.save(mentor);

            AlertController.showAlert(AlertType.INFORMATION, "Account Created", "Mentor account created successfully.");
            
            loadMentorData();
        } catch (Exception e) {
            AlertController.showAlert(AlertType.ERROR, "Error", "An error occurred while creating the account.");
        } 
    }


}
