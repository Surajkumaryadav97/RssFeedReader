package org.rss.rssfeed.controller;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<S> extends TableCell<S, String> {

    private final ImageView imageView;

    public ImageTableCell() {
        this.imageView = new ImageView();
        this.imageView.setFitHeight(100);
        this.imageView.setPreserveRatio(true);
    }

    @Override
    protected void updateItem(String imageUrl, boolean empty) {
        super.updateItem(imageUrl, empty);

        if (empty || imageUrl == null) {
            setGraphic(null);
        } else {
            Image image = new Image(imageUrl);
            imageView.setImage(image);
            setGraphic(imageView);
        }
    }
}
