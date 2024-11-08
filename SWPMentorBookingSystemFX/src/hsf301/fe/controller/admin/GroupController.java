package hsf301.fe.controller.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.ProjectGroup;
import service.projectGroup.ProjectGroupService;
import service.projectGroup.ProjectGroupServiceImpl;

import java.util.List;

import hsf301.fe.controller.AlertController;

public class GroupController {

	@FXML
	private TableView<ProjectGroup> groupTable;
	@FXML
	private TableColumn<ProjectGroup, Integer> groupID;
	@FXML
	private TableColumn<ProjectGroup, String> groupName;
	@FXML
	private TableColumn<ProjectGroup, String> progress;
	@FXML
	private TableColumn<ProjectGroup, String> topic;
	@FXML
	private TableColumn<ProjectGroup, Integer> walletPoints;
	@FXML
	private TableColumn<ProjectGroup, String> leaderName;
	@FXML
	private TextField txtPoint;

	private ProjectGroupService projectGroupService;

	public GroupController() {
		this.projectGroupService = new ProjectGroupServiceImpl();
	}

	@FXML
	public void initialize() {
		groupID.setCellValueFactory(new PropertyValueFactory<>("groupID"));
		groupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));
		progress.setCellValueFactory(new PropertyValueFactory<>("progress"));
		topic.setCellValueFactory(new PropertyValueFactory<>("topic"));
		walletPoints.setCellValueFactory(new PropertyValueFactory<>("walletPoints"));
		leaderName.setCellValueFactory(cellData -> {
			ProjectGroup projectGroup = cellData.getValue();
			return new SimpleStringProperty(
					projectGroup.getLeader() != null ? projectGroup.getLeader().getStudentName() : "");
		});

		loadGroups();
	}

	private void loadGroups() {
		List<ProjectGroup> groups = projectGroupService.findAll();
		ObservableList<ProjectGroup> observableGroups = FXCollections.observableArrayList(groups);
		groupTable.setItems(observableGroups);
	}

	@FXML
	public void btnAdd() {
		ProjectGroup pGroup = groupTable.getSelectionModel().getSelectedItem();

		if (pGroup == null) {
			AlertController.showAlert(AlertType.WARNING, "No Selection", "Please select a group from the table!");
			return;
		}

		if (txtPoint.getText() != null && !txtPoint.getText().isEmpty()) {
			try {
				int pointsToAdd = Integer.parseInt(txtPoint.getText());
				int groupWallet = pGroup.getWalletPoints();
				pGroup.setWalletPoints(groupWallet + pointsToAdd);
				projectGroupService.update(pGroup);
				AlertController.showAlert(AlertType.INFORMATION, "Add points successful!",
						"Points added successfully!");
				groupTable.refresh();
			} catch (NumberFormatException e) {
				AlertController.showAlert(AlertType.WARNING, "Invalid Input",
						"Please enter a valid number for points.");
			}
		} else {
			AlertController.showAlert(AlertType.WARNING, "Bad Request!", "Please enter a point value!");
		}
	}
}
