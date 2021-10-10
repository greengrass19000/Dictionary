module com.example.dictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.example.dictionary to javafx.fxml;
    exports com.example.dictionary;
    opens com.example.dictionary.components to javafx.fxml;
}