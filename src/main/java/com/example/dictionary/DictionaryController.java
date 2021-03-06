package com.example.dictionary;

import com.example.dictionary.backend.TrieDataStructure;
import com.example.dictionary.backend.TrieNode;
import com.example.dictionary.components.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

public class DictionaryController {
    // Cursed singleton pattern that absolutely does not belong to java
    public static DictionaryController instance;

    @FXML private TextField searchInputField;
    @FXML private ListView<String> searchResultPanel;
    @FXML private AnchorPane contentPanel;
    @FXML private Label errorDisplay;
    @FXML private Label translateSuggestion;

    @FXML private Button translateButton;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button editButton;

    final private String[] contentFxml = new String[] {"word-details.fxml", "edit-word.fxml", "translate.fxml"};

    final private Map<String, Node> contentViews = new HashMap<>();
    final private Map<String, ContentController> contentControllers = new HashMap<>();

    private final TrieDataStructure Dictionary = new TrieDataStructure("Dictionary.txt");

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
        LinkedList<String> words = Dictionary.lookup(query, 10);

        if (words == null || words.isEmpty()) {
            translateSuggestion.setVisible(true);
        } else {
            translateSuggestion.setVisible(false);
            for (String word : words) {
                searchResultPanel.getItems().add(word);
            }
        }
    }

    /**
     * Remove the currently selected word from the database.
     */
    public void removeWord(String word) {
        contentPanel.getChildren().clear();
        Dictionary.remove(word);
        displayMessage("Removed " + word + " from database", MessageType.SUCCESS);
        performSearch();
    }

    public void removeWord() {
        if (currentlySelectedWord != null)
            removeWord(currentlySelectedWord);
    }

    public void addWord(String word, TrieNode wordNode) {
        Dictionary.create(word, wordNode);
        performSearch();
        WordDetailsController controller = (WordDetailsController) contentControllers.get("word-details.fxml");
        controller.display(word);
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

    public void switchToWordDetail(String word) {
        currentlySelectedWord = word;
        switchToWordDetail();
    }

    public void switchToAddWord() {
        loadContentView("edit-word.fxml");
        EditWordController controller = (EditWordController) contentControllers.get("edit-word.fxml");
        controller.displayAdd();
    }

    public void switchToEditWord() {
        if (currentlySelectedWord == null) return;
        loadContentView("edit-word.fxml");
        EditWordController controller = (EditWordController) contentControllers.get("edit-word.fxml");
        controller.displayEdit(currentlySelectedWord, Dictionary.tryGetNode(currentlySelectedWord));
    }

    public void switchToTranslate() {
        loadContentView("translate.fxml");
    }

    public void switchToTranslateSelected() {
        switchToTranslate();
        TranslateController controller = (TranslateController) contentControllers.get("translate.fxml");
        controller.setText(searchInputField.getText());
        controller.execute();
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

    public static TrieDataStructure getDictionary() {
        return instance.Dictionary;
    }


    private void setIcon(Button button, String path) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(15);
        imgView.setFitWidth(15);
        button.setGraphic(imgView);
    }
    /**
     * Called on loading time. Load different content panel types into memory.
     */
    public void initialize() throws IOException {
        instance = this;
        translateSuggestion.setVisible(false);
        errorDisplay.setVisible(false);
        preloadContentViews();

        setIcon(addButton, "add.png");
        setIcon(removeButton, "remove.png");
        setIcon(editButton, "edit.png");
        setIcon(translateButton, "translate.png");
    }
}