package org.rss.rssfeed.controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class ArticleData {
    private final StringProperty title;
//    private final StringProperty description;

    public ArticleData(String title) {
        this.title = new SimpleStringProperty(title);
//        this.description = new SimpleStringProperty(description);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

//    public String getDescription() {
//        return description.get();
//    }

//    public StringProperty descriptionProperty() {
//        return description;
//    }

//    public void setDescription(String description) {
//        this.description.set(description);
//    }}
}