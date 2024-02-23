package org.rss.rssfeed.controller;





import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.rss.rssfeed.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController {

    @FXML
    private VBox selectedLayout;

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

    public void start(String category) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);

        newsPageController = fxmlLoader.getController();
        newsPageController.fetchCategory(category, " ");
        stage.show();
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

    String listView;
    public void showListView(ActionEvent actionEvent) {

    }
    String gridView;
    public void showGridView(ActionEvent actionEvent) {

    }
}
    // Other methods and logic for fetching and displaying data


