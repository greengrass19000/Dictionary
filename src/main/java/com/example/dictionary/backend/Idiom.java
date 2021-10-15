package com.example.dictionary.backend;

import java.util.LinkedList;

public class Idiom {
    String idiom;
    LinkedList<String> childList;

    Idiom(String s) {
        childList = new LinkedList<>();
        idiom = s;
    }

    public void add(String s) {
        childList.add(s);
    }

    public String get() {
        StringBuilder s = new StringBuilder(idiom);
        for(String t : childList) {
            s.append("            ").append(t).append("\n");
        }
        return s.toString();
    }
}
