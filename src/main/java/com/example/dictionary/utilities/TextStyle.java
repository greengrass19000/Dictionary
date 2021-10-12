package com.example.dictionary.utilities;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class TextStyle {
    public Color color;
    public String fontName;
    public FontPosture fontPosture;
    public double fontSize;

    public TextStyle(Color color, String fontName, FontPosture fontPosture, double fontSize) {
        this.color = color;
        this.fontName = fontName;
        this.fontPosture = fontPosture;
        this.fontSize = fontSize;
    }

    public Text apply(String content) {
        Text text = new Text(content);
        text.setFont(Font.font(fontName, fontPosture, fontSize));
        text.setFill(color);
        return text;
    }

    public Text applyln(String content) {
        return apply(content + "\n");
    }
}