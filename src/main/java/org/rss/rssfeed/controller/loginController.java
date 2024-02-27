package org.rss.rssfeed.controller;



import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;


public class loginController implements Initializable {

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
    private ImageView loginImageVeiw;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File brandingFile = new File("../../../images/login-removebg-preview.png");
            Image branding = new Image(brandingFile.toURI().toString());
            loginImageVeiw.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    HomepageController hg = new HomepageController();

    @FXML
    public void cancel(ActionEvent event) throws IOException {

        try {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
    public void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Homepage.fxml"));
       switchScene(new Scene(fxmlLoader.load()));
    }

    public void switchScene(Scene newScene) {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(newScene);
//        logger.debug("Switching scene");
    }
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
        try {
            Stage stage = (Stage) userSignup.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void loginButtonOnAction(ActionEvent actionEvent) throws SQLException, NoSuchAlgorithmException, IOException {
        String userName = usernameTextField.getText();
        String password = enterPasswordField.getText();

        if(userName.isEmpty() || password.isEmpty()){
            loginMessageLabel.setText("Enter username or password");
            return;
        }
  ///
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        // Prepare SQL statement to retrieve hashed password for the given username
        String sql = "SELECT Password,firstName FROM user WHERE userName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("Password");
                    String username= resultSet.getString("firstName");
                    hg.getUserName(username);
                    // Verify the entered password against the hashed password from the database
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {

                        // Authentication successful
                        loginMessageLabel.setText("Login successful");
//                        start(new Stage());
                          start();

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

