package org.rss.rssfeed;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

public class RssApplication extends Application  {


public static final Logger logger = LogManager.getLogger(RssApplication.class);



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
