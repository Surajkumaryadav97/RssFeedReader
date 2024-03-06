package org.rss.rssfeed.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.ResourceBundle;

import static org.rss.rssfeed.HelloApplication.logger;

public class HtmlContent  implements Initializable {

    @FXML
    private ImageView logoImageview;

    @FXML
    private ImageView userImageView;
    @FXML
    private Button cancel;

    @FXML
    private Button profile;

    @FXML
    private TableView<ArticleData> tableView;

    @FXML
    private TableColumn<ArticleData, String> titleColumn;

    @FXML
    private TableColumn<ArticleData, String> descriptionColumn;

    private HashMap<String, String> h = new HashMap<>();
    int cnt = 0;
    String username;
    String tech1;
    String medi1;

    static String techFeed1;
    static  String healthFeed1;


    public void initialize(String username1,String techfeed,String healthfeed) {
        username=username1;
        tech1=techfeed;
        medi1=healthfeed;

        System.out.println(username + "feedhtml");

       if(!techfeed.isEmpty()&&healthfeed.isEmpty()) {

                displayTechnews("https://arstechnica.com");

        }
        else if(!healthfeed.isEmpty()&&techfeed.isEmpty()){

                displayMedinews("http://www.medicalnewstoday.com");
        }
        else{

           displayrandomnews();
       }

    }

    private void displayrandomnews() {
        h.clear();
        int cnt=0;
        String techurl="https://arstechnica.com";
        String mediurl="http://www.medicalnewstoday.com";
        try {
            Document doc = Jsoup.connect(techurl).get();


            Elements articles = doc.select("li.tease.article");
            if (articles != null) {
                for (Element article : articles) {
                    Elements header = article.select("header");
                    for (Element headers : header) {
                        cnt++;

                        Elements anch = headers.select("h2 a");
                        String articleLink = anch.attr("href");
                        fetchArticleData(articleLink);
                    }
                    if(cnt==2) break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int cnt1=0;
        try {
            Document doc = Jsoup.connect(mediurl).get();
            Elements listItems = doc.select("ul.css-1q1zlz3 li.css-1ib8oek");
            for (Element listItem : listItems) {
                cnt1++;
                Element anchor = listItem.selectFirst("a.css-1xlgwie");
                if (anchor != null) {
                    String anchorText = anchor.text();
                    String href = anchor.attr("href");
                    h.put(anchorText, href);
                }
                if(cnt1==2) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
//        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());


        titleColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                    }
                }
            };


            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = h.get(desc);
                    if (ogUrl != null && !ogUrl.isEmpty()) {
                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }
                }
            });

            return cell;
        });


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        h.forEach((title, description) -> {

            articleList.add(new ArticleData(title));
        });
        tableView.setItems(articleList);
    }



    private void displayMedinews(String url) {
        h.clear();
        int cnt=0;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements listItems = doc.select("ul.css-1q1zlz3 li.css-1ib8oek");
            for (Element listItem : listItems) {
                cnt++;
                Element anchor = listItem.selectFirst("a.css-1xlgwie");
                if (anchor != null) {
                    String anchorText = anchor.text();
                    String href = anchor.attr("href");
                    h.put(anchorText, href);
                    if(cnt==2) break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
//        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());


        titleColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                    }
                }
            };


            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = h.get(desc);
                    if (ogUrl != null && !ogUrl.isEmpty()) {
                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }
                }
            });

            return cell;
        });


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        h.forEach((title, description) -> {

            articleList.add(new ArticleData(title));
        });
        tableView.setItems(articleList);
    }


    private void displayTechnews(String url) {
        h.clear();
        int cnt=0;
        try {
            Document doc = Jsoup.connect(url).get();


            Elements articles = doc.select("li.tease.article");
            if (articles != null) {
                for (Element article : articles) {
                    Elements header = article.select("header");
                    for (Element headers : header) {
                        cnt++;

                        Elements anch = headers.select("h2 a");
                        String articleLink = anch.attr("href");
                        fetchArticleData(articleLink);
                    }
                    if(cnt==2) break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
//        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());


        titleColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                        setTextFill(Color.BLACK);
                    }
                }
            };


            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = h.get(desc);
                    if (ogUrl != null && !ogUrl.isEmpty()) {
                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }
                }
            });

            return cell;
        });


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        h.forEach((title, description) -> {

            articleList.add(new ArticleData(title));
        });
        tableView.setItems(articleList);
    }


    private void fetchArticleData(String url) {
        try {
            Document doc = Jsoup.connect(url).get();


            String ogurl = doc.select("meta[property=og:url]").attr("content");

            String ogTitle = doc.select("meta[property=og:title]").attr("content");
            String ogDescription = doc.select("meta[property=og:description]").attr("content");
            String twitterTitle = doc.select("meta[name=twitter:title]").attr("content");
            String twitterDescription = doc.select("meta[name=twitter:description]").attr("content");
            String twitterurl = doc.select("meta[name=twitter:description]").attr("content");


            if (ogTitle.isEmpty()) {
                ogTitle = twitterTitle;
            }
            if (ogDescription.isEmpty()) {
                ogDescription = twitterDescription;
            }
            if (ogurl.isEmpty()) {
                ogurl = twitterurl;
            }

            h.put(ogDescription, ogurl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleHealthBtn(ActionEvent event) {

        System.out.println(username);
        saveFeedChoice(username,tech1,"health");
        retrieveFeedsByUsername(username);
        System.out.println("healthBtnClicked");
        initialize(username,techFeed1,healthFeed1);

    }

    public void handleTechBtn(ActionEvent event) {
        System.out.println("start");

      saveFeedChoice(username,"technology",medi1);
        retrieveFeedsByUsername(username);
        System.out.println("techBtnClicked");
        initialize(username,techFeed1,healthFeed1);

    }
//    loginController logincontroller=new loginController();







    public void saveFeedChoice(String username, String techFeed, String healthFeed) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String sql = "UPDATE user SET techFeed = ?, healthFeed = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, techFeed);
            statement.setString(2, healthFeed);
            statement.setString(3, username);

            statement.executeUpdate();
            System.out.println("Feed choices saved to the database successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void cancel(ActionEvent event) throws switchSceneException {

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
        try {
            File brandingFile = new File("images/logo_.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            logoImageview.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File brandingFile = new File("images/user.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            userImageView.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleRemoveTech(ActionEvent event) {
        removeFeedChoice(username,"",medi1);
        retrieveFeedsByUsername(username);
        System.out.println("techBtnremove");
        initialize(username,techFeed1,healthFeed1);

    }

    public void removeHandlehealth(ActionEvent event) {
        removeFeedChoice(username,tech1,"");
        retrieveFeedsByUsername(username);
        System.out.println("healthBtnremove");
        initialize(username,techFeed1,healthFeed1);
    }

    public void removeFeedChoice(String username, String techFeed, String healthFeed) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        String sql = "UPDATE user SET techFeed = ?, healthFeed = ? WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, techFeed);
            statement.setString(2, healthFeed);
            statement.setString(3, username);

            statement.executeUpdate();
            System.out.println("Feed choices saved to the database successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void retrieveFeedsByUsername(String username) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "SELECT techFeed, healthFeed FROM user WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                techFeed1 = resultSet.getString("techFeed");
                 healthFeed1 = resultSet.getString("healthFeed");

                System.out.println("Tech Feed: " + techFeed1);
                System.out.println("Health Feed: " + healthFeed1);
            } else {
                System.out.println("No feeds found for username: " + username);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void handleProfileBtn(ActionEvent event) throws switchSceneException {
        try {
            Stage stage = (Stage) profile.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserInfo.fxml"));
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
}



