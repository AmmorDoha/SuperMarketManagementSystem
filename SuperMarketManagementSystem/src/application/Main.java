package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private double x = 0;
	private double y = 0;
	@Override
	public void start(Stage primaryStage) {
		try {
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			
			root.setOnMousePressed((MouseEvent event) ->{
				x = event.getSceneX();
				y = event.getSceneY();
			});
			
			root.setOnMouseDragged((MouseEvent event) ->{
				primaryStage.setX(event.getScreenX() - x);
				primaryStage.setY(event.getScreenY() - y);
				
				primaryStage.setOpacity(.8);
			});
			
			root.setOnMouseReleased((MouseEvent event) ->{
				primaryStage.setOpacity(1);
			});
			
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
