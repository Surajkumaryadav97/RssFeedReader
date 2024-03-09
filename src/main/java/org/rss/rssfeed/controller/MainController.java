package org.rss.rssfeed.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label linkLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load XML file
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("https://timesofindia.indiatimes.com/rssfeedstopstories.cms");

            // Normalize XML structure
            doc.getDocumentElement().normalize();

            // Get item elements
            NodeList itemList = doc.getElementsByTagName("item");

            for (int i = 0; i < itemList.getLength(); i++) {
                if (itemList.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element item = (Element) itemList.item(i);

                    // Get description
                    String description = item.getElementsByTagName("description").item(0).getTextContent();
                    descriptionLabel.setText("Description: " + description);
                    System.out.println(description);

                    // Get link
                    String link = item.getElementsByTagName("link").item(0).getTextContent();
                    linkLabel.setText("Link: " + link);

                    // We'll just handle the first item for this example

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

