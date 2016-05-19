package gui.javafx;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuiMain extends Application  {


	public static void main(String[] args){
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Conjugation Trainer");
		Button b = new Button("Nicer Button");
		StackPane root = new StackPane();
		root.getChildren().add(b);
		primaryStage.setScene(new Scene(root,300,250));
		
		primaryStage.show();
	}
	

}
