package org.rss.rssfeed;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RssController implements Initializable {


    @FXML
    private Button loginbtn;


    @FXML
    private Button explore;

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
            File brandingFile = new File("images/logo_.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            logoImageView.setImage(branding2);

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

    public void explore(ActionEvent event) throws IOException {
        Stage stage = (Stage) explore.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}