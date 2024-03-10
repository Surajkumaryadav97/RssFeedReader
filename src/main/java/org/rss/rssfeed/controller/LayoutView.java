package org.rss.rssfeed.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import org.rss.rssfeed.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class LayoutView {
    public static ScrollPane createMagazineView(ArrayList<NewsModel> newsList) {
        TilePane magazineContainer = new TilePane();
        magazineContainer.setPadding(new Insets(15));
        magazineContainer.setAlignment(Pos.CENTER); // Center content
        magazineContainer.setHgap(10);
        magazineContainer.setVgap(10);

        magazineContainer.setStyle("-fx-background-color: green;");

        for (NewsModel news : newsList) {
            Node magazineItem = createMagazineItem(news);
            magazineContainer.getChildren().add(magazineItem);
        }
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        double itemWidth = screenWidth * 0.3; // 30% of the screen width
        double itemHeight = screenHeight * 0.4; // 30% of the screen height


        magazineContainer.setMaxWidth(screenWidth * 0.5); // Set maximum width to 80% of screen width
        magazineContainer.setMaxHeight(screenHeight * 0.3); // Set maximum height to 80% of screen height
        magazineContainer.setPrefTileWidth(itemWidth); // Set preferred tile width
        magazineContainer.setPrefTileHeight(itemHeight); // Set preferred tile height


        ScrollPane scrollPane = new ScrollPane(magazineContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return scrollPane;
    }

    private static Node createMagazineItem(NewsModel news) {
        VBox magazineItem = new VBox(15);
        magazineItem.setAlignment(Pos.CENTER);
        magazineItem.setPadding(new Insets(5));
        magazineItem.setStyle("-fx-background-color: blue; -fx-border-color: green; -fx-border-radius: 5px;");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Text description = new Text(news.getDesc());

        description.setWrappingWidth(180);

        // Asynchronously load image from URL
        loadImage(news.getImage(), imageView);

        description.setOnMouseClicked(event -> {
            // Open WebView with the URL
            Webview webViewSample = new Webview();
            webViewSample.loadURL(news.getLink());
        });


        magazineItem.getChildren().addAll(imageView, description);

        return magazineItem;
    }

    private static void loadImage(String imageUrl, ImageView imageView) {
        // Asynchronously load image from URL
        Image image = new Image(imageUrl, true);
        imageView.setImage(image);
    }
}

