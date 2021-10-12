package com.example.dictionary.utilities;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextFlow;

public class TextFlowFormatter {

    private static final TextStyle wordTypeStyle   = new TextStyle(Color.rgb(24, 74, 94), "System", FontPosture.REGULAR, 20);
    private static final TextStyle meaningStyle    = new TextStyle(Color.rgb(0, 0, 0), "System", FontPosture.REGULAR, 17);
    private static final TextStyle derivationStyle = new TextStyle(Color.rgb(74, 74, 74), "System", FontPosture.REGULAR, 15);

    public static void formatDescription(TextFlow textFlow, String description, boolean clearExistingNodes) {
        textFlow.setLineSpacing(5f);
        ObservableList<Node> textFlowNode = textFlow.getChildren();
        if (clearExistingNodes) {
            textFlowNode.clear();
        }
        //TODO: parse description into components or use a wrapper class instead
        textFlowNode.add(wordTypeStyle.applyln(description));
        textFlowNode.add(meaningStyle.applyln(description));
        textFlowNode.add(derivationStyle.applyln(description));
    }
}