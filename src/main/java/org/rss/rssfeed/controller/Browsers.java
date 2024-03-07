package org.rss.rssfeed.controller;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Browsers extends BorderPane {
    final WebView browser = new WebView();

    final WebEngine webEngine = browser.getEngine();
    final Button backButton = new Button("Back");
    final Button reloadButton = new Button("Reload");

    public Browsers() {
        this(Webview.Url);
        System.out.println(Webview.Url);
    }

    public Browsers(String url) {


        getStyleClass().add("browser");

        webEngine.load(url);


        backButton.getStyleClass().add("navigation-button");
        reloadButton.getStyleClass().add("reload-button");

        HBox buttonsContainer = new HBox(10);
        buttonsContainer.getChildren().addAll(backButton,  reloadButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        setTop(buttonsContainer);
        setCenter(browser);


        backButton.setStyle("-fx-background-color:  #0B60B0; " +
                "-fx-text-fill: white; " +
                "-fx-border-radius: 5px; " +
                "-fx-border-color: #388E3C;");

        backButton.setOnMouseEntered(event -> {
            backButton.setStyle("-fx-background-color: #07588A; " +
                    "-fx-text-fill: white; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-border-color: #07588A;");
        });

        backButton.setOnMouseExited(event -> {
            backButton.setStyle("-fx-background-color: #0B60B0; " +
                    "-fx-text-fill: white; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-border-color: #388E3C;");
        });

        reloadButton.setStyle("-fx-background-color: #4CAF50 ; " +
                "-fx-text-fill: white; " +
                "-fx-border-radius: 5px; " +
                "-fx-border-color: #0077A3;");

        reloadButton.setOnMouseEntered(event -> {
            reloadButton.setStyle("-fx-background-color: #45a049; " +
                    "-fx-text-fill: white; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-border-color: #45a049;");
        });

        reloadButton.setOnMouseExited(event -> {
            reloadButton.setStyle("-fx-background-color: #4CAF50; " +
                    "-fx-text-fill: white; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-border-color: #0077A3;");
        });




        backButton.setOnAction(event -> {
            if (webEngine.getHistory().getCurrentIndex() > 0) {
                webEngine.getHistory().go(-1);
            }
        });

        reloadButton.setOnAction(event -> {
            webEngine.reload();
        });
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
