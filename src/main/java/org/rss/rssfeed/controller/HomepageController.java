package org.rss.rssfeed.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.services.HomepageServices;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Initializable {
    public final Logger logger = LogManager.getLogger(HomepageController.class);
    private final HomepageServices homepageServices = new HomepageServices();

    @FXML
    private VBox buttonnews;

    @FXML
    private Button cancel;

    @FXML
    private ImageView userImageView;

    @FXML
    private ImageView logoImageView;

    @FXML
    private ImageView health1ImageView;

    @FXML
    private ImageView health2ImageView;

    @FXML
    private ImageView tech1ImageView;

    @FXML
    private ImageView tech2ImageView;

    public void start (String category) throws switchSceneException {

    try {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("NewsPage.fxml"));
        Scene newScene = new Scene(fxmlLoader.load());
        NewsPageController newsPageController = fxmlLoader.getController();
        newsPageController.fetchCategory(category, " ");
        switchScene(newScene);
    } catch (Exception e){
        throw new switchSceneException("Error in switching newspage", e);
    }
    }

    public void switchScene(Scene newScene) {
        Stage stage = (Stage) buttonnews.getScene().getWindow();
        stage.setScene(newScene);

    }
    @FXML
    private void handleTechnologyButtonClick(ActionEvent event) throws switchSceneException {
        start("technology");
    }

    @FXML
    private void handleHealthButtonClick(ActionEvent event) throws switchSceneException {
       start("health");
    }

        public void cancel(ActionEvent event) throws switchSceneException{

        try {
            Stage stage = (Stage) cancel.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            logger.debug("Switching to login");
        } catch (Exception ex) {
            System.out.println(ex);
            logger.error("Error in switching to login" + ex);
            throw new switchSceneException("Error in Homepage cancel button" , ex);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homepageServices.initializeLogo(userImageView, "User.png");
        homepageServices.initializeLogo(logoImageView, "logo_.png");
        homepageServices.initializeLogo(health1ImageView, "health1.png");
        homepageServices.initializeLogo(health2ImageView, "health3.png");
        homepageServices.initializeLogo(tech1ImageView, "tech3.png");
        homepageServices.initializeLogo(tech2ImageView, "tech4.png");
    }
}

