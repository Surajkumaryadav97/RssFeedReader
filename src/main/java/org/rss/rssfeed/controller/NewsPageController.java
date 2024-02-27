package org.rss.rssfeed.controller;

//
//import javafx.application.Application;
//import javafx.application.HostServices;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.Hyperlink;
//import javafx.scene.control.Label;
//import javafx.scene.layout.GridPane;
//import java.net.URL;
//import java.util.ResourceBundle;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import javafx.stage.Stage;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//
//public class NewsPageController extends Application implements Initializable {
//    private HostServices hostServices;
//    @FXML
//    private ChoiceBox<String> layoutChoiceBox;
//
//    @FXML
//    private GridPane newsGrid;
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        // Initialize the hostServices
//        this.hostServices = getHostServices();
//        // You can now use hostServices to interact with the host environment
//    }
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }
//
//    void fetchNewsData(String category) {
//        String apikey="d9a3f37be02a41f7af241478654d7ba8";
//        try {
////            String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8);
//            URL url = new URL("https://newsapi.org/v2/top-headlines/sources?category=" + category + "&apiKey=" + apikey);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
////d9a3f37be02a41f7af241478654d7ba8
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//                JsonParser jsonParser = new JsonParser();
//                JsonObject rootObject = jsonParser.parse(response.toString()).getAsJsonObject();
//                JsonArray sourcesArray = rootObject.getAsJsonArray("sources");
//
//                int row = 0;
//
//                for (JsonElement sourceElement : sourcesArray) {
//                    JsonObject sourceObject = sourceElement.getAsJsonObject();
//
//                    String name = sourceObject.get("name").getAsString();
//                    String description = sourceObject.get("description").getAsString();
//                    String urlLink = sourceObject.get("url").getAsString();
//                    System.out.println(urlLink);
//                    Hyperlink link = new Hyperlink(name);
//                    link.setOnAction(event -> {
//                        WebViewSample webViewSample = new WebViewSample();
//                        webViewSample.loadURL(urlLink);
//                    });
//
//                    Label descLabel = new Label(description);
//
//                    newsGrid.addRow(row, link, descLabel);
//                    row++;
//                }
//            } else {
//                System.out.println("HTTP Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
//            }
//            connection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public void handleLayoutSelection(ActionEvent actionEvent) {
//        String selectedLayout = layoutChoiceBox.getValue();
//        if (selectedLayout != null) {
//            switch (selectedLayout) {
//                case "List View":
//                    // Call method to display data in list view
//                    displayInListView();
//                    break;
//                case "Grid View":
//                    // Call method to display data in grid view
//                    displayInGridView();
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private void displayInListView() {
//        // Implement the logic to display data in list view
//        // Clear existing data
//        newsGrid.getChildren().clear();
//        // Add new data in list view format
//        // Example:
//
//        Label label = new Label("List View");
//        newsGrid.add(label, 0, 0);
//    }
//
//    private void displayInGridView() {
//        // Implement the logic to display data in grid view
//        // Clear existing data
//        newsGrid.getChildren().clear();
//        // Add new data in grid view format
//        // Example:
//        Label label = new Label("Grid View");
//        newsGrid.add(label, 0, 0);
//
//    }
//}


import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.stage.Stage;
import org.rss.rssfeed.HelloApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class NewsPageController extends Application implements Initializable {
    private HostServices hostServices;

    @FXML
    private GridPane newsGrid;

    @FXML
    private Button cancel;

    @FXML
    private Button cancelButton;

    @FXML
    private VBox newsbuttons;

//    @FXML
//    private ChoiceBox<String> layoutChoiceBox;


    private String currentLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the hostServices
        this.hostServices = getHostServices();
        // You can now use hostServices to interact with the host environment
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize layout choice box

    }
    String Ctgry;
    String grid;
    String list;
    String compact;
    void fetchCategory(String category, String adapt){
        Ctgry=category;
        System.out.println(Ctgry);
        fetchNewsData(Ctgry, adapt);
        System.out.println(Ctgry);
    }

    void fetchNewsData(String category, String adapt) {
        String apikey = "d9a3f37be02a41f7af241478654d7ba8";
//        String selectedLayout = value;
        try {
            URL url = new URL("https://newsapi.org/v2/top-headlines/sources?category=" + category + "&apiKey=" + apikey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonParser jsonParser = new JsonParser();
                JsonObject rootObject = jsonParser.parse(response.toString()).getAsJsonObject();
                JsonArray sourcesArray = rootObject.getAsJsonArray("sources");

//                // Clear existing data in the news grid
//                newsGrid.getChildren().clear();

                // Display data in the selected layout
//                currentLayout=value;
                displayDataInLayout(adapt, sourcesArray);
            } else {
                System.out.println("HTTP Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayDataInLayout(String layout, JsonArray sourcesArray) {
        switch (layout) {
            case "Tile View":
                // Display data in list view
                displayInTileView(sourcesArray);
                break;
            case "Masonry View":
                // Display data in grid view
                displayInMasonryView(sourcesArray);
                break;

            case "Compact View":
                displayInCompactView(sourcesArray);
            default:
                break;
        }
    }

    private void displayInTileView(JsonArray sourcesArray) {
        newsGrid.getChildren().clear();

        int col = 0;
        int row = 0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
//            String description = sourceObject.get("description").getAsString();
            String urlLink = sourceObject.get("url").getAsString();

            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                WebViewSample webViewSample = new WebViewSample();
                webViewSample.loadURL(urlLink);
            });

//            Label descLabel = new Label(description);

            link.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            // Add each item to the grid in a tile view format
            newsGrid.add(link, col, row);
//            newsGrid.add( descLabel,col, row + 1);

            // Adjust column and row for the next item
            col += 2;
            if (col >= 4) {
                col = 0;
                row += 2;
            }
        }
    }

    private void displayInMasonryView(JsonArray sourcesArray) {

            // Clear existing data

            // Clear existing data
        newsGrid.getChildren().clear();

        int col = 0;
        int row = 0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
            String description = sourceObject.get("description").getAsString();
            String urlLink = sourceObject.get("url").getAsString();

            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                WebViewSample webViewSample = new WebViewSample();
                webViewSample.loadURL(urlLink);
            });

//            Label descLabel = new Label(description);

            VBox itemBox = new VBox(5); // Adjust spacing as needed
            itemBox.getChildren().addAll(link);

            link.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
            newsGrid.add(itemBox, col, row);
            row++;
            if (row >= 2) {
                row = 0;
                col++;
            }
        }


    }
    private void displayInCompactView(JsonArray sourcesArray) {
        // Clear existing data
        newsGrid.getChildren().clear();

        int row = 0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
//            String description = sourceObject.get("description").getAsString();
            String urlLink = sourceObject.get("url").getAsString();

            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                WebViewSample webViewSample = new WebViewSample();
                webViewSample.loadURL(urlLink);
            });

//            Label descLabel = new Label(description);

            VBox itemBox = new VBox(5); // Adjust spacing as needed
            itemBox.getChildren().add(link);

            // Set layout properties for VBox
            link.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

            // Add VBox to GridPane
            newsGrid.add(itemBox, 0, row++);
        }
    }




//    public void handleLayoutSelection(ActionEvent actionEvent) {
//         fetchCategory(Ctgry);
//    }

    public void showMasonryView(ActionEvent actionEvent) {
        grid = "Masonry View";
        fetchCategory(Ctgry, grid);

    }

    public void showTileView(ActionEvent actionEvent) {
        list = "Tile View";
        fetchCategory(Ctgry, list);
    }

    public void showCompactView(ActionEvent actionEvent) {
        compact="Compact View";
        fetchCategory(Ctgry, compact);

    }

    public void cancelLayoutButton(ActionEvent event) throws IOException {

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

//    public void handleLayoutSelection(ActionEvent actionEvent) {
//        displayDataInLayout("List View",);
//    }
}



