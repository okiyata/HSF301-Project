package hsf301.fe.controller.student;

import java.util.List;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private Label txtProgress;
    @FXML
    private Label txtConfirm;

    private ProjectGroupService projectGroupService;
    private StudentService studentService;
    private CustomSession session;
    private ProjectGroup currentGroup;

    public GroupDetailsController() {
        projectGroupService = new ProjectGroupServiceImpl();
        studentService = new StudentServiceImpl();
        session = CustomSession.getInstance();
    }

    @FXML
    private void initialize() {
        currentGroup = (ProjectGroup) session.getProperties().get("currentGroup");
        loadGroupDetails();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        loadMemberTable();
    }

    private void loadGroupDetails() {
        txtGroupName.setText(currentGroup.getGroupName());
        txtTopic.setText(currentGroup.getTopic());
        txtProgress.setText(currentGroup.getProgress());
        txtWalletPoint.setText(String.valueOf(currentGroup.getWalletPoints()));
    }

    private void loadMemberTable() {
    	List<Student> members = projectGroupService.getMembers(currentGroup.getGroupID());
        ObservableList<Student> memberList = FXCollections.observableArrayList(members);
        groupTable.setItems(memberList);
    }

    @FXML
    private void handleAdd(ActionEvent event) {
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
}
