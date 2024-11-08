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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pojo.Account;
import pojo.Student;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.student.StudentService;
import service.student.StudentServiceImpl;

public class CreateAccountStudent {
    @FXML
    private StackPane contentPane;  
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtName;
    @FXML 
    private ComboBox<String> txtRole;
    @FXML
    private TableView<Student> groupTable;
    @FXML
    private TableColumn<Student, Integer> studentID;
    @FXML
    private TableColumn<Student, String> studentName;
    
    private AccountService accountService;
    private StudentService studentService;

    public CreateAccountStudent() {
        accountService = new AccountServiceImpl();
        studentService = new StudentServiceImpl();
    }
    
    private void loadStudentData() {
        List<Student> students = studentService.findAll(); 
        groupTable.getItems().setAll(students); 
    }
    
    @FXML
    public void initialize() {
    	studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
    	studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
    
    	loadStudentData();
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

            Student student = new Student();
            student.setAccount(account);
            student.setStudentName(name);
            studentService.save(student);

            AlertController.showAlert(AlertType.INFORMATION, "Account Created", "Mentor account created successfully.");
            
        	loadStudentData();
        } catch (Exception e) {
            AlertController.showAlert(AlertType.ERROR, "Error", "An error occurred while creating the account.");
        } 
    }
}
