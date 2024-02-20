package org.rss.rssfeed.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class loginController {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField enterPasswordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button userSignup;

    @FXML
    void cancel(ActionEvent event) {
        // Add logic to close the login window
        // This can be achieved by getting the reference to the current stage and closing it
        // For example:
        // Stage stage = (Stage) cancelButton.getScene().getWindow();
        // stage.close();
    }
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

//    public void switchScene(Scene newScene) {
//        Stage stage = (Stage) cancelButton.getScene().getWindow();
//        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
//        fadeOut.setFromValue(1);
//        fadeOut.setToValue(0);
//        fadeOut.setOnFinished(event -> {
//            stage.setScene(newScene);
//            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), stage.getScene().getRoot());
//            fadeIn.setFromValue(0);
//            fadeIn.setToValue(1);
//            fadeIn.play();
//        });
//        fadeOut.play();
////        logger.debug("Switching scene");
//    }
    @FXML
    void login(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = enterPasswordField.getText();

        // Add logic to authenticate the user
        // This can involve querying the database to check if the username and password match

        // For demonstration purposes, let's assume a simple authentication logic
        if ("ADMIN".equals(username) && "ADMIN".equals(password)) {
            loginMessageLabel.setText("Login successful");

        } else {
            loginMessageLabel.setText("Invalid username or password");
        }
    }

    @FXML
    void signup(ActionEvent event) {
        // Add logic to handle user signup
        // This can involve opening a new window for user registration
    }

    @FXML
    void loginButtonOnAction(ActionEvent actionEvent) throws SQLException, NoSuchAlgorithmException, IOException {
        String userName = usernameTextField.getText();
        String password = enterPasswordField.getText();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        // Prepare SQL statement to retrieve hashed password for the given username
        String sql = "SELECT Password FROM user WHERE userName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("Password");

                    // Verify the entered password against the hashed password from the database
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                        // Authentication successful
                        loginMessageLabel.setText("Login successful");
                        start(new Stage());

                        // Proceed with further actions (e.g., navigating to another scene)
                    } else {
                        // Authentication failed
                        loginMessageLabel.setText("Invalid username or password");
                    }
                } else {
                    // Username not found
                    loginMessageLabel.setText("Invalid username or password");
                }
            }
        } catch (SQLException e) {
            // Handle database errors
            e.printStackTrace();
        } finally {
            // Close connection
            connection.close();
        }
    }













}

