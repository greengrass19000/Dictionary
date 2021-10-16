package com.example.dictionary.backend;

import java.util.LinkedList;

public class Type{
    String type = "";
    LinkedList<String> mean;
    LinkedList<String> example;

    Type(String s) {
        type = s;
        mean = new LinkedList<>();
        example = new LinkedList<>();
    }

    public void addExample(String s) { example.add(s); }

    public void addMean(String s) { mean.add(s); }

    public String get() {
        String s = "";
        s += "  ";
        s += type;
        s += '\n';
        for(String ss : mean) {
            s += "     (nghĩa) " + ss + "\n";
        }
        for(String ss : example) {
            s += "          VD : " + ss + "\n";
        }
        return s;
    }
}
