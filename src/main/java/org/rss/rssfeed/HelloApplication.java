package org.rss.rssfeed;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application  {




    @FXML
    private ImageView logoImageView;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 650);

        primaryStage.setScene(scene);
        primaryStage.setTitle("RFR");
        primaryStage.setResizable(false);
        primaryStage.show();

        // Pass the stage instance to the controller


    }

    public static void main(String[] args) {
        launch(args);
    }


}
