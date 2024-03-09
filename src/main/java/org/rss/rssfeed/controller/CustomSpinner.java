package org.rss.rssfeed.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CustomSpinner {

    @FXML
    private ProgressIndicator progressIndicator;

    public void showLoader() {
        progressIndicator.setVisible(true);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    public void hideLoader() {
        progressIndicator.setVisible(false);
    }
}