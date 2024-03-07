package org.rss.rssfeed.controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class ArticleData {
    private final StringProperty title;


    public ArticleData(String title) {
        this.title = new SimpleStringProperty(title);

    }

    public StringProperty titleProperty() {
        return title;
    }



}