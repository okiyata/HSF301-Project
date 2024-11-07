package hsf301.fe.controller.student;

import java.io.IOException;
import java.util.List;

import hsf301.fe.controller.AlertController;
import hsf301.fe.controller.CustomSession;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import pojo.ProjectGroup;
import pojo.Student;
import service.projectGroup.ProjectGroupService;
import service.projectGroup.ProjectGroupServiceImpl;
import service.student.StudentService;
import service.student.StudentServiceImpl;

public class CreateOrSearchGroupController {
	@FXML
	private StackPane contentPane;
	@FXML
	private TextField txtSearch;
	@FXML
	private TextField txtTopic;
	@FXML
	private TextField txtGroupName;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnCreate;
	@FXML
	private Button btnJoin;
	@FXML
	private Label txtConfirm;
	@FXML
	private TableView<ProjectGroup> groupTable;
	@FXML
	private TableColumn<ProjectGroup, Integer> idColumn;
	@FXML
	private TableColumn<ProjectGroup, String> topicColumn;
	@FXML
	private TableColumn<ProjectGroup, Integer> membersColumn;
	@FXML
	private TableColumn<ProjectGroup, String> progressColumn;

	private ProjectGroupService projectGroupService;
	private StudentService studentService;
	private CustomSession session;
	private ProjectGroup selectedGroup;

	public CreateOrSearchGroupController() {
		projectGroupService = new ProjectGroupServiceImpl();
		studentService = new StudentServiceImpl();
		session = CustomSession.getInstance();
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("groupID"));
		topicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));
		progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
		membersColumn.setCellValueFactory(cellData -> {
	        ProjectGroup group = cellData.getValue();
	        int memberCount = projectGroupService.getMemberCount(group.getGroupID());
	        return new SimpleIntegerProperty(memberCount).asObject();
	    });

		groupTable.setItems(FXCollections.observableArrayList());

		groupTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectedGroup = newSelection;
				txtConfirm.setText("Join group " + selectedGroup.getGroupID() + "?");
			}
		});
	}

	@FXML
	private void handleCreate(ActionEvent event) {
		String topic = txtTopic.getText();
		String groupName = txtGroupName.getText();
		if (topic.isEmpty() || groupName.isEmpty()) {
			AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request", "Topic or Group Name is missing!");
			return;
		}
		Student student = (Student) session.getProperties().get("user");
		ProjectGroup newGroup = projectGroupService.createGroup(topic, groupName, student.getStudentID());

		if (newGroup != null) {
			loadUI("GroupDetails");
		}
	}

	@FXML
	private void handleSearch(ActionEvent event) {
		String groupName = txtSearch.getText();
		if (groupName.isEmpty()) {
			AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request", "Search query is empty!");
			return;
		}
		List<ProjectGroup> groups = projectGroupService.findByName(groupName);
		if (groups.isEmpty()) {
			AlertController.showAlert(Alert.AlertType.INFORMATION, "No Results",
					"No groups found with the name \"" + groupName + "\"");
			return;
		}
		ObservableList<ProjectGroup> groupList = FXCollections.observableArrayList(groups);
		groupTable.setItems(groupList);
	}

	private void loadUI(String uiName) {
		try {
			Parent screen = FXMLLoader.load(getClass().getResource("/hsf301/fe/view/student/" + uiName + ".fxml"));
			contentPane.getChildren().clear();
			contentPane.getChildren().add(screen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void handleJoin(ActionEvent event) {
		if (selectedGroup == null) {
			AlertController.showAlert(Alert.AlertType.WARNING, "Bad Request", "Please choose group!");
			return;
		}
		Student student = (Student) session.getProperties().get("user");
		boolean joined = studentService.joinGroup(selectedGroup.getGroupID(), student.getStudentID());
		if (joined) {
			txtConfirm.setText("Joined group " + selectedGroup.getGroupID() + " successfully!");
			session.getProperties().put("currentGroup", selectedGroup);
			loadUI("GroupDetails");
		} else {
			txtConfirm.setText("Unable to join group " + selectedGroup.getGroupID());
		}
	}

}
