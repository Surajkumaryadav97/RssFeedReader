package org.rss.rssfeed.model;

public class NewsModel {
    private String desc;
    private String link;

    private String image;

    public NewsModel(String desc, String link, String image) {
        this.desc = desc;
        this.link = link;
        this.image = image;
    }

    public NewsModel() {
    }

    public String getDesc() {
        return desc;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
