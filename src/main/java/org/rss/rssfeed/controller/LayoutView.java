package org.rss.rssfeed.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import org.rss.rssfeed.model.NewsModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LayoutView {







    //This function is adding magazineItem in magazineContainer
    public static ScrollPane createMagazineView(ArrayList<NewsModel> newsList) {
        TilePane magazineContainer = new TilePane();
        magazineContainer.setPadding(new Insets(15));
        magazineContainer.setAlignment(Pos.CENTER);
        magazineContainer.setHgap(10);
        magazineContainer.setVgap(10);

        magazineContainer.setStyle("-fx-background-color: #F7F6BB;-fx-border-radius: 20px;");

        for (NewsModel news : newsList) {
            Node magazineItem = createMagazineItem(news);
            magazineContainer.getChildren().add(magazineItem);
        }
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        double itemWidth = screenWidth * 0.3;
        double itemHeight = screenHeight * 0.4;


        magazineContainer.setMaxWidth(screenWidth * 0.5);
        magazineContainer.setMaxHeight(screenHeight * 0.7);
        magazineContainer.setPrefTileWidth(itemWidth);
        magazineContainer.setPrefTileHeight(itemHeight);


        ScrollPane scrollPane = new ScrollPane(magazineContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }
    //This function is adding items like images and description and handling OnClickEvent on desc to open browser
    private static Node createMagazineItem(NewsModel news) {
        VBox magazineItem = new VBox(15);
        magazineItem.setAlignment(Pos.CENTER);
        magazineItem.setPadding(new Insets(5));
        magazineItem.setStyle("-fx-background-color: #CCD3CA; -fx-border-color: #000000; -fx-border-radius: 20px;");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Text description = new Text(news.getDesc());

        description.setWrappingWidth(180);
        description.setStyle("-fx-font-weight: bold;");

        // asynchronous task
        loadImage(news.getImage(), imageView);

        description.setOnMouseClicked(event -> {

            Webview webViewSample = new Webview();
            webViewSample.loadURL(news.getLink());
        });


        magazineItem.getChildren().addAll(imageView, description);

        return magazineItem;
    }
    //This function is used to loadImage from ImageUrl as there is only link of image
    private static void loadImage(String imageUrl, ImageView imageView) {

        Image image = new Image(imageUrl, true);
        imageView.setImage(image);
    }



}

