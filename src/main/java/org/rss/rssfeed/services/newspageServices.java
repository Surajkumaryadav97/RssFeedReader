package org.rss.rssfeed.services;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rss.rssfeed.Exceptions.NewsNotFetchedExceptions;
//import org.rss.rssfeed.Webview;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.rss.rssfeed.controller.Webview;

public class newspageServices {

    private static final Logger logger = LogManager.getLogger(newspageServices.class);

    public void fetchCategory(String category, String adapt, GridPane newsGrid) throws NewsNotFetchedExceptions {
        String Ctgry = category;
        fetchNewsData(Ctgry, adapt, newsGrid);
    }

    public void fetchNewsData(String category, String adapt, GridPane newsGrid) throws NewsNotFetchedExceptions {
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

                displayDataInLayout(adapt, sourcesArray, newsGrid);
            } else {
                logger.error("HTTP Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
            connection.disconnect();
        } catch (Exception e) {
            logger.error("Error in fetchNewsData is" + e);
            throw new NewsNotFetchedExceptions("News Not Fetched Error", e);
        }
    }

    private void displayDataInLayout(String layout, JsonArray sourcesArray, GridPane newsGrid) {
        switch (layout) {
            case "Tile View":
                displayInTileView(sourcesArray, newsGrid);
                break;
            case "Masonry View":
                displayInMasonryView(sourcesArray, newsGrid);
                break;
            case "Compact View":
                displayInCompactView(sourcesArray, newsGrid);
                break;
            default:
                displayInCardView(sourcesArray, newsGrid);
                break;
        }
    }

    private void displayInCardView(JsonArray sourcesArray, GridPane newsGrid) {

        newsGrid.getChildren().clear();

        int col = 0;
        int row = 0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
            String urlLink = sourceObject.get("url").getAsString();


            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                Webview webViewSample = new Webview();
                webViewSample.loadURL(urlLink);
            });

            link.setStyle("-fx-text-fill:#FFF4E0 ; -fx-font-weight: bold;");


            VBox card = new VBox(link);
            card.setStyle("-fx-background-color: #4D4D4D;" +
                    "-fx-background-radius: 6px; " +
                    "-fx-border-color: #FFF4E0; " +
                    "-fx-border-width: 1px; " +
                    "-fx-border-radius: 10px; " +
                    "-fx-padding: 6px; " +
                    "-fx-text-fill: #FFF4E0;"+
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

            card.setPrefWidth(200);
            card.setAlignment(Pos.CENTER);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), card);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            card.setOnMouseEntered(e -> {
                fadeIn.playFromStart();
            });




            newsGrid.add(card, row/14, row++);

        }
    }

    private void displayInTileView(JsonArray sourcesArray, GridPane newsGrid) {
        // Implement tile view layout logic
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


            link.setStyle("-fx-text-fill: #FFF4E0; -fx-font-weight: bold;");


            VBox card = new VBox(link);
            card.setStyle("-fx-background-color: #4D4D4D; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-border-color: #FFF4E0; " +
                    "-fx-border-width: 1px; " +
                    "-fx-border-radius: 10px; " +
                    "-fx-padding: 10px; " +
                    "-fx-text-fill: #FFF4E0;"+
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");

            card.setPrefWidth(200);
            card.setAlignment(Pos.CENTER);


            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), card);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            card.setOnMouseEntered(e -> {
                fadeIn.playFromStart();
            });

            newsGrid.add(card, col, row);

            col++;
            if (col >= 4) {
                col = 0;
                row++;
            }
        }
    }

    private void displayInMasonryView(JsonArray sourcesArray, GridPane newsGrid) {
        // Implement masonry view layout logic
        newsGrid.getChildren().clear();

        int col = 0;
        int row = 0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
            String urlLink = sourceObject.get("url").getAsString();


            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                Webview webViewSample = new Webview();
                webViewSample.loadURL(urlLink);
            });


            link.setStyle("-fx-text-fill: #FFF4E0; -fx-font-weight: bold;");


            VBox itemBox = new VBox(5);
            itemBox.getChildren().add(link);


            itemBox.setStyle("-fx-background-color: #4D4D4D; " +
                    "-fx-background-radius: 10px; " +
                    "-fx-border-color: #FFF4E0; " +
                    "-fx-border-width: 1px; " +
                    "-fx-border-radius: 10px; " +
                    "-fx-padding: 10px; " +
                    "-fx-text-fill: #FFF4E0;"+
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");


            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), itemBox);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            itemBox.setOnMouseEntered(e -> {
                fadeIn.playFromStart();
            });

            newsGrid.add(itemBox, col, row);


            row++;
            if (row >= 4) {
                row = 2;
                col+=1;

            }

        }
    }

    private void displayInCompactView(JsonArray sourcesArray, GridPane newsGrid) {
        // Implement compact view layout logic
        newsGrid.getChildren().clear();

        int row = 0;
        int col=0;
        for (JsonElement sourceElement : sourcesArray) {
            JsonObject sourceObject = sourceElement.getAsJsonObject();

            String name = sourceObject.get("name").getAsString();
            //String description = sourceObject.get("description").getAsString();
            String urlLink = sourceObject.get("url").getAsString();

            Hyperlink link = new Hyperlink(name);
            link.setOnAction(event -> {
                Webview webViewSample = new Webview();
                webViewSample.loadURL(urlLink);
            });

            link.setStyle("-fx-text-fill: #FFF4E0; -fx-font-weight: bold;");
            //Label descLabel = new Label(description);

            VBox itemBox = new VBox(5); // Adjust spacing as needed
            itemBox.getChildren().add(link);


            itemBox.setStyle("-fx-background-color: #4D4D4D; " +
                    "-fx-background-radius: 6px; " +
                    "-fx-border-color: #FFF4E0; " +
                    "-fx-border-width: 1px; " +
                    "-fx-border-radius: 10px; " +
                    "-fx-padding: 6px; " +

                    "-fx-text-fill: #FFF4E0;"+
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 10, 0, 0, 0);");

            itemBox.setPrefWidth(200); // Adjust the width as needed
            itemBox.setAlignment(Pos.CENTER);


            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), itemBox);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            itemBox.setOnMouseEntered(e -> {
                fadeIn.playFromStart();
            });


            newsGrid.add(itemBox, 0, ++row);
        }
    }
}

