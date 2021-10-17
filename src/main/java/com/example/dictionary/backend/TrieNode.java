package com.example.dictionary.backend;

import java.util.LinkedList;

public class TrieNode {
    char data;
    public boolean isEnd = false;
    public String phonetic = "";
    public LinkedList<TrieNode> childList = new LinkedList<>();
    public LinkedList<Integer> upper = new LinkedList<>();
    public LinkedList<String> mean = new LinkedList<>();
    public LinkedList<Idiom> idiom = new LinkedList<>();
    public LinkedList<Type> type = new LinkedList<>();
    public LinkedList<String> example = new LinkedList<>();

    /** Constructor.*/
    public TrieNode(char c) {
        data = c;
    }
    
    public TrieNode() {
        data = ' ';
    }

    public void applyData(TrieNode other)
    {
        mean = other.mean;
        idiom = other.idiom;
        type = other.type;
        example = other.example;
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(String ss : mean) {
            System.out.println("AAA");
            s.append(ss).append("\n");
        }
        for(String ss : example) {
            System.out.println("AAA");
            s.append(ss).append("\n");
        }
        for(Type t : type) {
            s.append(t.toString());
        }
        for(Idiom i : idiom) {
            s.append(i.toString());
        }
        return s.toString();
    }
}
