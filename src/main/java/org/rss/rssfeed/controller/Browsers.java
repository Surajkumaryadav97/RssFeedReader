package org.rss.rssfeed.controller;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Button;
import org.rss.rssfeed.controller.Webview;

public class Browsers extends BorderPane {
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    final Button backButton = new Button("Back");
    final Button reloadButton = new Button("Reload");

    public Browsers() {
        this(Webview.Url); // Default URL or about:blank
    }

    public Browsers(String url) {
        getStyleClass().add("browser");

        // Wrap the browser WebView inside a ScrollPane
        ScrollPane scrollPane = new ScrollPane(browser);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // Ensure the scrollbars are enabled from the start

        // Set minimum width and height for WebView
        browser.setMinWidth(750);
        browser.setMinHeight(500);

        // Load the URL
        clearBrowser();
        webEngine.load(url);

        backButton.getStyleClass().add("navigation-button");
        reloadButton.getStyleClass().add("reload-button");

        HBox buttonsContainer = new HBox(10);
        buttonsContainer.getChildren().addAll(backButton, reloadButton);
        buttonsContainer.setAlignment(Pos.CENTER_RIGHT);
        setTop(buttonsContainer);

        backButton.setStyle("-fx-background-color: #0B60B0; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: #388E3C;");
        reloadButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: #0077A3;");

        backButton.setOnMouseEntered(event -> backButton.setStyle("-fx-background-color: #07588A; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: #07588A;"));
        backButton.setOnMouseExited(event -> backButton.setStyle("-fx-background-color: #0B60B0; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: #388E3C;"));

        reloadButton.setOnMouseEntered(event -> reloadButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: #45a049;"));
        reloadButton.setOnMouseExited(event -> reloadButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-border-radius: 5px; -fx-border-color: #0077A3;"));

        backButton.setOnAction(event -> {
            if (webEngine.getHistory().getCurrentIndex() > 0) {
                webEngine.getHistory().go(-1);
            }
        });

        reloadButton.setOnAction(event -> webEngine.reload());

        setCenter(scrollPane);
    }

    private void clearBrowser() {
        // Clear cache, history, cookies, etc.
        webEngine.loadContent(""); // Clear the content
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
