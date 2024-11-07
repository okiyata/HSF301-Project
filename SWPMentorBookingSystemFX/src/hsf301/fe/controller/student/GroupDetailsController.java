package hsf301.fe.controller.student;

import java.util.List;
import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ProjectGroup;
import pojo.Student;
import service.projectGroup.ProjectGroupService;
import service.projectGroup.ProjectGroupServiceImpl;
import service.student.StudentService;
import service.student.StudentServiceImpl;

public class GroupDetailsController {
    @FXML
    private TextField txtAdd;
    @FXML
    private TextField txtWalletPoint;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<Student> groupTable;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private Label txtGroupName;
    @FXML
    private Label txtTopic;
    @FXML
    private Label txtConfirm;
    @FXML
    private ComboBox<String> cbProgress;

    private ProjectGroupService projectGroupService;
    private StudentService studentService;
    private CustomSession session;
    private ProjectGroup currentGroup;
    private Student currentStudent;

    public GroupDetailsController() {
        projectGroupService = new ProjectGroupServiceImpl();
        studentService = new StudentServiceImpl();
        session = CustomSession.getInstance();
    }

    @FXML
    private void initialize() {
        currentGroup = (ProjectGroup) session.getProperties().get("currentGroup");
        currentStudent = (Student) session.getProperties().get("user");
        
        loadGroupDetails();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameColumn.setCellValueFactory(cellData -> {
            Student student = cellData.getValue();
            String studentName = student.getStudentName();
            
            if (currentGroup.getLeader() != null && student.getStudentID() == currentGroup.getLeader().getStudentID()) {
                studentName += " (LEADER)";
            }
            
            return new SimpleStringProperty(studentName);
        });

        loadMemberTable();

        cbProgress.setItems(FXCollections.observableArrayList("Not Started", "In Progress", "Near Completion", "Completed"));
    	cbProgress.setValue(currentGroup.getProgress());
        
        if (!isCurrentStudentLeader()) {
            disableEditing();
        }
    }

    private boolean isCurrentStudentLeader() {
        return currentGroup.getLeader() != null && currentGroup.getLeader().getStudentID() == currentStudent.getStudentID();
    }

    private void disableEditing() {
        txtAdd.setDisable(true);
        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        cbProgress.setDisable(true);
    }

    private void loadGroupDetails() {
        txtGroupName.setText(currentGroup.getGroupName());
        txtTopic.setText(currentGroup.getTopic());
        txtWalletPoint.setText(String.valueOf(currentGroup.getWalletPoints()));
    }

    private void loadMemberTable() {
        List<Student> members = projectGroupService.getMembers(currentGroup.getGroupID());
        ObservableList<Student> memberList = FXCollections.observableArrayList(members);
        groupTable.setItems(memberList);
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        if (!isCurrentStudentLeader()) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Permission Denied", "Only the group leader can add members.");
            return;
        }
        
        String memberName = txtAdd.getText();
        if (memberName.isEmpty()) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request", "Please enter a student name to add!");
            return;
        }

        Student newMember = studentService.findByName(memberName);
        if (newMember == null) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Not Found", "No student found with the name \"" + memberName + "\".");
            return;
        }

        boolean added = projectGroupService.addMemberToGroup(currentGroup.getGroupID(), newMember.getStudentID());
        if (added) {
            txtConfirm.setText("Added " + memberName + " to the group successfully!");
            loadMemberTable();
        } else {
            AlertController.showAlert(Alert.AlertType.WARNING, "Add Failed", memberName + " is already in the group or cannot be added.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (!isCurrentStudentLeader()) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Permission Denied", "Only the group leader can update the progress.");
            return;
        }
        
        String selectedProgress = cbProgress.getValue();
        if (selectedProgress == null || selectedProgress.isEmpty()) {
            AlertController.showAlert(Alert.AlertType.WARNING, "Invalid Progress", "Please select a valid progress status.");
            return;
        }

        currentGroup.setProgress(selectedProgress);
        projectGroupService.update(currentGroup);
        txtConfirm.setText("Progress updated successfully to: " + selectedProgress);
    }
}
