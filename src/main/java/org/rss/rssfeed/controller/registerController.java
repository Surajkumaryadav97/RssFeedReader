package org.rss.rssfeed.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registerController {

    @FXML
    private TextField reg_Fname;

    @FXML
    private TextField reg_Lname;

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

    private final HelloApplication helloApplication = new HelloApplication();

    @FXML
    public void register() {
        String firstName = reg_Fname.getText();
        String lastName = reg_Lname.getText();
        String userName = reg_Uname.getText();
        String password = reg_password.getText();
        String role = "NORMAL";

        // Hash the password using bcrypt
        String Password = hashPassword(password);

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String sql = "INSERT INTO user (firstName, lastName, userName, Password, role) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, Password);
            preparedStatement.setString(5, role);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
//                regisMessageLabel.setText("Login successful");
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }

    }

    @FXML
    public void returnBack(ActionEvent event) throws IOException {

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

    private String hashPassword(String password) {
        // Hash the password using bcrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
