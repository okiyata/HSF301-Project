package hsf301.fe.controller.admin;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Rating;
import service.rating.RatingService;
import service.rating.RatingServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class RatingController {
	@FXML
	private TableView<Rating> ratingTable;
	@FXML
	private TableColumn<Rating, Integer> appointmentIDColumn;
	@FXML
	private TableColumn<Rating, Integer> ratingColumn;
	@FXML
	private TableColumn<Rating, String> feedbackColumn;
	@FXML
	private TableColumn<Rating, String> ratingTypeColumn;
	@FXML
	private ComboBox<String> cbRatingType;
	@FXML
	private ComboBox<String> cbRating;
	@FXML
	private TextField txtSearch;
	@FXML
	private Label txtConfirm;

	private RatingService ratingService;
	private List<Rating> allRatings;

	public RatingController() {
		this.ratingService = new RatingServiceImpl();
	}

	@FXML
	private void initialize() {
		appointmentIDColumn.setCellValueFactory(
				cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentID()).asObject());
		ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
		feedbackColumn.setCellValueFactory(new PropertyValueFactory<>("feedback"));
		ratingTypeColumn.setCellValueFactory(new PropertyValueFactory<>("ratingType"));

		cbRatingType.setItems(FXCollections.observableArrayList("ALL", "MENTOR", "GROUP"));
		cbRatingType.setValue("ALL");

		cbRating.setItems(FXCollections.observableArrayList("ALL", "1", "2", "3", "4", "5"));
		cbRating.setValue("ALL");

		allRatings = ratingService.findAll();

		txtSearch.textProperty().addListener((observable, oldValue, newValue) -> filterRatings());
		cbRatingType.valueProperty().addListener((observable, oldValue, newValue) -> filterRatings());
		cbRating.valueProperty().addListener((observable, oldValue, newValue) -> filterRatings());

		ratingTable.setItems(FXCollections.observableArrayList(allRatings));
	}

	private void filterRatings() {
		String appointmentIdText = txtSearch.getText();
		String selectedRatingType = cbRatingType.getValue();
		String selectedRating = cbRating.getValue();

		List<Rating> filteredRatings = allRatings.stream().filter(rating -> {
			if (!appointmentIdText.isEmpty()) {
				try {
					int appointmentId = Integer.parseInt(appointmentIdText);
					if (rating.getAppointment().getAppointmentID() != appointmentId) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
			}
			if (!"ALL".equals(selectedRatingType) && !rating.getRatingType().equals(selectedRatingType)) {
				return false;
			}
			if (!"ALL".equals(selectedRating)) {
				try {
					int ratingValue = Integer.parseInt(selectedRating);
					if (rating.getRating() != ratingValue) {
						return false;
					}
				} catch (NumberFormatException e) {
					return false;
				}
			}
			return true;
		}).collect(Collectors.toList());

		ratingTable.setItems(FXCollections.observableArrayList(filteredRatings));
	}
}
