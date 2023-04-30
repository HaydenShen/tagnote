package ca.unb.cs3035.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class TagnoteApp extends Application {
    private static Stage primaryStage;
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Boolean isSplashLoaded = false;

    @Override
    public void start(Stage stage) throws IOException {
        setPrimaryStage(stage);
        FXMLLoader fxmlLoader1 = new FXMLLoader(TagnoteApp.class.getResource("tagnote-view.fxml"));

        Scene scene = new Scene(fxmlLoader1.load(), 600, 400);

        stage.setTitle("TagNote");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
