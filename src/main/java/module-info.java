module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    requires jsapi;
    requires cmu.us.kal;
    requires en.us;
    requires freetts;
    requires freetts.jsapi10;
    requires cmudict04;
    requires cmu.time.awb;
    requires cmulex;
    requires java.net.http;
    requires okhttp3;
    requires commons.lang;

    opens com.example.dictionary to javafx.fxml;
    exports com.example.dictionary;
    opens com.example.dictionary.components to javafx.fxml;
    exports com.example.dictionary.backend;
    exports com.example.dictionary.utilities;
}