package org.rss.rssfeed;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello!");
        primaryStage.show();

        // Pass the stage instance to the controller


    }

    public static void main(String[] args) {
        launch(args);
    }
}
