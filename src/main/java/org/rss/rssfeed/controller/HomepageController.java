package org.rss.rssfeed.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomepageController {

    @FXML
    private VBox selectedLayout;

    @FXML
    private Label newsContent;

    @FXML
    private void showLayout1() {
        // Implement layout 1
        selectedLayout.getChildren().clear();
        // Add UI components for layout 1
    }

    @FXML
    private void showLayout2() {
        // Implement layout 2
        selectedLayout.getChildren().clear();
        // Add UI components for layout 2
    }

    @FXML
    private void showLayout3() {
        // Implement layout 3
        selectedLayout.getChildren().clear();
        // Add UI components for layout 3
    }

    // Other methods and logic for fetching and displaying data
}

