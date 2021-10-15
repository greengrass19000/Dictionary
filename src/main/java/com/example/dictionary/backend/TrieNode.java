package com.example.dictionary.backend;

import java.util.LinkedList;

public class TrieNode {
    char data;
    boolean isEnd;
    int count;
    String mean;
    String phonetic;
    LinkedList<TrieNode> childList;
    LinkedList<Integer> upper;
    LinkedList<Idiom> idiom;
    LinkedList<Type> type;

    /** Constructor.*/
    public TrieNode(char c) {
        childList = new LinkedList<>();
        upper = new LinkedList<>();
        idiom = new LinkedList<>();
        type = new LinkedList<>();
        isEnd = false;
        data = c;
    }

    public TrieNode getChild(char c) {
        if (childList != null)
            for (TrieNode eachChild : childList)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }

    public String get() {
        String s = "";
        for(Type t : type) {
            s += t.get();
        }
        for(Idiom i : idiom) {
            s += i.get();
        }
        return s;
    }
}
