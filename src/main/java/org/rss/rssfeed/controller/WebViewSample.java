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

public class WebViewSample extends Application {
    public Scene scene;

   static String Url;
    @Override
    public void start(Stage stage) {
        // Create the scene
        stage.setTitle("Web View");

        scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
        stage.setScene(scene);
       scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();
    }

    public void loadURL(String url) {
//        browser.loadURL(url);

          Url=url;
          start(new Stage());

    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Browser extends Region {


    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser(){
       this(WebViewSample.Url);
       System.out.println(WebViewSample.Url);
    }

    public Browser(String url) {
        // Apply the styles
        getStyleClass().add("browser");
        // Add the WebView to the scene


        // Set user agent to Google Chrome
//        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36";


        // Load the URL
        webEngine.load(url);

        // Create scene


        getChildren().add(browser);


    }



    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 750;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 500;
    }
}

