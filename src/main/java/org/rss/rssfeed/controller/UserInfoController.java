package org.rss.rssfeed.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.rss.rssfeed.db.DatabaseConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label techFeedLabel;

    @FXML
    private Label healthFeedLabel;

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

        // Set the text of the labels here
//        firstNameLabel.setText("Ravi Singh Suraj");

//        lastNameLabel.setText(userName);
//        usernameLabel.setText("Suraj");
//        techFeedLabel.setText(techfeedName);
//        healthFeedLabel.setText(healthfeedName);
    }

    // Additional methods can be added here if needed



}
