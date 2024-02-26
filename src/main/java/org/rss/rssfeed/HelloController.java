package org.rss.rssfeed;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private VBox loginandregister;
    @FXML
    private Button loginbtn;

    @FXML
    private Button regbtn;


    public void login(ActionEvent event) throws IOException {
        Stage stage = (Stage) loginbtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
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