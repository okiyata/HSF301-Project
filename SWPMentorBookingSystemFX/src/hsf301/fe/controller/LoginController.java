package hsf301.fe.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pojo.Account;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.mentor.MentorService;
import service.mentor.MentorServiceImpl;
import service.student.StudentService;
import service.student.StudentServiceImpl;

public class LoginController {
	@FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    
    private AccountService accountService;
    private StudentService studentService;
    private MentorService mentorService;
    
    private SceneController sceneController;
    private CustomSession session;
    
    
    public LoginController () {
    	accountService = new AccountServiceImpl();
    	studentService = new StudentServiceImpl();
    	mentorService = new MentorServiceImpl();
    	sceneController = new SceneController();
    	session = CustomSession.getInstance();
	}

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }
        
        Account acc = accountService.authenticate(username, password); 
        
        if (acc == null) {
        	AlertController.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Account with email "+username+"does not exist!");
        } else {
        	System.err.println("account khong null nee");
        	handleAuthorizedPage(acc, event);
        }

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

    }

    @FXML
    private void handleCancel() {
        txtUsername.clear();
        txtPassword.clear();
        Platform.exit();
    }
    
    private void handleAuthorizedPage (Account acc, ActionEvent event) {
    	try { 
	    	switch (acc.getRole()) {
	    		case "STUDENT":
	    			System.err.println("account student");
	            	session.getProperties().put("user", studentService.findById(acc.getStudent().getStudentID()));
	    			sceneController.directToStudent(event);
	    			break;
	    		case "MENTOR":
	    			System.err.println("account mentor");
	            	session.getProperties().put("user", mentorService.findById(acc.getMentor().getMentorID()));
	    			sceneController.directToMentor(event);
	    			break;
				default:
					AlertController.showAlert(Alert.AlertType.ERROR, "Internal Error", "Something is wrong in the server");
					break;
	    	}
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    	System.err.println(ex);
	    }
    }
}
