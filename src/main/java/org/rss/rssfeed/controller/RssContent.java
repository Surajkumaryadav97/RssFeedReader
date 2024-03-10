package org.rss.rssfeed.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.rss.rssfeed.Exceptions.switchSceneException;
import org.rss.rssfeed.HelloApplication;
import org.rss.rssfeed.db.DatabaseConnection;
import org.rss.rssfeed.model.ArticleData;
import org.rss.rssfeed.model.NewsModel;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ResourceBundle;


public class RssContent implements Initializable {
    public static final Logger logger = LogManager.getLogger(RssContent.class);

    @FXML
    private ImageView logoImageview;

    @FXML
    private Label userNameLabel1;

    @FXML
    private VBox parentContainer;

    @FXML
    private ImageView userImageView;

    @FXML
    private ImageView iconImageView;

    @FXML
    private  ImageView iconImageView1;

    @FXML
    private Button cancel1;

    @FXML
    private Button cardview;

    @FXML
    private Button profile;

    @FXML
    private TableView<ArticleData> tableView;

    @FXML
    private TableColumn<ArticleData, String> descriptionColumn;

    @FXML
    private TableColumn<ArticleData, String> imageColumn;

    private ArrayList<NewsModel> newsarraylist = new ArrayList<>();
    int cnt = 0;
    String username;
    String tech1;
    String medi1;

    static String techFeed1;
    static String healthFeed1;
    static String techurl="";
    static String healthurl="";
    static String topnewsurl="";

    static String layoutview="";



    public void parse() {
        try {
            File xmlFile = new File("C:\\Users\\91704\\Downloads\\RssFeedReader\\RssFeedReader\\src\\main\\java\\org\\rss\\rssfeed\\controller\\news.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList linkList = doc.getElementsByTagName("link");
            for (int i = 0; i < linkList.getLength(); i++) {
                org.w3c.dom.Element linkElement = (org.w3c.dom.Element) linkList.item(i);
                String category = linkElement.getElementsByTagName("category").item(0).getTextContent();
                String url = linkElement.getElementsByTagName("url").item(0).getTextContent();
                System.out.println("Category: " + category);
                System.out.println("URL:1 " + url);
                if ("tech".equals(category)) {
                    techurl = url;
                    System.out.println("URL:tech " + techurl);
                } else if ("health".equals(category)) {
                    healthurl = url;
                    System.out.println("URL:health " + healthurl);
                } else {
                    topnewsurl = url;
                    System.out.println("URL:topnewsurl " + topnewsurl);
                }

            }
        }catch(Exception e){}
    }


    public void initialize(String username1, String techfeed, String healthfeed,String layout) {
        parse();
        layoutview=layout;
        username = username1;
        System.out.println(username);
        tech1 = techfeed;
        medi1 = healthfeed;
        if (!techfeed.isEmpty() && healthfeed.isEmpty()) {

            displayTechnews(techurl);





        } else if (!healthfeed.isEmpty() && techfeed.isEmpty()) {

            displayMedinews(healthurl);



        } else if(!healthfeed.isEmpty() && !techfeed.isEmpty()){

            displaybothnews(techurl,healthurl);



        }
        else{
            displayrandomnews(topnewsurl,techurl,healthurl);




        }

    }

    private void displayrandomnews(String topnewsurl,String techurl,String healthurl) {
        newsarraylist.clear();


        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(topnewsurl);

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);

                    // Get description
                    String title = item.getElementsByTagName("title").item(0).getTextContent();


                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
//                    linkLabel.setText("Link: " + link);
                    NodeList enclosureList = item.getElementsByTagName("enclosure");

                    if (enclosureList.getLength() > 0) {
                        Element enclosureElement = (Element) enclosureList.item(0);
                        String imageUrl = enclosureElement.getAttribute("url");
                        newsarraylist.add(new NewsModel(title, link, imageUrl));
                    }



                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(healthurl);

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);

                    // Get description
                    String title = item.getElementsByTagName("title").item(0).getTextContent();


                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();

                    String imageUrl = item.getElementsByTagName("image").item(0).getTextContent();
                    newsarraylist.add(new NewsModel(title, link, imageUrl));



                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(techurl);

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);

                    // Get description
                    String title = item.getElementsByTagName("title").item(0).getTextContent();


                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
//                    linkLabel.setText("Link: " + link);

                    NodeList enclosureList = item.getElementsByTagName("enclosure");

                    if (enclosureList.getLength() > 0) {
                        Element enclosureElement = (Element) enclosureList.item(0);
                        String imageUrl = enclosureElement.getAttribute("url");

                        newsarraylist.add(new NewsModel(title, link, imageUrl));
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       if("magazineview".equals(layoutview)){
            LayoutView layout = new LayoutView();
            Parent cardView = layout.createMagazineView(newsarraylist);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(cardView);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setPrefViewportHeight(parentContainer.getHeight());

            parentContainer.getChildren().clear();

            scrollPane.setPrefViewportHeight(parentContainer.getHeight());


            parentContainer.getChildren().add(scrollPane);

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            return;
        }

        descriptionColumn.setCellValueFactory(data -> data.getValue().titleProperty());




        tableView.setRowFactory(tv -> {
            TableRow<ArticleData> row = new TableRow<>();
            row.setPrefHeight(60); // Set the preferred height for each row
            return row;
        });
//        descriptionColumn.setCellValueFactory(data -> data.getValue().imageUrlProperty());

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        imageColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (imageUrl != null && !empty) {
                        ImageView imageView = new ImageView(new Image(imageUrl));

                        imageView.setFitWidth(120); // Set width of the image
                        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });
//



        descriptionColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #f2f2f2;"); // Customize font size and weight
                    } else {
                        setText(null);
                    }
                }
            };

            cell.setOnMousePressed(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    // Set blue background color when the mouse is pressed
                    cell.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #ADD8E6;");
                }
            });


            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = null;

                    // Find the NewsModel object with the matching description
                    for (NewsModel news : newsarraylist) {
                        if (news.getDesc().equals(desc)) {
                            // Found the matching NewsModel object
                            // Retrieve the ogUrl attribute
                            ogUrl = news.getLink();
                            break; // Exit the loop since we found the match
                        }
                    }

                    if (ogUrl != null && !ogUrl.isEmpty()) {
                        // Load the URL into the WebView
                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }

                }
            });

            return cell;
        });


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        newsarraylist.forEach(news -> {
            String image = news.getImage();
            String description = news.getDesc();
            // Assuming the third value is stored in a field called thirdValue in NewsModel class
            // String thirdValue = news.getThirdValue();

            // Create ArticleData object using title, description, and thirdValue
            // Change ArticleData constructor accordingly if needed
            articleList.add(new ArticleData(image, description));
        });
        tableView.setItems(articleList);
    }





    private void displayMedinews(String url) {
        newsarraylist.clear();

        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(url);

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);

                    // Get description
                    String title = item.getElementsByTagName("title").item(0).getTextContent();
//                    descriptionLabel.setText("Description: " + description);

                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
//                    linkLabel.setText("Link: " + link);

                    String imageUrl = item.getElementsByTagName("image").item(0).getTextContent();

//                    linkLabel.setText("Link: " + link);

                    newsarraylist.add(new NewsModel(title, link, imageUrl));


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if("magazineview".equals(layoutview)){
            LayoutView layout = new LayoutView();
            Parent cardView = layout.createMagazineView(newsarraylist);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(cardView);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setPrefViewportHeight(parentContainer.getHeight());

            parentContainer.getChildren().clear();

            parentContainer.getChildren().add(scrollPane);

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            return;
        }

        descriptionColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        imageColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (imageUrl != null && !empty) {

                        ImageView imageView = new ImageView(new Image(imageUrl));

                        imageView.setFitWidth(120); // Set width of the image
                        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });
//
//
        tableView.setRowFactory(tv -> {
            TableRow<ArticleData> row = new TableRow<>();
            row.setPrefHeight(60); // Set the preferred height for each row
            return row;
        });


        descriptionColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #f2f2f2;"); // Customize font size and weight
                    } else {
                        setText(null);
                    }
                }
            };
            cell.setOnMousePressed(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    // Set blue background color when the mouse is pressed
                    cell.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #ADD8E6;");
                }
            });



            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = null;

                    // Find the NewsModel object with the matching description
                    for (NewsModel news : newsarraylist) {
                        if (news.getDesc().equals(desc)) {
                            // Found the matching NewsModel object
                            // Retrieve the ogUrl attribute
                            ogUrl = news.getLink();
                            break; // Exit the loop since we found the match
                        }
                    }

                    if (ogUrl != null && !ogUrl.isEmpty()) {
                        // Load the URL into the WebView
                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }

                }
            });

            return cell;
        });


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        newsarraylist.forEach(news -> {
            String image = news.getImage();
            String description = news.getDesc();
            articleList.add(new ArticleData(image,description));
        });
        tableView.setItems(articleList);
    }
    private void displaybothnews(String techurl, String healthurl) {
        newsarraylist.clear();

        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(techurl);

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);

                    // Get description
                    String title = item.getElementsByTagName("title").item(0).getTextContent();


                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
//                    linkLabel.setText("Link: " + link);

                    NodeList enclosureList = item.getElementsByTagName("enclosure");

                    if (enclosureList.getLength() > 0) {
                        Element enclosureElement = (Element) enclosureList.item(0);
                        String imageUrl = enclosureElement.getAttribute("url");

                        newsarraylist.add(new NewsModel(title, link, imageUrl));
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(healthurl);

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);

                    // Get description
                    String title = item.getElementsByTagName("title").item(0).getTextContent();


                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
//                    linkLabel.setText("Link: " + link);

                    String imageUrl = item.getElementsByTagName("image").item(0).getTextContent();

                    newsarraylist.add(new NewsModel(title, link, imageUrl));


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        descriptionColumn.setCellValueFactory(data -> data.getValue().titleProperty());
//        imageColumn.setCellValueFactory(data -> data.getValue().imageUrlProperty());



        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        imageColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (imageUrl != null && !empty) {
                        ImageView imageView = new ImageView(new Image(imageUrl));

                        imageView.setFitWidth(120); // Set width of the image
                        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });
        tableView.setRowFactory(tv -> {
            TableRow<ArticleData> row = new TableRow<>();
            row.setPrefHeight(60);
            return row;
        });


        descriptionColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #f2f2f2;"); // Customize font size and weight
                    } else {
                        setText(null);
                    }
                }
            };
            cell.setOnMousePressed(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {

                    cell.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #ADD8E6;");
                }
            });


            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = null;


                    for (NewsModel news : newsarraylist) {
                        if (news.getDesc().equals(desc)) {

                            ogUrl = news.getLink();
                            break;
                        }
                    }

                    if (ogUrl != null && !ogUrl.isEmpty()) {

                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }

                }
            });

            return cell;
        });
          if("magazineview".equals(layoutview)) {
            LayoutView layout = new LayoutView();
            Parent cardView = layout.createMagazineView(newsarraylist);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(cardView);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
           scrollPane.setPrefViewportHeight(parentContainer.getHeight());

            parentContainer.getChildren().clear();

            scrollPane.setPrefViewportHeight(parentContainer.getHeight());


            parentContainer.getChildren().add(scrollPane);

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            return;
        }


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        newsarraylist.forEach(news -> {
            String image = news.getImage();
            String description = news.getDesc();
            articleList.add(new ArticleData(image,description));
        });
        tableView.setItems(articleList);
    }





    private void displayTechnews(String url) {
        newsarraylist.clear();

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(url);


            doc.getDocumentElement().normalize();


            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    org.w3c.dom.Element item = (org.w3c.dom.Element) itemList.item(i);


                    String title = item.getElementsByTagName("title").item(0).getTextContent();


                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();

                    NodeList enclosureList = item.getElementsByTagName("enclosure");

                    if (enclosureList.getLength() > 0) {
                        Element enclosureElement = (Element) enclosureList.item(0);
                        String imageUrl = enclosureElement.getAttribute("url");

                        newsarraylist.add(new NewsModel(title, link, imageUrl));
                    }


                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if("magazineview".equals(layoutview)){
            LayoutView layout = new LayoutView();
            Parent cardView = layout.createMagazineView(newsarraylist);

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(cardView);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);


            parentContainer.getChildren().clear();

            parentContainer.getChildren().add(scrollPane);

            scrollPane.setPrefViewportHeight(parentContainer.getHeight());

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            return;
        }

        descriptionColumn.setCellValueFactory(data -> data.getValue().titleProperty());

        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));
        imageColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String imageUrl, boolean empty) {
                    super.updateItem(imageUrl, empty);
                    if (imageUrl != null && !empty) {

                        ImageView imageView = new ImageView(new Image(imageUrl));

                        imageView.setFitWidth(120);
                        imageView.setPreserveRatio(true);
                        setGraphic(imageView);
                    } else {
                        setGraphic(null);
                    }
                }
            };
            return cell;
        });
        tableView.setRowFactory(tv -> {
            TableRow<ArticleData> row = new TableRow<>();
            row.setPrefHeight(60); // Set the preferred height for each row
            return row;
        });


        descriptionColumn.setCellFactory(col -> {
            TableCell<ArticleData, String> cell = new TableCell<ArticleData, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(item);
                        setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #f2f2f2;"); // Customize font size and weight
                    } else {
                        setText(null);
                    }
                }
            };
            cell.setOnMousePressed(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    // Set blue background color when the mouse is pressed
                    cell.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #ADD8E6;");
                }
            });


            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    String desc = cell.getItem();
                    String ogUrl = null;

                    // Find the NewsModel object with the matching description
                    for (NewsModel news : newsarraylist) {
                        if (news.getDesc().equals(desc)) {
                            // Found the matching NewsModel object
                            // Retrieve the ogUrl attribute
                            ogUrl = news.getLink();

                            break; // Exit the loop since we found the match
                        }
                    }

                    if (ogUrl != null && !ogUrl.isEmpty()) {
                        // Load the URL into the WebView
                        Webview webViewSample = new Webview();
                        webViewSample.loadURL(ogUrl);
                    }

                }
            });

            return cell;
        });


        ObservableList<ArticleData> articleList = FXCollections.observableArrayList();
        newsarraylist.forEach(news -> {
            String image = news.getImage();

            String description = news.getDesc();
            articleList.add(new ArticleData(image,description));
        });
        tableView.setItems(articleList);
    }





    public void handleHealthBtn(ActionEvent event) {


        saveFeedChoice(username,tech1,"health");
        retrieveFeedsByUsername(username);
        System.out.println("healthBtnClicked");

        initialize(username,techFeed1,healthFeed1,layoutview);

    }

    public void handleTechBtn(ActionEvent event) {
        System.out.println("start");

        saveFeedChoice(username,"technology",medi1);
        retrieveFeedsByUsername(username);
        System.out.println("techBtnClicked");
        System.out.println(layoutview + "intechbtn");
        initialize(username,techFeed1,healthFeed1,layoutview);

    }








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
            Stage stage = (Stage) cancel1.getScene().getWindow();
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
//        titleColumn.setCellFactory(column -> new ImageTableCell<>());
        parentContainer = (VBox) tableView.getParent();
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

        try {
            File brandingFile = new File("images/icon.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            iconImageView.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            File brandingFile = new File("images/icon.png");
            Image branding2 = new Image(brandingFile.toURI().toString());
            iconImageView1.setImage(branding2);

        } catch (Exception e) {
            e.printStackTrace();

        }

        loginController logincontroller=new loginController();
        String username=logincontroller.getUsername();
        fetchDataFromDatabase(username);
    }

    public void handleRemoveTech(ActionEvent event) {
        removeFeedChoice(username,"",medi1);
        retrieveFeedsByUsername(username);
        System.out.println("techBtnremove");
        initialize(username,techFeed1,healthFeed1,layoutview);

    }

    public void removeHandlehealth(ActionEvent event) {
        removeFeedChoice(username,tech1,"");
        retrieveFeedsByUsername(username);
        System.out.println("healthBtnremove");
        initialize(username,techFeed1,healthFeed1,layoutview);
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

    private void fetchDataFromDatabase(String username) {
        try {
            // Connect to the database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            String query = "SELECT firstName, lastName, userName, techFeed, healthFeed FROM user WHERE userName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // If a user with the given username is found
            if (resultSet.next()) {
                // Retrieve data from the result set
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String userName = resultSet.getString("userName");
                String techFeed = resultSet.getString("techFeed");
                String healthFeed = resultSet.getString("healthFeed");

                userNameLabel1.setText(userName);
            }

            // Close the all opened resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void allCategories(ActionEvent actionEvent) {
        displayrandomnews(topnewsurl,techurl,healthurl);
    }

    // Inside your method where you handle the button action




    public void Magazineview(ActionEvent event) {
        layoutview = "magazineview";
        LayoutView layout = new LayoutView();

        // Call the createCardView method to generate the card view UI component
        Parent cardView = layout.createMagazineView(newsarraylist);

        // Create a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(cardView);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);



//        scrollPane.setPrefViewportHeight(parentContainer.getHeight()); // Set preferred viewport height

        // Get the parent container of the TableView
        // Replace "tableViewParent" with the actual parent container ID in your FXML file
        parentContainer = (VBox) tableView.getParent();


//        if (parentContainer == null) {
//            parentContainer = (VBox) scrollPane.getContent().getParent(); // Get the parent of the content of the ScrollPane
//            scrollPane.setPrefViewportHeight(parentContainer.getHeight());
//        }

        scrollPane.setPrefViewportHeight(parentContainer.getHeight());
//
//        // Remove the TableView from the parent container
////        scrollPane.setPrefViewportHeight(parentContainer.getHeight());
//
        parentContainer.getChildren().clear();
//
//        // Add the ScrollPane containing the card views to the parent container
        parentContainer.getChildren().add(scrollPane);

        // Set the VBox containing the card views to be resizable and fill its parent container
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }
}