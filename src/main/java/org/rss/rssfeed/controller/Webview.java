package org.rss.rssfeed.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Webview extends Application {
    public Scene scene;
    static String Url;


    @Override
    public void start(Stage stage) {

        stage.setTitle("Web View");

        scene = new Scene(new Browsers(), 850, 650, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");

        stage.show();

    }

    public void loadURL(String url) {


        Url=url;
        start(new Stage());

    }
    public static void main(String[] args) {
        launch(args);
    }



}

