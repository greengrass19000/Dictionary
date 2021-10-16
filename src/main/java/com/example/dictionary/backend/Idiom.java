package com.example.dictionary.backend;

import java.util.LinkedList;

public class Idiom {
    String idiom;
    LinkedList<String> mean;
    LinkedList<String> example;

    Idiom(String s) {
        mean = new LinkedList<>();
        example = new LinkedList<>();
        idiom = s;
    }

    public void addMean(String s) {
        mean.add(s);
    }

    public void addExample(String s) {
        example.add(s);
    }

    public String get() {
        StringBuilder s = new StringBuilder();
        s.append("  ");
        s.append(idiom);
        s.append('\n');
        for(String t : mean) {
            s.append("     (nghĩa) ").append(t).append("\n");
        }
        for(String t : example) {
            s.append("          VD : ").append(t).append("\n");
        }
        return s.toString();
    }
}
