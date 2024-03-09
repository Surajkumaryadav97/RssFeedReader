package org.rss.rssfeed.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
    private Label usernameLabel;

    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView userImageView;


    // Method to initialize user information
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
//        firstNameLabel.setText("Suraj Suraj Suraj");



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File brandingFile = new File("images/logo_.png");
            Image branding = new Image(brandingFile.toURI().toString());
            logoImageView.setImage(branding);
//            logger.info("Image loading");

        } catch (Exception e) {
//            logger.error("Image loading error" + e);
        }
        try {
            File brandingFile = new File("images/image6.png");
            Image branding = new Image(brandingFile.toURI().toString());
            userImageView.setImage(branding);
//            logger.info("Image loading");

        } catch (Exception e) {
//            logger.error("Image loading error" + e);
        }
        loginController logincontroller=new loginController();
        String username=logincontroller.getUsername();
                fetchDataFromDatabase(username);


//        firstNameLabel.setText("Ravi Singh Suraj");

//        lastNameLabel.setText(userName);
//        usernameLabel.setText("Suraj");
//        techFeedLabel.setText(techfeedName);
//        healthFeedLabel.setText(healthfeedName);
    }

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

    public void cancel1(ActionEvent actionEvent) throws switchSceneException {

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view.fxml"));
            Scene scene=new Scene(fxmlLoader.load());
            RssContent htmlContent = fxmlLoader.getController();

            htmlContent.initialize(userName1,techFeed,healthFeed);

            stage.setScene(scene);
            stage.show();
            logger.info("Switching to Homepage");
        } catch (IOException ex) {
            System.out.println(ex);
            logger.error("Error in switching homepage" + ex);
            throw new switchSceneException("Error in NewspageContoller switching to Homepage in cancellayoutButton",ex);
        }



    }
    String userName1;
    String techFeed;
    String healthFeed;
    private void fetchDataFromDatabase(String userName) {
        try {
            // Connect to the database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            String query = "SELECT firstName, lastName, userName, techFeed, healthFeed FROM user WHERE userName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            System.out.println(userName+"in user profile");
            statement.setString(1, userName); // Set the username

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // If a user with the given username is found
            if (resultSet.next()) {
                // get data from the result set to assign the variables
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                userName1 = resultSet.getString("userName");
                 techFeed = resultSet.getString("techFeed");
                healthFeed = resultSet.getString("healthFeed");

                // set the values to the labels with the fetched data
                firstNameLabel.setText(firstName);
                lastNameLabel.setText(lastName);
                userName1Label.setText(userName1);
                techFeedLabel.setText(techFeed);
                healthFeedLabel.setText(healthFeed);
                userNameLabel.setText(userName1);
                userNameLabel1.setText(userName1);
            }

            // Close the all opening resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}