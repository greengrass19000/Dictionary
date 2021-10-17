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
    public String prefix;

    public TextStyle(Color color, String fontName, FontPosture fontPosture, double fontSize, String prefix) {
        this.color = color;
        this.fontName = fontName;
        this.fontPosture = fontPosture;
        this.fontSize = fontSize;
        this.prefix = prefix;
    }

    public Text apply(String content) {
        content = prefix + content;
        Text text = new Text(content);
        text.setFont(Font.font(fontName, fontPosture, fontSize));
        text.setFill(color);
        return text;
    }

    public Text applyLine(String content) {
        return apply(content + "\n");
    }
}