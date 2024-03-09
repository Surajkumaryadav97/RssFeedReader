module org.rss.rssfeed {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires javafx.graphics;
    requires com.google.gson;
    requires javafx.web;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires org.jsoup;


    opens org.rss.rssfeed to javafx.fxml;
    exports org.rss.rssfeed;
    exports org.rss.rssfeed.controller;
    opens org.rss.rssfeed.controller to javafx.fxml;
    exports org.rss.rssfeed.model;
    opens org.rss.rssfeed.model to javafx.fxml;
}