package org.rss.rssfeed.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ArticleData {
    private final StringProperty title;
    private final StringProperty imageUrl;
    public ArticleData(String imageUrl, String title) {
        this.title = new SimpleStringProperty(title);
        this.imageUrl = new SimpleStringProperty(imageUrl);
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

    public String getImageUrl() {
        return imageUrl.get();
    }

    public StringProperty imageUrlProperty() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }
}
