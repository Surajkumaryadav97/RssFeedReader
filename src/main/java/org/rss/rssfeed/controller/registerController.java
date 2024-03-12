package org.rss.rssfeed.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.rss.rssfeed.Exceptions.sqlException;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.RssApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    public final Logger logger = LogManager.getLogger(registerController.class);
    public Label registerMessageLabel;
    @FXML
    private TextField reg_Fname;

    @FXML
    private Button login;

    @FXML
    private TextField reg_Lname;

    @FXML
    private ImageView registerImageView;

    @FXML
    private TextField reg_Uname;

    @FXML
    private PasswordField reg_password;

    @FXML
    private ComboBox<String> isAdmin;

    @FXML
    private Button regButton;

    @FXML
    private Button cancelButton;

   //This method is used to register the new user and saving the credentials to Database

    @FXML
    public void register() throws sqlException {
        String firstName = reg_Fname.getText();
        String lastName = reg_Lname.getText();
        String userName = reg_Uname.getText();
        String password = reg_password.getText();
        String role = "NORMAL";



        if(firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty()
        || password.isEmpty()) {
            registerMessageLabel.setText("All Fields are Required");
            logger.debug("Something missing in registering User");
            return;
        }

        if (userName.contains("@")) {
            registerMessageLabel.setText("Username cannot contain '@'");
            logger.debug("Invalid username format");
            return;
        }


        String Password = hashPassword(password);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String sql = "INSERT INTO user (firstName, lastName, userName, Password, role,techFeed,healthFeed) VALUES (?, ?, ?, ?, ?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, Password);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, "");
            preparedStatement.setString(7, "");



            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                System.out.println("User registered successfully!");
                reg_Fname.clear();
                reg_Lname.clear();
                reg_Uname.clear();
                reg_password.clear();
                registerMessageLabel.setText("User registered successfully!");

            } else {
                System.out.println("Failed to register user.");
            }

            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error registering user: " + e.getMessage());
            registerMessageLabel.setText("UserName is already exist!!");
            reg_Fname.clear();
            reg_Lname.clear();
            reg_Uname.clear();
            reg_password.clear();
            throw new sqlException("Error in registering user", e);
        }
        finally{
            try {
                connection.close();
            }
            catch(Exception ex){
                throw new sqlException("Error in closing the DB connection", ex);
            }
        }

    }
    //this function is used to switch to mainPage
    @FXML
    public void returnBack(ActionEvent event) throws IOException {

        try {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(RssApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            logger.info("Switching to hello-view");
        } catch (IOException ex) {
            System.out.println(ex);
            logger.error("Error in register cancel is" + ex);
        }

    }
     //This function is used to store Password in Encrypted form
    private String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    //This function runs initially and loading all images to show on page, without this you cant load image
    //So,it is necessary to load images in Intialize method.


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            File brandingFile = new File("images/register.png");
            Image branding = new Image(brandingFile.toURI().toString());
            registerImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //This function is used to go back to Login Page
    public void Login(ActionEvent event) throws switchSceneException {
        try {
            Stage stage = (Stage) login.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(RssApplication.class.getResource("login.fxml"));
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
