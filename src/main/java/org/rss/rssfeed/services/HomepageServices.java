package org.rss.rssfeed.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class HomepageServices {
    public final Logger logger = LogManager.getLogger(HomepageServices.class);







    public void initializeLogo(ImageView imageView, String imageName) {
        try {
            File brandingFile = new File("images/" + imageName);
            Image branding = new Image(brandingFile.toURI().toString());
            imageView.setImage(branding);
        } catch (Exception e) {
            logger.error("Error loading image: " + imageName, e);
        }
    }
}
