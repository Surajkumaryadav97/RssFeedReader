package org.rss.rssfeed;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application  {


public static final Logger logger = LogManager.getLogger(HelloApplication.class);

    @FXML
    private ImageView logoImageView;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("RFR");
        primaryStage.setResizable(false);
        primaryStage.show();
       logger.debug("Scene switching");



    }

    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        launch();
    }


}
