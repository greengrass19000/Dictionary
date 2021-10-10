package com.example.dictionary;

import com.example.dictionary.components.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryController {
    @FXML private TextField searchInputField;
    @FXML private ListView<String> searchResultPanel;
    @FXML private AnchorPane contentPanel;

    final private String[] contentFxml = new String[] {"error.fxml", "word-details.fxml", "add-word.fxml", "edit-word.fxml"};

    final private Map<String, VBox> contentViews = new HashMap<>();
    final private Map<String, ContentController> contentControllers = new HashMap<>();

    String currentlySelectedWord;

    //STATE MANAGEMENT

    private void displayError(String error) {
        loadContentView("error.fxml");
        ErrorController controller = (ErrorController) contentControllers.get("error.fxml");
        controller.display(error);
    }

    /**
     * Preload the different content views (word details, add word, delete words,... ) into memory
     * Avoid having to re-read the fxml files. Also means that the state of these views are persistent
     */
    private void preloadContentViews() throws IOException {
        for (String fxml : contentFxml) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            VBox vbox = loader.load();
            ContentController controller = loader.getController();

            contentViews.put(fxml, vbox);
            contentControllers.put(fxml, controller);
        }
    }

    /**
     * Load the desired vbox and display them on the right panel
     */
    private void loadContentView(String fxmlName) {
        contentPanel.getChildren().clear();
        try {
            VBox targetContent = contentViews.get(fxmlName);
            contentPanel.getChildren().add(targetContent);
            AnchorPane.setBottomAnchor(targetContent, 0.0);
            AnchorPane.setTopAnchor(targetContent, 0.0);
            AnchorPane.setLeftAnchor(targetContent, 0.0);
            AnchorPane.setRightAnchor(targetContent, 0.0);
        } catch (Exception e) {
            displayError("View has not been preloaded! \n" + e);
        }
    }

    /**
     * Search for similar words to user's query and display them onto searchResultPanel
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

    //UI INTERFACE (Called by interactions with the UI elements)

    /**
     * Get the selected word within the words displayed in the search result
     */
    public void readSelectedWord() {
        String selected = searchResultPanel.getSelectionModel().getSelectedItem();
        if (selected != null) {
            switchToWordDetail();
            WordDetailsController controller = (WordDetailsController) contentControllers.get("word-details.fxml");
            controller.display(selected);
            currentlySelectedWord = selected;
        }
    }

    public void performSearch() {
        String searchInput = searchInputField.getText();
        displaySimilarWords(searchInput);
    }

    public void switchToWordDetail() {
        loadContentView("word-details.fxml");
    }

    public void switchToAddWord() {
        loadContentView("add-word.fxml");
    }

    public void removeWord() {
        //TODO: Implement
        System.out.println("REMOVE " + currentlySelectedWord);
    }

    public void switchToEditWord() {
        //TODO: Implement
        System.out.println("EDIT " + currentlySelectedWord);
        loadContentView("edit-word.fxml");
    }

    //INITIALIZE

    /**
     * Called on loading time. Load different content panel types into memory
     */
    public void initialize() throws IOException {
        preloadContentViews();
    }
}