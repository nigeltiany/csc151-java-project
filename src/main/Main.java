package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.main.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main/main.fxml"));

        AnchorPane mainPane = loader.load();
        MainController mainController = loader.getController();
        mainController.loadMainView();

        Scene scene = new Scene(mainPane, 1104 , 720);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Event Manager");
        primaryStage.setMinWidth(1104);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
