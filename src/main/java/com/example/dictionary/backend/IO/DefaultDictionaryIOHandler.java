package com.example.dictionary.backend.IO;

import com.example.dictionary.backend.Idiom;
import com.example.dictionary.backend.TrieNode;
import com.example.dictionary.backend.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class DefaultDictionaryIOHandler implements DictionaryIOHandler {

    @Override
    public TrieNode read(String path) throws FileNotFoundException {
        TrieNode root = new TrieNode();
        File myObj = new File(path);
        Scanner myReader = new Scanner(myObj);
        myReader.nextLine();

        String s;
        while (myReader.hasNextLine()) {
            s = myReader.nextLine();
            char type = s.charAt(0);

            if(type ==  '@') {
                TrieNode current = root;
                s = s.substring(1);
                String[] word = s.split("/");
                LinkedList<Integer> tmp = new LinkedList<>();
                int i = 0;
                word[0] = word[0].substring(0, word[0].length() - 1);
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
                                case 1 -> current.type.getLast().addMean(s);
                                case 2 -> current.idiom.getLast().addMean(s);
                                default -> current.addMean(s);
                            }
                            break;
                        case '=':
                            switch (isType) {
                                case 1 -> current.type.getLast().addExample(s);
                                case 2 -> current.idiom.getLast().addExample(s);
                                default -> current.addMean(s);
                            }
                            break;
                        default:
                    }
                }
            }
        }
        myReader.close();
        return root;
    }

    @Override
    public void serialize(String path, TrieNode root) throws FileNotFoundException {
        //TODO: IMPLEMENT THIS
    }
}
