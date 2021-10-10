package com.example.dictionary.components;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    @FXML private TextField translationField;
    @FXML private TextField descriptionField;

    @Override
    public void resetView() {
        titleLabel.setText("No phrase selected");
        wordField.setText("");
        phoneticField.setText("");
        translationField.setText("");
        descriptionField.setText("");
    }

    /**
     * Display the view for adding word to the database.
     */
    public void displayAdd() {
        editMode = EditMode.ADD;
        titleLabel.setText("Adding a new phrase:");
        wordField.setText("");
        phoneticField.setText("");
        translationField.setText("");
        descriptionField.setText("");
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
        translationField.setText("temp");
        descriptionField.setText("temp");
    }

    /**
     * Confirm user's action and write to database.
     */
    public void confirm() {
        String word = wordField.getText();
        String phonetic = phoneticField.getText();
        String translation = translationField.getText();
        String description = descriptionField.getText();

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
