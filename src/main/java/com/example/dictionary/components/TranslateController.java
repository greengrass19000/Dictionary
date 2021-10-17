package com.example.dictionary.components;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.MessageType;
import com.example.dictionary.utilities.TranslateAPI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TranslateController implements ContentController {

    @FXML TextField inputField;
    @FXML TextField outputField;

    @Override
    public void resetView() {
        inputField.setText("");
        outputField.setText("");
    }

    public void execute() {
        try {
            String result = TranslateAPI.request(inputField.getText());
            outputField.setText(result);
        } catch (IOException e) {
            DictionaryController.instance.displayMessage("Error sending request", MessageType.ERROR);
        }
    }

    public void setText(String text) {
        inputField.setText(text);
    }
}
