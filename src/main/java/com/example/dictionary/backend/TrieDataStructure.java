package com.example.dictionary.backend;

import java.lang.*;
import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TrieDataStructure {
    public TrieNode root;
    private int wordLimit;

    public TrieDataStructure() {
        root = new TrieNode(' ');
    }

    /** This function is used to search a word in trie.*/
    public boolean search(String word) {
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
    private boolean search2(String s) {
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            if (current.getChild(ch) == null)
                return false;
            else
                current = current.getChild(ch);
        }
        return true;
    }

    /** This function suggest the words for the current string.*/
    private String lookup(TrieNode root, String s) {
        if(wordLimit == 0) return "";
        if(root.childList == null || root.childList.size() == 0)
            return "";
        StringBuilder t = new StringBuilder();
        for (TrieNode node : root.childList) {
            s += node.data;
            if (node.isEnd) {
                for (Integer l : root.upper) {
                    char c = s.charAt(l);
                    c = Character.toUpperCase(c);

                    StringBuilder newStr = new StringBuilder(s);
                    newStr.setCharAt(l, c);
                    s = newStr.toString();
                }
                t.append(s).append("\n");
                --wordLimit;
            }
            t.append(lookup(node, s));
            s = s.substring(0, s.length() - 1);
        }
        return t.toString();
    }

    public void lookup(String s, int amount) {
        s = s.toLowerCase();
        if(!search2(s)) return;
        wordLimit = amount;
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            current = current.getChild(ch);
        }
        System.out.print(lookup(current, s));
    }

    /** Import Dictionary.*/
    public void readFromFile() {
        try {
            File myObj = new File("Dictionary.txt");
            Scanner myReader = new Scanner(myObj);
            String s = myReader.nextLine();
            String l = "";
            while (myReader.hasNextLine()) {
                s = myReader.nextLine();
                char type = s.charAt(0);
                if(type ==  '@') {
                    TrieNode current = root;
                    insertWordFromFile(current, s);
                    boolean isType = true;
                    while(myReader.hasNextLine()) {
                        s  = myReader.nextLine();
                        if(Objects.equals(s, ""))
                            break;
                        type = s.charAt(0);
                        s = s.substring(1);
                        switch(type) {
                            case '*':
                                isType = true;
                                current.type.add(new Type(s));
                                break;
                            case '!':
                                isType = false;
                                current.idiom.add(new Idiom(s));
                                break;
                            case '-':
                                if(isType) {
                                    current.type.getLast().addMean(s);
                                } else {
                                    current.idiom.getLast().add(s);
                                }
                                break;
                            case '=':
                                current.type.getLast().addexample(s);
                                break;
                            default:
                                current.type.getLast().addPhonetic(s);
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void insertWordFromFile(TrieNode current, String s) {
        s = s.substring(1);
        String[] word = s.split(" ");
        LinkedList<Integer> tmp = new LinkedList<>();
        int i = 0;
        for (char ch : word[0].toCharArray()) {
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
            current.count++;
            ++i;
        }
        current.isEnd = true;
        current.upper = tmp;
        if(word.length > 1)
            current.phonetic = word[1];
    }

    public void showAllWords(TrieNode root, String s) {
        if(root.childList==null || root.childList.size()==0)
            return;
        for (TrieNode node : root.childList) {
            s += node.data;
            if (node.isEnd) {
                //Test print
                System.out.print(s);
                for (int i = s.length(); i < 30; i++) System.out.print("-");
                System.out.println(node.mean);
                //Test print
            }
            showAllWords(node, s);
            s = s.substring(0, s.length() - 1);
        }
    }

    /** This function is used to change the meaning of the word if existed.*/
    public void change(String word, String mean) {
        word = word.toLowerCase();
        if (!search(word)) {
            System.out.println("Word hasn't existed");
            return;
        }
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.mean = mean;
    }

    /** This function is used to insert a word in trie.*/
    public void insert(String word, String mean) {
        if (search(word))
            return;
        TrieNode current = root;
        for (char ch : word.toCharArray() ) {
            TrieNode child = current.getChild(ch);
            if (child != null)
                current = child;
            else {
                // If child not present, adding it io the list
                current.childList.add(new TrieNode(ch));
                current = current.getChild(ch);
            }
            current.count++;
        }
        current.isEnd = true;
        current.mean = mean;
    }

    /** This function is used to remove function from trie.*/
    public void remove(String word) {
        word = word.toLowerCase();
        if (!search(word)) {
            System.out.println(word +" does not exist");
            return;
        }
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode child = current.getChild(ch);
            if (child.count == 1) {
                current.childList.remove(child);
                return;
            }
            else {
                child.count--;
                current = child;
            }
        }
        current.isEnd = false;
    }
}