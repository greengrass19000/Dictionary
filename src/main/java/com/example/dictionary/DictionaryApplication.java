package com.example.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DictionaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, NullPointerException {
        FXMLLoader fxmlLoader = new FXMLLoader(DictionaryApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = Objects.requireNonNull(DictionaryApplication.class.getResource("style.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}