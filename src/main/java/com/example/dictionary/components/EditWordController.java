package com.example.dictionary.components;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.MessageType;
import com.example.dictionary.backend.Idiom;
import com.example.dictionary.backend.TrieNode;
import com.example.dictionary.backend.Type;
import com.example.dictionary.utilities.TextFlowFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.Objects;


public class EditWordController implements ContentController {

    private enum EditMode {
        EDIT,
        ADD
    }
    private EditMode editMode;
    private String editingWord;
    private final ObservableList<String> choices = FXCollections.observableArrayList("Meaning", "Idiom", "Word type", "Example");

    @FXML private Label titleLabel;
    @FXML private TextField wordField;
    @FXML private TextField phoneticField;
    @FXML private VBox linesBox;

    private void createRow(int row) {
        createRow(row, "", "");
    }

    private void createRow(int row, String choice, String content) {
        Node node;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("edit-word-row.fxml"));
            node = loader.load();

            try {
                Node firstNode = ((AnchorPane) node).getChildren().get(0);
                Node secondNode = ((AnchorPane) node).getChildren().get(1);
                Node thirdNode = ((AnchorPane) node).getChildren().get(2);
                Node fourthNode = ((AnchorPane) node).getChildren().get(3);

                @SuppressWarnings("unchecked") //Blame javafx for not allowing to define choices within fxml
                ChoiceBox<String> choiceBox = (ChoiceBox<String>) firstNode;
                choiceBox.setItems(choices);
                choiceBox.setValue(choice);

                TextField textField = (TextField) secondNode;
                textField.setText(content);

                Button addRowButton = (Button) thirdNode;
                addRowButton.setOnAction(e -> createRow(row + 1));

                Button removeRowButton = (Button) fourthNode;
                removeRowButton.setOnAction(e -> removeRow(row));

                linesBox.getChildren().add(row, node);
            } catch(Exception e) {
                DictionaryController.instance.displayMessage("Error with edit-word-row.fxml", MessageType.ERROR);
            }

        } catch (IOException e) {
            DictionaryController.instance.displayMessage("Could not load edit-word-row.fxml", MessageType.ERROR);
        }
    }

    private void removeRow(int row) {
        linesBox.getChildren().remove(row);
        if (linesBox.getChildren().isEmpty()) {
            createRow(0);
        }
    }

    @Override
    public void resetView() {
        titleLabel.setText("No phrase selected");
        wordField.setText("");
        phoneticField.setText("");
        linesBox.getChildren().clear();
    }

    /**
     * Display the view for adding word to the database.
     */
    public void displayAdd() {
        resetView();
        createRow(0);
        editMode = EditMode.ADD;
        titleLabel.setText("Adding a new phrase:");
    }

    /**
     * Display the view for editing an existing entry.
     * @param word The word chosen
     */
    public void displayEdit(String word, TrieNode wordNode) {
        resetView();
        editMode = EditMode.EDIT;
        editingWord = word;
        titleLabel.setText("Editing \"" + word + "\"");

        wordField.setText(word);
        phoneticField.setText(wordNode.phonetic);

        int row = 0;
        for (String meaning : wordNode.mean) {
            createRow(row++, choices.get(0), meaning);
        }
        for (String example : wordNode.example) {
            createRow(row++, choices.get(1), example);
        }
        for (Type type : wordNode.type) {
            createRow(row++, choices.get(2), type.type);
            for (String meaning : type.mean) {
                createRow(row++, choices.get(0), meaning);
            }
            for (String example : type.example) {
                createRow(row++, choices.get(1), example);
            }
        }
        for (Idiom idiom : wordNode.idiom) {
            createRow(row++, choices.get(3), idiom.idiom);
            for (String meaning : idiom.mean) {
                createRow(row++, choices.get(0), meaning);
            }
            for (String example : idiom.example) {
                createRow(row++, choices.get(1), example);
            }
        }
    }

    /**
     * Confirm user's action and write to database.
     */
    public void confirm() {
        String word = wordField.getText();
        String phonetic = phoneticField.getText();

        if (Objects.equals(word, "")) {
            DictionaryController.instance.displayMessage("Phrase field can not be empty!", MessageType.ERROR);
            return;
        }

        if (editMode == EditMode.EDIT) {
            DictionaryController.instance.removeWord(word);
        }

        TrieNode wordNode = new TrieNode();
        wordNode.phonetic = phonetic;

        LinkedList<String> exampleListToAdd = wordNode.example;
        LinkedList<String> meaningListToAdd = wordNode.mean;

        for (Node rowNode : linesBox.getChildren()) {
            AnchorPane row = (AnchorPane) rowNode;
            Node firstNode  = row.getChildren().get(0);
            Node secondNode = row.getChildren().get(1);

            @SuppressWarnings("unchecked")
            String lineChoice = ((ChoiceBox<String>) firstNode).getValue();
            String content = ((TextField) secondNode).getText();

            switch (lineChoice) {
                case "Meaning" -> meaningListToAdd.add(content);
                case "Example" -> exampleListToAdd.add(content);
                case "Word type" -> {
                    Type type = new Type(content);
                    wordNode.type.add(type);
                    exampleListToAdd = type.example;
                    meaningListToAdd = type.mean;
                }
                case "Idiom" -> {
                    Idiom idiom = new Idiom(content);
                    wordNode.idiom.add(idiom);
                    exampleListToAdd = idiom.example;
                    meaningListToAdd = idiom.mean;
                }
            }
        }

        DictionaryController.instance.addWord(word, wordNode);

        if (editMode == EditMode.ADD) {
            DictionaryController.instance.displayMessage("Added \"" + word + "\" to the database", MessageType.SUCCESS);
        } else if (editMode == EditMode.EDIT) {
            DictionaryController.instance.displayMessage("Successfully edited \"" + word + "\"", MessageType.SUCCESS);
        }

        DictionaryController.instance.switchToWordDetail(word);
    }

    /**
     * Cancel the action and go back.
     */
    public void cancel() {
        if (editMode == EditMode.EDIT) {
            DictionaryController.instance.switchToWordDetail(editingWord);
        } else if (editMode == EditMode.ADD) {
            DictionaryController.instance.switchToWordDetail();
        }
    }
}
