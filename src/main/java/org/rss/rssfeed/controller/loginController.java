package org.rss.rssfeed.controller;



import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.rss.rssfeed.Exceptions.sqlException;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.Exceptions.userNotFoundException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class loginController implements Initializable {

    public final Logger logger = LogManager.getLogger(loginController.class);

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
            File brandingFile = new File("images/login.png");
            Image branding = new Image(brandingFile.toURI().toString());
            loginImageVeiw.setImage(branding);
            logger.info("Image loading");

        } catch (Exception e) {
           logger.error("Image loading error" + e);
        }
    }




    @FXML
    public void cancel(ActionEvent event) throws switchSceneException {

        try {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
           logger.debug("Switching to hello-view");
        } catch (Exception ex) {
            System.out.println(ex);
            logger.error("Error in login is" + ex);
            throw new switchSceneException("error in loginController cancel switch to hello-view", ex);
        }

    }

    public void start(String userName1, String techfeed, String healthfeed,String layout) throws switchSceneException {
        try {
            // Create loader for the new scene
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view.fxml"));
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setStyle("-fx-progress-color: green; -fx-min-width: 150px; -fx-min-height: 150px;-fx-background-color: black;");
            Stage loaderStage = new Stage();
            loaderStage.initStyle(StageStyle.TRANSPARENT); // Set stage style to transparent
            loaderStage.setResizable(false); // Disable resizing
            loaderStage.initModality(Modality.APPLICATION_MODAL); // Make the loader stage modal
            loaderStage.setScene(new Scene(new StackPane(progressIndicator), Color.TRANSPARENT));
            loaderStage.show();
            Rectangle overlay = new Rectangle();
            overlay.setFill(Color.rgb(0, 0, 0, 0.8)); // Set the color and transparency
            overlay.widthProperty().bind(loginButton.getScene().widthProperty()); // Set the width to match the scene
            overlay.heightProperty().bind(loginButton.getScene().heightProperty()); // Set the height to match the scene
            ((Pane) loginButton.getScene().getRoot()).getChildren().add(overlay);

            // Load the new scene in a background thread
            new Thread(() -> {
                try {
                    // Load the new scene
                    Scene newScene = new Scene(fxmlLoader.load());

                    // Access the controller of the new scene
                    RssContent htmlContent = fxmlLoader.getController();
                    htmlContent.initialize(userName1, techfeed, healthfeed,layout);

                    // Switch to the new scene on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        switchScene(newScene);
                        loaderStage.close(); // Close the loader stage after switching
                    });
                } catch (Exception ex) {
                    // Handle any exceptions
                    ex.printStackTrace(); // Print the stack trace for debugging purposes
                    // Optionally, show an error message to the user
                    loaderStage.close(); // Close the loader stage in case of an error
                }
            }).start();
        } catch (Exception ex) {
            // Throw switchSceneException or handle the exception as needed
            throw new switchSceneException("Error in loginController start method switch to Homepage", ex);
        }
    }



    public void switchScene(Scene newScene) {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(newScene);
      logger.debug("Switching scene");
    }

    @FXML
    void signup(ActionEvent event) throws switchSceneException {
        try {
            Stage stage = (Stage) userSignup.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            logger.debug("Switching to register");
        } catch (Exception ex) {
            System.out.println(ex);
            logger.error(ex);
            throw new switchSceneException("Error in switching to register from loginController signup", ex);
        }
    }

   static String userName1;

    static String techFeed;
    static String healthFeed;


    @FXML
    void loginButtonOnAction(ActionEvent actionEvent) throws sqlException, userNotFoundException {
        String userName = usernameTextField.getText();
        String password = enterPasswordField.getText();

        if (userName.isEmpty() || password.isEmpty()) {
            logger.debug("Some fields are Empty");
            loginMessageLabel.setText("Enter username or password");
            return;
        }
        ProgressIndicator progressIndicator = new ProgressIndicator();
        StackPane rootPane = new StackPane();

        // Show loader before starting login process
        rootPane.getChildren().add(progressIndicator);
        progressIndicator.setVisible(true);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();


        String sql = "SELECT Password,firstName,userName,techFeed,healthFeed FROM user WHERE userName = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("Password");
                    String username = resultSet.getString("firstName");
                     userName1 =resultSet.getString("userName");
                     techFeed=resultSet.getString("techFeed");
                     healthFeed=resultSet.getString("healthFeed");

                    System.out.println(userName1);

                    // Verify the entered password with the hashed password from the database
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {

                        // Authentication of user has done successfully
                        loginMessageLabel.setText("Login successful");
                        UserInfoController userInfoController=new UserInfoController();
                        System.out.println(userName1);
                        userInfoController.initializeUserInfo(userName1,username,techFeed,healthFeed);
                        start(userName1,techFeed,healthFeed,"");


                    } else {

                        logger.debug("Invalid User");
                        loginMessageLabel.setText("Invalid username or password");
                    }
                }
            }
            catch(Exception e){
               logger.error(e);
                throw new sqlException("Error in executing database Query", e);
            }
        } catch (Exception e) {

            logger.error(e);
            throw new userNotFoundException("No User Found", e);
        } finally {
            // Close the DB connection ,it is good practice
            try {
                connection.close();
            }catch(Exception e){
                throw new sqlException("Error in closing the database connection", e);
            }
        }
    }

    public String getUsername() {
        return userName1;
    }
}















