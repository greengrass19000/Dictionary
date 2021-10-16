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
        StringBuilder t = new StringBuilder();
        if (root.isEnd) {
            String ss = s;
            for (Integer l : root.upper) {
                char c = s.charAt(l);
                c = Character.toUpperCase(c);
                StringBuilder newStr = new StringBuilder(ss);
                newStr.setCharAt(l, c);
                ss = newStr.toString();
            }
            t.append(ss).append("\n");
            --wordLimit;
        }
        if(root.childList == null || root.childList.size() == 0)
            return t.toString();
        for (TrieNode node : root.childList) {
            if(wordLimit == 0) break;
            s += node.data;
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
            while (myReader.hasNextLine()) {
                s = myReader.nextLine();
                char type = s.charAt(0);
                if(type ==  '@') {
                    TrieNode current = root;
                    s = s.substring(1);
                    String[] word = s.split("/");
                    LinkedList<Integer> tmp = new LinkedList<>();
                    int i = 0;
                    word[0] = word[0].substring(0, word[0].length() - 1);;
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
                        ++i;
                    }
                    current.isEnd = true;
                    current.upper = tmp;
                    if(word.length > 1)
                        current.phonetic = word[1];
                    int isType = 0;
                    while(myReader.hasNextLine()) {
                        s  = myReader.nextLine();
                        if(Objects.equals(s, ""))
                            break;
                        type = s.charAt(0);
                        s = s.substring(1);
                        switch(type) {
                            case '*':
                                isType = 1;
                                s = s.trim();
                                current.type.add(new Type(s));
                                break;
                            case '!':
                                isType = 2;
                                current.idiom.add(new Idiom(s));
                                break;
                            case '-':
                                s = s.substring(1);
                                switch (isType) {
                                    case 1 :
                                        current.type.getLast().addMean(s);
                                        break;
                                    case 2 :
                                        current.idiom.getLast().addMean(s);
                                        break;
                                    default:
                                        current.addMean(s);
                                }
                                break;
                            case '=':
                                switch (isType) {
                                    case 1 :
                                        current.type.getLast().addExample(s);
                                        break;
                                    case 2 :
                                        current.idiom.getLast().addExample(s);
                                        break;
                                    default:
                                        current.addMean(s);
                                }
                                break;
                            default:
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

    /** This function is used to change the meaning of the word if existed.*/
    public void change(String word, String mean) {
        word = word.toLowerCase();
        if (!search(word)) {
            System.out.println("Word hasn't existed");
            return;
        }
        remove(word);

        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
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
            current = child;
        }
        current.isEnd = false;
    }

    public String get(String word) {
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        return current.get();
    }

    public void remove1(String word, Integer id) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.mean.size()) {
            return;
        }
        current.mean.remove(id - 1);
    }

    public void remove12(String word, Integer id) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.example.size()) {
            return;
        }
        current.example.remove(id - 1);
    }

    public void remove2(String word, Integer id) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.type.size()) {
            return;
        }
        current.type.remove(id - 1);
    }

    public void remove21(String word, Integer id, Integer id2) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.type.size()) {
            return;
        }
        if(id2 > current.type.get(id - 1).mean.size()) {
            return;
        }
        current.type.get(id - 1).mean.remove(id2 - 1);
    }

    public void remove22(String word, Integer id, Integer id2) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.type.size()) {
            return;
        }
        if(id2 > current.type.get(id - 1).example.size()) {
            return;
        }
        current.type.get(id - 1).example.remove(id2 - 1);
    }

    public void remove3(String word, Integer id) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.idiom.size()) {
            return;
        }
        current.idiom.remove(id - 1);
    }

    public void remove31(String word, Integer id, Integer id2) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.idiom.size()) {
            return;
        }
        if(id2 > current.idiom.get(id - 1).mean.size()) {
            return;
        }
        current.idiom.get(id - 1).mean.remove(id2 - 1);
    }

    public void remove32(String word, Integer id, Integer id2) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id > current.idiom.size()) {
            return;
        }
        if(id2 > current.idiom.get(id - 1).example.size()) {
            return;
        }
        current.idiom.get(id - 1).example.remove(id2 - 1);
    }

    public void add1(String word, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.mean.add(s);
    }

    public void add12(String word, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.example.add(s);
    }

    public void add2(String word, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.type.add(new Type(s));
    }

    public void add21(String word, Integer id, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id - 1 > current.type.size()) {
            return;
        }
        current.type.get(id - 1).mean.add(s);
    }

    public void add22(String word, Integer id, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id - 1 > current.type.size()) {
            return;
        }
        current.type.get(id - 1).example.add(s);
    }

    public void add3(String word, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.idiom.add(new Idiom(s));
    }

    public void add31(String word, Integer id, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id - 1 > current.idiom.size()) {
            return;
        }
        current.idiom.get(id - 1).mean.add(s);
    }

    public void add32(String word, Integer id, String s) {
        if(!search(word)) return;
        TrieNode current = root;
        for(char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        if(id - 1 > current.idiom.size()) {
            return;
        }
        current.idiom.get(id - 1).example.add(s);
    }

    /** This function is used to insert a word in trie.*/
    public void create(String word) {
        if (search(word))
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
        current.upper = tmp;
    }
}