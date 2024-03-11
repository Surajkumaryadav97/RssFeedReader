package org.rss.rssfeed.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.rss.rssfeed.HelloApplication.logger;

public class UserInfoController implements Initializable {

    @FXML
    private Label userNameLabel;
    @FXML
    private Label userNameLabel1;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label userName1Label;

    @FXML
    private Label techFeedLabel;

    @FXML
    private Label healthFeedLabel;


    @FXML
    private Button cancel;


    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView userImageView;






    String userName;
    String firstname;
    String techfeedName;
    String healthfeedName;
    public void initializeUserInfo( String username,String firstName,String techFeed,String healthFeed) {
        System.out.println(username);
        System.out.println(firstName);
        System.out.println(techFeed);
        System.out.println(healthFeed);

        firstname=firstName;
        userName=username;
        techfeedName=techFeed;
        healthfeedName=healthFeed;




    }

    //This function runs initially and loading all images to show on page, without this you cant load image
    //So,it is necessary to load images in Intialize method.


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            File brandingFile = new File("images/logo_.png");
            Image branding = new Image(brandingFile.toURI().toString());
            logoImageView.setImage(branding);


        } catch (Exception e) {

        }
        try {
            File brandingFile = new File("images/image6.png");
            Image branding = new Image(brandingFile.toURI().toString());
            userImageView.setImage(branding);


        } catch (Exception e) {

        }
        loginController logincontroller=new loginController();
        String username=logincontroller.getUsername();
                fetchDataFromDatabase(username);


    }

    //This function is used to switch to Login Page on Clicking to Logout button
    public void cancel(ActionEvent actionEvent) throws switchSceneException {

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            logger.debug("Switching to login");
        } catch (Exception ex) {
            System.out.println(ex);
            logger.error("Error in switching to login" + ex);
            throw new switchSceneException("Error in Homepage cancel button" , ex);
        }
    }
   //This function is used to go back to rssContent(where all data is rendering)
    //Also handling Loader.
    public void cancel1(ActionEvent actionEvent) throws switchSceneException {


        try {

            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view.fxml"));
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setStyle("-fx-progress-color: green; -fx-min-width: 150px; -fx-min-height: 150px;-fx-background-color: black;");
            Stage loaderStage = new Stage();
            loaderStage.initStyle(StageStyle.TRANSPARENT);
            loaderStage.setResizable(false);
            loaderStage.initModality(Modality.APPLICATION_MODAL);
            loaderStage.setScene(new Scene(new StackPane(progressIndicator), Color.TRANSPARENT));
            loaderStage.show();
            Rectangle overlay = new Rectangle();
            overlay.setFill(Color.rgb(0, 0, 0, 0.8));
            overlay.widthProperty().bind(cancel.getScene().widthProperty());
            overlay.heightProperty().bind(cancel.getScene().heightProperty());
            ((Pane) cancel.getScene().getRoot()).getChildren().add(overlay);


            new Thread(() -> {
                try {

                    Scene newScene = new Scene(fxmlLoader.load());

                    RssContent htmlContent = fxmlLoader.getController();
                    htmlContent.initialize(userName1,techFeed,healthFeed,"");


                    Platform.runLater(() -> {
                        stage.setScene(newScene);
                        stage.show();
                        loaderStage.close();
                    });
                } catch (Exception ex) {

                    ex.printStackTrace();
                    loaderStage.close();
                }
            }).start();
        } catch (Exception ex) {

            throw new switchSceneException("Error in loginController start method switch to Homepage", ex);
        }



    }
    String userName1;
    String techFeed;
    String healthFeed;

    //This function is fetching user Crendentials from its userName
    private void fetchDataFromDatabase(String userName) {
        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            String query = "SELECT firstName, lastName, userName, techFeed, healthFeed FROM user WHERE userName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            System.out.println(userName+"in user profile");
            statement.setString(1, userName);


            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {

                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                userName1 = resultSet.getString("userName");
                 techFeed = resultSet.getString("techFeed");
                healthFeed = resultSet.getString("healthFeed");


                firstNameLabel.setText(firstName);
                lastNameLabel.setText(lastName);
                userName1Label.setText(userName1);
                techFeedLabel.setText(techFeed);
                healthFeedLabel.setText(healthFeed);
                userNameLabel.setText(userName1);
                userNameLabel1.setText(userName1);
            }


            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}