package org.rss.rssfeed.controller;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Webview extends Application {
    public Scene scene;

    static String Url;
    @Override
    public void start(Stage stage) {
        // Create the scene
        stage.setTitle("Web View");

        scene = new Scene(new Browsers(), 750, 500, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();
    }

    public void loadURL(String url) {

        //set the url
        Url=url;
        start(new Stage());

    }


}

