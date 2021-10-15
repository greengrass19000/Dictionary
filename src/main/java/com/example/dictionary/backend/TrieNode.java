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
        count = 0;
        mean = "";
    }

    public TrieNode getChild(char c) {
        if (childList != null)
            for (TrieNode eachChild : childList)
                if (eachChild.data == c)
                    return eachChild;
        return null;
    }
}
