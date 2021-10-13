package com.example.dictionary.components;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.MessageType;
import javafx.collections.FXCollections;
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
import java.util.Objects;


public class EditWordController implements ContentController {

    private enum EditMode {
        EDIT,
        ADD
    }
    private EditMode editMode;

    @FXML private Label titleLabel;
    @FXML private TextField wordField;
    @FXML private TextField phoneticField;
    @FXML private VBox linesBox;

    private void createRow(int row) {
        Node node;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("edit-word-row.fxml"));
            node = loader.load();

            try {
                Node firstNode = ((AnchorPane) node).getChildren().get(0);
                Node thirdNode = ((AnchorPane) node).getChildren().get(2);
                Node fourthNode = ((AnchorPane) node).getChildren().get(3);

                @SuppressWarnings("unchecked") //Blame javafx for not allowing to define choices within fxml
                ChoiceBox<String> choiceBox = (ChoiceBox<String>) firstNode;
                choiceBox.setItems(FXCollections.observableArrayList("Word type", "Translation", "Example"));

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

    public void initialize() {
        createRow(0);
    }

    @Override
    public void resetView() {
        titleLabel.setText("No phrase selected");
        wordField.setText("");
        phoneticField.setText("");
        linesBox.getChildren().clear();
        createRow(0);
    }

    /**
     * Display the view for adding word to the database.
     */
    public void displayAdd() {
        editMode = EditMode.ADD;
        titleLabel.setText("Adding a new phrase:");
        resetView();
    }

    /**
     * Display the view for editing an existing entry.
     * @param word The word chosen
     */
    public void displayEdit(String word) {
        editMode = EditMode.EDIT;
        titleLabel.setText("Editing \"" + word + "\"");
        //TODO: get and fill in word data
        wordField.setText("temp");
        phoneticField.setText("temp");
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

        //TODO: save the data
        if (editMode == EditMode.ADD) {
            DictionaryController.instance.displayMessage("Added \"" + word + "\" to the database", MessageType.SUCCESS);
        } else if (editMode == EditMode.EDIT) {
            DictionaryController.instance.displayMessage("Successfully edited \"" + word + "\"", MessageType.SUCCESS);
        }
    }

    /**
     * Cancel the action and go back.
     */
    public void cancel() {
        DictionaryController.instance.switchToWordDetail();
    }
}
