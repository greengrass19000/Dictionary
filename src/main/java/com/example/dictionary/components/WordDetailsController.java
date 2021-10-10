package com.example.dictionary.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WordDetailsController implements ContentController {

    @FXML private Label wordLabel;
    @FXML private Label phoneticLabel;
    @FXML private Label translationLabel;
    @FXML private Label descriptionLabel;

    private String currentWord;

    @Override
    public void resetView() {
        wordLabel.setText("");
        phoneticLabel.setText("");
        translationLabel.setText("");
        descriptionLabel.setText("");
    }

    public void display(String word) {
        currentWord = word;
        //TODO: Actually do something that makes sense
        wordLabel.setText(word);
        phoneticLabel.setText("/"+word+"/");
        translationLabel.setText("translation of " + word);
        descriptionLabel.setText("this is a word, it means something");
    }

    public void playVolume() {
        System.out.println("PLAY VOLUME " + currentWord);
    }
}
