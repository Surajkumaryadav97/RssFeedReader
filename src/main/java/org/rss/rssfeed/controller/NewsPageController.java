package org.rss.rssfeed.controller;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rss.rssfeed.Exceptions.NewsNotFetchedExceptions;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.services.newspageServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;



public class NewsPageController extends Application implements Initializable {

    public final Logger logger = LogManager.getLogger(NewsPageController.class);
    public Button logout;
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

    @FXML
    private ImageView dashboard;

    @FXML
    String Ctgry;

    @FXML
    String grid;

    @FXML
    String list;

    @FXML
    String compact;

    @FXML
    private ImageView feedImageView;

    private String currentLayout;

    private newspageServices newspageServices = new newspageServices();

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

        try {
            File brandingFile = new File("images/dashboard.png");
            Image branding = new Image(brandingFile.toURI().toString());
            dashboard.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void fetchCategory(String category, String adapt) throws NewsNotFetchedExceptions {
        Ctgry = category;
        System.out.println(Ctgry);
        newspageServices.fetchCategory(Ctgry, adapt, newsGrid);
        System.out.println(Ctgry);
    }
    @FXML
    void showMasonryView(ActionEvent actionEvent) throws NewsNotFetchedExceptions {
        newspageServices.fetchCategory(Ctgry, "Masonry View", newsGrid);
    }

    @FXML
    void showTileView(ActionEvent actionEvent) throws NewsNotFetchedExceptions {
        newspageServices.fetchCategory(Ctgry, "Tile View", newsGrid);
    }

    @FXML
    void showCompactView(ActionEvent actionEvent) throws NewsNotFetchedExceptions {
        newspageServices.fetchCategory(Ctgry, "Compact View", newsGrid);
    }

    public void cancelLayoutButton(ActionEvent event) throws switchSceneException {

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Homepage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
           logger.info("Switching to Homepage");
        } catch (IOException ex) {
            System.out.println(ex);
            logger.error("Error in switching homepage" + ex);
            throw new switchSceneException("Error in NewspageContoller switching to Homepage in cancellayoutButton",ex);
        }

    }

    public void Logout(ActionEvent event) throws switchSceneException {
        try {
            Stage stage = (Stage) logout.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            logger.info("Switching to Homepage");
        } catch (IOException ex) {
            System.out.println(ex);
            logger.error("Error in switching homepage" + ex);
            throw new switchSceneException("Error in NewspageContoller switching to Homepage in cancellayoutButton",ex);
        }
    }
}




