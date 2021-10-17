package com.example.dictionary.backend;

import java.util.LinkedList;

public class Type{
    public String type;
    public LinkedList<String> mean;
    public LinkedList<String> example;

    public Type(String s) {
        type = s;
        mean = new LinkedList<>();
        example = new LinkedList<>();
    }

    public void addExample(String s) { example.add(s); }

    public void addMean(String s) { mean.add(s); }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("  ");
        s.append(type);
        s.append('\n');
        for(String ss : mean) {
            s.append("     (nghĩa) ").append(ss).append("\n");
        }
        for(String ss : example) {
            s.append("          VD : ").append(ss).append("\n");
        }
        return s.toString();
    }
}
