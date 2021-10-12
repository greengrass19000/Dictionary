package com.example.dictionary.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextFlow;
import com.example.dictionary.utilities.TextFlowFormatter;

public class WordDetailsController implements ContentController {

    @FXML private Label wordLabel;
    @FXML private Label phoneticLabel;
    @FXML private TextFlow description;

    private String currentWord;

    @Override
    public void resetView() {
        wordLabel.setText("");
        phoneticLabel.setText("");
        description.getChildren().clear();
    }

    public void display(String word) {
        currentWord = word;
        //TODO: Actually do something that makes sense
        wordLabel.setText(word);
        phoneticLabel.setText("/" + word + "/");
        TextFlowFormatter.formatDescription(description, word + "description", true);
    }

    public void playVolume() {
        System.out.println("PLAY VOLUME " + currentWord);
    }
}
