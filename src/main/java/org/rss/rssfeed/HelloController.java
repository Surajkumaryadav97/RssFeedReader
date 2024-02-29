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

    @FXML
    private ImageView image1View;

    @FXML
    private ImageView image2View;

    @FXML
    private ImageView image3View;

    @FXML
    private ImageView image4View;

    @FXML
    private ImageView image5View;

    @FXML
    private ImageView image6View;
    @FXML
    private ImageView image7View;
    @FXML
    private ImageView image8View;


    @FXML
    private ImageView homeImageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            File brandingFile = new File("images/home.png");
            Image branding1 = new Image(brandingFile.toURI().toString());
            homeImageView.setImage(branding1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/logo_.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            logoImageView.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image1.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image1View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image10.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image2View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image11.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image3View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image3.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image4View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image8.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image5View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image6.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image6View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image5.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image7View.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/image7.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            image8View.setImage(branding2);

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