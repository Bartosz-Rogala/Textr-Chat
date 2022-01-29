module rogala.client {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires javafx.base;
	requires javafx.media;
	requires org.jsoup;
	requires java.desktop;

    opens rogala.client to javafx.fxml;
    exports rogala.client;
}
