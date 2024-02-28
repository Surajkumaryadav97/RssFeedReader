package org.rss.rssfeed.controller;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.stage.Stage;
import org.rss.rssfeed.Exceptions.NewsNotFetchedExceptions;
import org.rss.rssfeed.Exceptions.switchSceneException;
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

    @FXML
    private ImageView userImageView1;

    @FXML
    private ImageView logoImageView1;


    private String currentLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the hostServices
        this.hostServices = getHostServices();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File brandingFile = new File("images/logo_.png");
            Image branding = new Image(brandingFile.toURI().toString());
            logoImageView1.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/User.png");
            Image branding = new Image(brandingFile.toURI().toString());
            userImageView1.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    String Ctgry;
    String grid;
    String list;
    String compact;

    void fetchCategory(String category, String adapt) throws NewsNotFetchedExceptions {
        Ctgry = category;
        System.out.println(Ctgry);
        fetchNewsData(Ctgry, adapt);
        System.out.println(Ctgry);
    }

    void fetchNewsData(String category, String adapt) throws NewsNotFetchedExceptions {
        String apikey = "d9a3f37be02a41f7af241478654d7ba8";

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


                displayDataInLayout(adapt, sourcesArray);
            } else {
                System.out.println("HTTP Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            loggerController.logger.error("Error in fetchNewsData is" + e);
            throw new NewsNotFetchedExceptions("News Not Fetched Error", e);
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
            String urlLink = sourceObject.get("url").getAsString();

            // Create a Hyperlink with the name
            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                Webview webViewSample = new Webview();
                webViewSample.loadURL(urlLink);
            });

            // Customize the style of the Hyperlink
            link.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

            // Create a VBox to hold the Hyperlink
            VBox card = new VBox(link);
            card.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10px; -fx-background-color: #f0f0f0;");

            // Add the VBox to the grid
            newsGrid.add(card, col, row);

            // Adjust column and row for the next item
            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
    }

    private void displayInMasonryView(JsonArray sourcesArray) {
        // Clear existing data
        newsGrid.getChildren().clear();

        int col = 0;
        int row = 0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
            String urlLink = sourceObject.get("url").getAsString();

            // Create a Hyperlink with the name
            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                Webview webViewSample = new Webview();
                webViewSample.loadURL(urlLink);
            });

            // Customize the style of the Hyperlink
            link.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

            // Create a VBox to hold the Hyperlink
            VBox itemBox = new VBox(5); // Adjust spacing as needed
            itemBox.getChildren().add(link);

            // Set layout properties for VBox
            itemBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10px;-fx-background-color: #f0f0f0;");

            // Add the VBox to the GridPane
            newsGrid.add(itemBox, col, row);

            // Adjust row and column for the next item
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
                Webview webViewSample = new Webview();
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


    public void showMasonryView(ActionEvent actionEvent) throws NewsNotFetchedExceptions {
        grid = "Masonry View";
        fetchCategory(Ctgry, grid);

    }

    public void showTileView(ActionEvent actionEvent) throws NewsNotFetchedExceptions {
        list = "Tile View";
        fetchCategory(Ctgry, list);
    }

    public void showCompactView(ActionEvent actionEvent) throws NewsNotFetchedExceptions {
        compact = "Compact View";
        fetchCategory(Ctgry, compact);

    }

    public void cancelLayoutButton(ActionEvent event) throws switchSceneException {

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            loggerController.logger.info("Switching to Homepage");
        } catch (IOException ex) {
            System.out.println(ex);
            loggerController.logger.error("Error in switching homepage" + ex);
            throw new switchSceneException("Error in NewspageContoller switching to Homepage in cancellayoutButton",ex);
        }

    }
}




