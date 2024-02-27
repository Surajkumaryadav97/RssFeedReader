package org.rss.rssfeed;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private VBox loginandregister;
    @FXML
    private Button loginbtn;

    @FXML
    private Button regbtn;

    @FXML
    private ImageView logoImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File brandingFile = new File("../../../images/logo_.png");
            Image branding = new Image(brandingFile.toURI().toString());
            logoImageView.setImage(branding);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginbtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        Stage stage = (Stage) regbtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }





//    public void onHelloButtonClick(ActionEvent actionEvent) throws IOException {
//        HelloController helloController=new HelloController();
//
//        helloController.login();
//    }

//    public void onRegisterButtonClick(ActionEvent actionEvent) throws IOException {
//        HelloController helloController=new HelloController();
//        helloController.register();
//    }
}