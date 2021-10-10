package com.example.dictionary.components;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ErrorController implements ContentController {
    @FXML private TextArea errorText;

    public void display(String error) {
        errorText.setText(error);
    }

    @Override
    public void resetView() {
        errorText.setText("");
    }
}
