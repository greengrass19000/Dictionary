package com.example.dictionary.backend;

import java.util.LinkedList;

public class Meaning {
    String mean;
    LinkedList<String> childList;

    Meaning(String s) {
        mean = s;
        childList = new LinkedList<>();
    }

    public void add(String mean) {
        childList.add(mean);
    }

    public String get() {
        StringBuilder s = new StringBuilder(mean);
        for(String t : childList) {
            s.append("            ").append(t).append("\n");
        }
        return s.toString();
    }
}
