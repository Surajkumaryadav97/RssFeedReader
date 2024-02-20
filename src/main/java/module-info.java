module org.rss.rssfeed {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;


    opens org.rss.rssfeed to javafx.fxml;
    exports org.rss.rssfeed;
    exports org.rss.rssfeed.controller;
    opens org.rss.rssfeed.controller to javafx.fxml;
}