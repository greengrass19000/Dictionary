package com.example.dictionary.components;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.backend.TextSpeech;
import com.example.dictionary.backend.TrieNode;
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
        TrieNode wordNode = DictionaryController.getDictionary().tryGetNode(word);
        if (wordNode == null) return;

        wordLabel.setText(word);
        phoneticLabel.setText("/" + wordNode.phonetic + "/");
        TextFlowFormatter.formatDescription(description, wordNode, true);
    }

    public void playVolume() {
        TextSpeech.read(currentWord);
    }
}
