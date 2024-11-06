package hsf301.fe.main;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	// Variables for dragging the window
    private double xOffset = 0;
    private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ToolBar toolBar = new ToolBar();

	        int height = 25;
	        toolBar.setPrefHeight(height);
	        toolBar.setMinHeight(height);
	        toolBar.setMaxHeight(height);
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/hsf301/fe/view/LoginUI.fxml"));
			Scene scene = new Scene(root);
			
			scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
			
			scene.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            });
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("SWP Mentor Booking System");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
