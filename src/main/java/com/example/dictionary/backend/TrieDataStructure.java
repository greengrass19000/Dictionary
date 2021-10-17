package com.example.dictionary.backend;

import com.example.dictionary.DictionaryController;
import com.example.dictionary.MessageType;
import com.example.dictionary.backend.IO.DefaultDictionaryIOHandler;
import com.example.dictionary.backend.IO.DictionaryIOHandler;

import java.lang.*;
import java.util.*;
import java.io.FileNotFoundException;

public class TrieDataStructure {
    public TrieNode root;
    private final String path;
    private int wordLimit;
    private final DictionaryIOHandler dictionaryIOHandler = new DefaultDictionaryIOHandler();

    public TrieDataStructure(String path) {
        this.path = path;
        readFromFile(path);
    }

    /** This function is used to search a word in trie.*/
    public boolean isWordExists(String word) {
        word = word.toLowerCase();
        TrieNode current = root;
        for (char ch : word.toCharArray() ) {
            if (current.getChild(ch) == null)
                return false;
            else
                current = current.getChild(ch);
        }
        return current.isEnd;
    }

    /** This function checks if there is at least one word containing the string s.*/
    private boolean isWordExistsContaining(String s) {
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            if (current.getChild(ch) == null)
                return false;
            else
                current = current.getChild(ch);
        }
        return true;
    }

    private void serialize() {
        try {
            dictionaryIOHandler.serialize(path, root);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** This function suggest the words for the current string.*/
    private LinkedList<String> lookup(TrieNode root, String s) {
        LinkedList<String> results = new LinkedList<>();
        if(wordLimit == 0) return results;

        if (root.isEnd) {
            String ss = s;
            for (Integer l : root.upper) {
                char c = s.charAt(l);
                c = Character.toUpperCase(c);
                StringBuilder newStr = new StringBuilder(ss);
                newStr.setCharAt(l, c);
                ss = newStr.toString();
            }
            results.add(ss);
            --wordLimit;
        }

        if(root.childList == null || root.childList.size() == 0)
            return results;
        for (TrieNode node : root.childList) {
            if(wordLimit == 0) break;
            s += node.data;
            results.addAll(lookup(node, s));
            s = s.substring(0, s.length() - 1);
        }
        return results;
    }

    public LinkedList<String> lookup(String s, int amount) {
        s = s.toLowerCase();
        if(!isWordExistsContaining(s)) return null;
        wordLimit = amount;
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            current = current.getChild(ch);
        }
        return lookup(current, s);
    }

    public TrieNode tryGetNode(String word) {
        word = word.toLowerCase();
        if(!isWordExists(word)) {
            DictionaryController.instance.displayMessage("Word does not exists in the database", MessageType.ERROR);
            return null;
        }
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        return current;
    }

    /** Import Dictionary.*/
    public void readFromFile(String path) {
        try {
            root = dictionaryIOHandler.read(path);
        } catch (FileNotFoundException e) {
            DictionaryController.instance.displayMessage("Could not read the database", MessageType.ERROR);
            e.printStackTrace();
        }
    }

    /** This function is used to change the meaning of the word if existed.*/
    public void change(String word, String mean) {
        word = word.toLowerCase();
        if (!isWordExists(word)) {
            DictionaryController.instance.displayMessage("Word does not exists in the database", MessageType.ERROR);
            return;
        }

        remove(word);
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        serialize();
    }

    /** This function is used to remove function from trie.*/
    public void remove(String word) {
        word = word.toLowerCase();
        TrieNode current = tryGetNode(word);
        if (current == null) return;
        current.isEnd = false;
        serialize();
    }

    /** This function is used to insert a word in trie.*/
    public void create(String word, TrieNode nodeData) {
        if (isWordExists(word))
            return;
        TrieNode current = root;
        LinkedList<Integer> tmp = new LinkedList<>();
        int i = 0;
        for (char ch : word.toCharArray() ) {
            if (Character.isUpperCase(ch)) {
                tmp.add(i);
                ch = Character.toLowerCase(ch);
            }
            TrieNode child = current.getChild(ch);
            if (child != null)
                current = child;
            else {
                current.childList.add(new TrieNode(ch));
                current = current.getChild(ch);
            }
            ++i;
        }
        current.isEnd = true;
        current.applyData(nodeData);
        current.upper = tmp;
        serialize();
    }
}