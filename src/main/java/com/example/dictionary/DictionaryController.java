package com.example.dictionary;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DictionaryController {
    @FXML private TextField searchInputField;
    @FXML private ListView<String> searchResultPanel;

    private void displaySimilarWords(String target) {
        // TODO: hook to backend for real input
        String[] words = new String[] {"TEST1", "TEST2", "TEST3"};

        for (String word : words) {
            searchResultPanel.getItems().add(word);
        }
    }

    public void readSelectedWord() {
        String selected = searchResultPanel.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // TODO: display content on the right panel
            System.out.println(selected);
        }
    }

    public void performSearch() {
        String searchInput = searchInputField.getText();
        displaySimilarWords(searchInput);
    }
}