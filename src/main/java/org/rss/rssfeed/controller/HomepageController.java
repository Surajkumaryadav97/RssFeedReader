package org.rss.rssfeed.controller;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {


    @FXML
    private VBox buttonnews;

    @FXML
    private Button cancel;


    @FXML
    private Label usernameLabel;

    private Stage stage;

    @FXML
    private ImageView userImageView;

    @FXML
    private ImageView logoImageView;
    @FXML
    private ImageView health1ImageView;

    @FXML
    private ImageView health2ImageView;

    @FXML
    private ImageView tech1ImageView;

    @FXML
    private ImageView tech2ImageView;



public void setStage(Stage stage) {
    this.stage = stage;
}

//it is used to switchscreen btw two stage
public void start (String category) throws switchSceneException {

    try {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewsPage.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        NewsPageController newsPageController = fxmlLoader.getController();
        newsPageController.fetchCategory(category, " ");
        switchScene(newScene);
    } catch (Exception e){
        throw new switchSceneException("Error in switching newspage", e);
    }
    }

    public void switchScene(Scene newScene) {
        Stage stage = (Stage) buttonnews.getScene().getWindow();
        stage.setScene(newScene);

    }



     String firstname="";
    public void getUserName(String username){
    System.out.println(username);
        firstname=username;
    }

  @FXML
    public void initialize(){

        usernameLabel.setText(firstname);

    }

    @FXML
    private void handleTechnologyButtonClick(ActionEvent event) throws switchSceneException {
        //call the start method so that TechNews can be rendered
        start("technology");
    }



    //
    @FXML
    private void handleHealthButtonClick(ActionEvent event) throws switchSceneException {
        //call the start method so that HealthNews can be rendered
        start("health");
    }


    public void cancel(ActionEvent event) throws switchSceneException{

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            loggerController.logger.debug("Switching to login");
        } catch (Exception ex) {
            System.out.println(ex);
            loggerController.logger.error("Error in switching to login" + ex);
            throw new switchSceneException("Error in Homepage cancel button" , ex);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File brandingFile = new File("images/logo_.png");
            Image branding = new Image(brandingFile.toURI().toString());
            logoImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/User.png");
            Image branding = new Image(brandingFile.toURI().toString());
            userImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/health1.png");
            Image branding = new Image(brandingFile.toURI().toString());
            health1ImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/health3.png");
            Image branding = new Image(brandingFile.toURI().toString());
            health2ImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/tech3.png");
            Image branding = new Image(brandingFile.toURI().toString());
            tech1ImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/tech4.png");
            Image branding = new Image(brandingFile.toURI().toString());
            tech2ImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



