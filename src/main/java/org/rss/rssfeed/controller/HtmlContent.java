package org.rss.rssfeed.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import javafx.util.converter.DefaultStringConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//public class HtmlContent {
//
//    HashMap<String,String> h=new HashMap<>();
//
//    @FXML
//    private WebView webView;
//
//    @FXML
//    private Label titleLabel;
//
//    @FXML
//    private Label excerptLabel;
//
//    @FXML
//    private Label authorLabel;
//
//    @FXML
//    private Label dateLabel;
//
//    @FXML
//    private Label commentCountLabel;
//
//    public void initialize() {
//        // Load HTML content from the main page
//        String url = "https://arstechnica.com";
//        try {
//            Document doc = Jsoup.connect(url).get();
//            //System.out.println(doc);
//
//            // Extract links to articles
//            Elements articles = doc.select("li.tease.article");
////            System.out.println(articles);
//            if (articles != null) {
//
//                Elements header = articles.select("header");
//
//                for(Element headers : header){
//
//                    Elements anch = headers.select("h2 a"); // Extract article title
//                    String articleLink = anch.attr("href");
//                    fetchArticleData(articleLink);
//                }
//                System.out.println(h);
//
//            }
//        }catch(Exception e){
//
//        }
//
//    }
//
//
//
//            private void fetchArticleData(String url) {
//                try {
////                    System.out.println(url);
//
//                    Document doc = Jsoup.connect(url).get();
////                    System.out.println(doc);
//
//                    // Extract relevant information
//                    String ogUrl = doc.select("meta[property=og:url]").attr("content");
//                    String ogTitle = doc.select("meta[property=og:title]").attr("content");
//                    String ogDescription = doc.select("meta[property=og:description]").attr("content");
//                    String twitterTitle = doc.select("meta[name=twitter:title]").attr("content");
//                    String twitterDescription = doc.select("meta[name=twitter:description]").attr("content");
//
//                    // Check if any of the metadata values are empty
//                    if (ogTitle.isEmpty()) {
//                        System.out.println("Open Graph Title not found");
//                        ogTitle = twitterTitle;
//                    }
//                    if (ogDescription.isEmpty()) {
//                        System.out.println("Open Graph Description not found");
//                        ogDescription = twitterDescription;
//                    }
//
//                    // Print extracted values for debugging
//                    System.out.println("OG URL: " + ogUrl);
//                    System.out.println("OG Title: " + ogTitle);
//                    System.out.println("OG Description: " + ogDescription);
//
//                    h.put(ogTitle,ogDescription);
//
//
//
//                    // Now you can use these values to set labels or perform other operations
//                    titleLabel.setText(ogTitle);
//                    excerptLabel.setText(ogDescription);
//                    tableView.getItems().add(new ArticleData(title, description));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//
//

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;

public class HtmlContent {

    @FXML
    private TableView<ArticleData> tableView;

    @FXML
    private TableColumn<ArticleData, String> titleColumn;

    @FXML
    private TableColumn<ArticleData, String> descriptionColumn;

    private HashMap<String, String> h = new HashMap<>();
    int cnt = 0;

    public void initialize(String feed) {
        System.out.println(feed + "feedhtml");
        // Load HTML content from the main page
        switch (feed) {
            case "technology":
                displayTechnews("https://arstechnica.com");
                break;
            case "Medi":
                displayMedinews("http://www.medicalnewstoday.com");
                break;
            default:
                break;
        }

//        String urlTech = "https://arstechnica.com";
//        String urlHlth = "http://www.medicalnewstoday.com";
//        try {
//            Document doc = Jsoup.connect(urlTech).get();
//
//            // Extract links to articles
//            Elements articles = doc.select("li.tease.article");
//            if (articles != null) {
//                for (Element article : articles) {
//                    Elements header = article.select("header");
//                    for (Element headers : header) {
//                        cnt++;
//                        Elements anch = headers.select("h2 a"); // Extract article title
//                        String articleLink = anch.attr("href");
//                        fetchArticleData(articleLink);
//                    }
//                    if(cnt==4) break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int cntHealth=0;
//        try {
//            Document doc = Jsoup.connect(urlHlth).get();
//            Elements listItems = doc.select("div.__chrome ul li a");
//
//            for (Element listItem : listItems) {
//                cntHealth++;
//                if(cntHealth==4) break;
//                String anchorText = listItem.text();
//                String href = listItem.attr("href");
//                System.out.println("Anchor Text: " + anchorText);
//                System.out.println("URL: " + href);
//                System.out.println();
//                h.put(anchorText, href);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        // Set up columns
//        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
//        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());
//
//        // Set up custom cell factory for title column
//        titleColumn.setCellFactory(col -> {
//            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item != null) {
//                        setText(item);
//                    }
//                }
//            };
//
//            // Add mouse click event handler
//            cell.setOnMouseClicked(event -> {
//                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
//                    String desc = cell.getItem();
//                    String ogUrl=h.get(desc);
//                    if (ogUrl != null && !ogUrl.isEmpty()) {
//                        Webview webViewSample = new Webview();
//                        webViewSample.loadURL(ogUrl);
//                    }
//                }
//            });
//
//            return cell;
//        });
//
//        // Add data to table
//
//        // Add data to table
//        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
//        h.forEach((title, description) -> {
//
//            articleList.add(new ArticleData(title, description));
//        });
//        tableView.setItems(articleList);
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
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());

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

            articleList.add(new ArticleData(title, description));
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
        descriptionColumn.setCellValueFactory(data -> data.getValue().descriptionProperty());


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

            articleList.add(new ArticleData(title, description));
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
}



