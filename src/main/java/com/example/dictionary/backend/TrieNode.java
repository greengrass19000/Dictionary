package com.example.dictionary.backend;

import java.util.LinkedList;

public class TrieNode {
    char data;
    boolean isEnd;
    String phonetic;
    LinkedList<String> mean;
    LinkedList<TrieNode> childList;
    LinkedList<Integer> upper;
    LinkedList<Idiom> idiom;
    LinkedList<Type> type;
    LinkedList<String> example;

    /** Constructor.*/
    public TrieNode(char c) {
        childList = new LinkedList<>();
        upper = new LinkedList<>();
        idiom = new LinkedList<>();
        type = new LinkedList<>();
        mean = new LinkedList<>();
        example = new LinkedList<>();
        isEnd = false;
        data = c;
        phonetic = "";
    }

    public TrieNode getChild(char c) {
        if (childList != null)
            for (TrieNode eachChild : childList)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }

    public void addMean(String s) { mean.add(s); }

    public void addExample(String s) { example.add(s); }

    public String getPhonetic() { return phonetic; }

    public String get() {
        String s = "";
        for(String ss : mean) {
            s += ss + "\n";
        }
        for(String ss : example) {
            s += ss + "\n";
        }
        for(Type t : type) {
            s += t.get();
        }
        for(Idiom i : idiom) {
            s += i.get();
        }
        return s;
    }
}
