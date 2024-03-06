package org.rss.rssfeed.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

import static org.rss.rssfeed.HelloApplication.logger;

public class HtmlContent {

    @FXML
    private Button cancel;
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
    public void initialize(String username1,String techfeed,String healthfeed) {
        username=username1;
        tech1=techfeed;
        medi1=healthfeed;
        System.out.println(username + "feedhtml");
        // Load HTML content from the main page
       if(techfeed!=null&&healthfeed==null) {

                displayTechnews("https://arstechnica.com");

        }
        else if(healthfeed!=null&&techfeed==null){

                displayMedinews("http://www.medicalnewstoday.com");
        }
        else{

           displayrandomnews();
       }

    }

    private void displayrandomnews() {
        int cnt=0;
        String techurl="https://arstechnica.com";
        String mediurl="http://www.medicalnewstoday.com";
        try {
            Document doc = Jsoup.connect(techurl).get();

            // Extract links to articles
            Elements articles = doc.select("li.tease.article");
            if (articles != null) {
                for (Element article : articles) {
                    Elements header = article.select("header");
                    for (Element headers : header) {
                        cnt++;

                        Elements anch = headers.select("h2 a"); // Extract article title
                        String articleLink = anch.attr("href");
                        fetchArticleData(articleLink);
                    }
                    if(cnt==5) break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Document doc = Jsoup.connect(mediurl).get();
            Elements listItems = doc.select("div.__chrome ul li a");

            for (Element listItem : listItems) {

                String anchorText = listItem.text();
                String href = listItem.attr("href");
                System.out.println("Anchor Text: " + anchorText);
                System.out.println("URL: " + href);
                System.out.println();
                h.put(anchorText, href);
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

            // Add mouse click event handler
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

        // Add data to table

        // Add data to table
        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        h.forEach((title, description) -> {

            articleList.add(new ArticleData(title));
        });
        tableView.setItems(articleList);
    }

//    private void displayRandomnews() {
//
//    }

    private void displayMedinews(String url) {

        try {
            Document doc = Jsoup.connect(url).get();
            Elements listItems = doc.select("div.__chrome ul li a");

            for (Element listItem : listItems) {

                String anchorText = listItem.text();
                String href = listItem.attr("href");
                System.out.println("Anchor Text: " + anchorText);
                System.out.println("URL: " + href);
                System.out.println();
                h.put(anchorText, href);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
//        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

        // Set up custom cell factory for title column
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

            // Add mouse click event handler
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

        // Add data to table

        // Add data to table
        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        h.forEach((title, description) -> {

            articleList.add(new ArticleData(title));
        });
        tableView.setItems(articleList);
    }


    private void displayTechnews(String url) {
        int cnt=0;
        try {
            Document doc = Jsoup.connect(url).get();

            // Extract links to articles
            Elements articles = doc.select("li.tease.article");
            if (articles != null) {
                for (Element article : articles) {
                    Elements header = article.select("header");
                    for (Element headers : header) {
                        cnt++;

                        Elements anch = headers.select("h2 a"); // Extract article title
                        String articleLink = anch.attr("href");
                        fetchArticleData(articleLink);
                    }
                    if(cnt==5) break;

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

            // Add mouse click event handler
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

        // Add data to table

        // Add data to table
        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        h.forEach((title, description) -> {

            articleList.add(new ArticleData(title));
        });
        tableView.setItems(articleList);
    }


    private void fetchArticleData(String url) {
        try {
            Document doc = Jsoup.connect(url).get();

            // Extract relevant information
            String ogurl = doc.select("meta[property=og:url]").attr("content");

            String ogTitle = doc.select("meta[property=og:title]").attr("content");
            String ogDescription = doc.select("meta[property=og:description]").attr("content");
            String twitterTitle = doc.select("meta[name=twitter:title]").attr("content");
            String twitterDescription = doc.select("meta[name=twitter:description]").attr("content");
            String twitterurl = doc.select("meta[name=twitter:description]").attr("content");

            // Check if any of the metadata values are empty
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

    }

    public void handleTechBtn(ActionEvent event) {
        System.out.println("start");
//        String username=logincontroller.getUserName();
      saveFeedChoice(username,"technology",medi1);

    }
    loginController logincontroller=new loginController();







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
}



