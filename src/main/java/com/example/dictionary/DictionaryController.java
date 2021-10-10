package com.example.dictionary;

import com.example.dictionary.components.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryController {
    // Cursed singleton pattern that absolutely does not belong to java
    public static DictionaryController instance;

    @FXML private TextField searchInputField;
    @FXML private ListView<String> searchResultPanel;
    @FXML private AnchorPane contentPanel;
    @FXML private Label errorDisplay;

    final private String[] contentFxml = new String[] {"word-details.fxml", "edit-word.fxml"};

    final private Map<String, Node> contentViews = new HashMap<>();
    final private Map<String, ContentController> contentControllers = new HashMap<>();

    String currentlySelectedWord;

    //STATE MANAGEMENT

    /**
     * Preload the different content views (word details, add word, delete words,... ) into memory.
     * Avoid having to re-read the fxml files. Also means that the state of these views are persistent.
     */
    private void preloadContentViews() throws IOException {
        for (String fxml : contentFxml) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            Node node = loader.load();
            ContentController controller = loader.getController();

            contentViews.put(fxml, node);
            contentControllers.put(fxml, controller);
        }
    }

    /**
     * Load the desired vbox and display them on the right panel
     */
    private void loadContentView(String fxmlName) {
        contentPanel.getChildren().clear();
        try {
            Node targetContent = contentViews.get(fxmlName);
            contentPanel.getChildren().add(targetContent);
            AnchorPane.setBottomAnchor(targetContent, 0.0);
            AnchorPane.setTopAnchor(targetContent, 0.0);
            AnchorPane.setLeftAnchor(targetContent, 0.0);
            AnchorPane.setRightAnchor(targetContent, 0.0);
        } catch (Exception e) {
            displayMessage("View has not been preloaded! \n" + e, MessageType.ERROR);
        }
    }

    /**
     * Search for similar words to user's query and display them onto searchResultPanel.
     * @param query user's input
     */
    public void displaySimilarWords(String query) {
        searchResultPanel.getItems().clear();
        // TODO: hook to backend for real input
        String[] words = new String[] {"TEST1", "TEST2", "TEST3"};

        for (String word : words) {
            searchResultPanel.getItems().add(word);
        }
    }

    /**
     * Remove the currently selected word from the database.
     */
    public void removeWord() {
        //TODO: Implement
        displayMessage("Removed " + currentlySelectedWord + " from database", MessageType.SUCCESS);
    }

    //UI NAVIGATION

    /**
     * Get the selected word within the words displayed in the search result.
     */
    public void readSelectedWord() {
        String selected = searchResultPanel.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentlySelectedWord = selected;
            switchToWordDetail();
            WordDetailsController controller = (WordDetailsController) contentControllers.get("word-details.fxml");
            controller.display(selected);
        }
    }

    public void performSearch() {
        String searchInput = searchInputField.getText();
        displaySimilarWords(searchInput);
    }

    public void switchToWordDetail() {
        if (currentlySelectedWord != null) {
            loadContentView("word-details.fxml");
        } else {
            contentPanel.getChildren().clear();
        }
    }

    public void switchToAddWord() {
        loadContentView("edit-word.fxml");
        EditWordController controller = (EditWordController) contentControllers.get("edit-word.fxml");
        controller.displayAdd();
    }

    public void switchToEditWord() {
        loadContentView("edit-word.fxml");
        EditWordController controller = (EditWordController) contentControllers.get("edit-word.fxml");
        controller.displayEdit(currentlySelectedWord);
    }

    public void hideMessage() {
        errorDisplay.setText("");
        errorDisplay.setVisible(false);
    }

    public void displayMessage(String message, MessageType type) {
        errorDisplay.setText(message);
        errorDisplay.getStyleClass().clear();
        errorDisplay.getStyleClass().add(type.getStyleClass());
        errorDisplay.setVisible(true);
    }

    /**
     * Called on loading time. Load different content panel types into memory.
     */
    public void initialize() throws IOException {
        instance = this;
        errorDisplay.setVisible(false);
        preloadContentViews();
    }
}