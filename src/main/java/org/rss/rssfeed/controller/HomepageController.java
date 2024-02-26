package org.rss.rssfeed.controller;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.rss.rssfeed.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController {

    @FXML
    private VBox buttonnews;


    @FXML
    private ChoiceBox<String> layoutChoiceBox;
    private NewsPageController newsPageController;


    //    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // Initialize method
//    }
    @FXML
    private Label newsContent;
//
//    @FXML
//    private void showLayout1() {
//        // Implement layout 1
//        selectedLayout.getChildren().clear();
//        // Add UI components for layout 1
//    }
//public void switchScene(Scene newScene) {
//
//    Stage stage = (Stage) cancelButton.getScene().getWindow();
//    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
//    fadeOut.setFromValue(1);
//    fadeOut.setToValue(0);
//    fadeOut.setOnFinished(event -> {
//        stage.setScene(newScene);
//        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
//        fadeIn.setFromValue(0);
//        fadeIn.setToValue(1);
//        fadeIn.play();
//    });
//    fadeOut.play();
////        logger.debug("Switching scene");

private Stage stage;

public void setStage(Stage stage) {
    this.stage = stage;
}
    public void start(String category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewsPage.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        NewsPageController newsPageController = fxmlLoader.getController();
        newsPageController.fetchCategory(category, " ");
        switchScene(newScene);
    }
    public void switchScene(Scene newScene) {
        Stage stage = (Stage) buttonnews.getScene().getWindow();
        stage.setScene(newScene);
//        logger.debug("Switching scene");
    }

    @FXML
    private void showLayout2() {
        // Implement layout 2
        buttonnews.getChildren().clear();
        // Add UI components for layout 2
    }

    @FXML
    private void showLayout3() {
        // Implement layout 3
        buttonnews.getChildren().clear();
        // Add UI components for layout 3
    }

    @FXML
    private void handleTechnologyButtonClick(ActionEvent event) throws IOException {
//          if (newsPageController != null) {
//              newsPageController.fetchNewsData("technology");
        start("technology");
    }

    //
    @FXML
    private void handleHealthButtonClick(ActionEvent event) throws IOException {
        start("health");
    }



}
    // Other methods and logic for fetching and displaying data


