package com.example.dictionary.backend.IO;

import com.example.dictionary.backend.Idiom;
import com.example.dictionary.backend.TrieNode;
import com.example.dictionary.backend.Type;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class DefaultDictionaryIOHandler implements DictionaryIOHandler {
    FileWriter myWriter;

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
        try {
            myWriter = new FileWriter("Dictionary.txt");
            myWriter.write("\n");
            Export(root, "", myWriter);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void Export(TrieNode root, String s, FileWriter myWriter) throws IOException {
        if(root.childList==null || root.childList.size()==0)
            return;
        for (TrieNode node : root.childList) {
            s += node.data;
            if (node.isEnd) {
                //Test print
                String ss = s;
                for (Integer l : root.upper) {
                    char c = s.charAt(l);
                    c = Character.toUpperCase(c);
                    StringBuilder newStr = new StringBuilder(ss);
                    newStr.setCharAt(l, c);
                    ss = newStr.toString();
                }
                myWriter.write("@");
                myWriter.write(ss);
                myWriter.write(" ");
                myWriter.write(node.export());
                myWriter.write("\n");
                //Test print
            }
            Export(node, s, myWriter);
            s = s.substring(0, s.length() - 1);
        }
    }
}
