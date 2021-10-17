package com.example.dictionary.utilities;

import com.example.dictionary.backend.Idiom;
import com.example.dictionary.backend.TrieNode;
import com.example.dictionary.backend.Type;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextFlow;

public class TextFlowFormatter {

    private static final TextStyle wordTypeStyle   = new TextStyle(Color.rgb(24, 74, 94), "System", FontPosture.REGULAR, 20, "・");
    private static final TextStyle meaningStyle    = new TextStyle(Color.rgb(0, 0, 0), "System", FontPosture.REGULAR, 17, "");
    private static final TextStyle exampleStyle    = new TextStyle(Color.rgb(74, 74, 74), "System", FontPosture.REGULAR, 15, "- ");

    public static void formatDescription(TextFlow textFlow, TrieNode wordNode, boolean clearExistingNodes) {
        textFlow.setLineSpacing(5f);
        ObservableList<Node> textFlowNode = textFlow.getChildren();
        if (clearExistingNodes) {
            textFlowNode.clear();
        }

        for (String meaning : wordNode.mean) {
            textFlowNode.add(meaningStyle.applyLine(meaning));
        }

        for (String example : wordNode.example) {
            textFlowNode.add(exampleStyle.applyLine(example));
        }

        for (Type type : wordNode.type) {
            textFlowNode.add(wordTypeStyle.applyLine(type.type));
            for (String meaning : type.mean) {
                textFlowNode.add(meaningStyle.applyLine(meaning));
            }

            for (String example : type.example) {
                textFlowNode.add(exampleStyle.applyLine(example));
            }
        }

        for (Idiom idiom : wordNode.idiom) {
            textFlowNode.add(exampleStyle.applyLine(idiom.idiom));
            for (String meaning : idiom.mean) {
                textFlowNode.add(meaningStyle.applyLine(meaning));
            }

            for (String example : idiom.example) {
                textFlowNode.add(exampleStyle.applyLine(example));
            }
        }
    }
}