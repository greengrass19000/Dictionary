package com.example.dictionary.backend;

import java.util.LinkedList;

public class Idiom {
    public String idiom;
    public LinkedList<String> mean;
    public LinkedList<String> example;

    public Idiom(String s) {
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("  ");
        s.append(idiom);
        s.append("\n");
        for(String t : mean) {
            s.append("     (nghĩa) ").append(t).append("\n");
        }
        for(String t : example) {
            s.append("          VD : ").append(t).append("\n");
        }
        return s.toString();
    }

    public String export(){
        StringBuilder s = new StringBuilder();
        s.append("!").append(idiom).append("\n");
        for(String t : mean) {
            s.append("- ").append(t).append("\n");
        }
        for(String t : example) {
            s.append("=").append(t).append("\n");
        }
        return s.toString();
    }
}
