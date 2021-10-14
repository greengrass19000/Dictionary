import sun.awt.image.ImageWatched;
import sun.text.normalizer.Trie;
import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class TrieDataStructure {
    public TrieNode root;
    private int wordlimit;

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
        if (current.isEnd == true)
            return true;
        return false;
    }

    /** This funtion checks if there is at least one word containing the string s.*/
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

    /** This funtion suggest the words for the current string.*/
    private String Lookup(TrieNode root, String s) {
        if(wordlimit == 0) return "";
        TrieNode current = root;
        if(root.childList == null || root.childList.size() == 0)
            return "";
        String t = "";
        Iterator<TrieNode> iter=current.childList.iterator();
        while(iter.hasNext()) {
            TrieNode node = iter.next();
            s += node.data;
            if(node.isEnd == true) {
                String s2 = s;
                for (Integer l : current.upper) {
                    Character.toUpperCase(s.charAt(l));
                }
                t += s2 + "\n";
                --wordlimit;
            }
            t += Lookup(node, s);
            s = s.substring(0, s.length() - 1);
        }
        return t;
    }

    public void Lookup(String s, int amount) {
        s = s.toLowerCase();
        if(!search2(s)) return;
        wordlimit = amount;
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            current = current.getChild(ch);
        }
        System.out.print(Lookup(current, s));
    }

    /** Import Dictionary.*/
    public void ReadFromFile() {
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
                    InsertWordFromFile(current, s);
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
                                    current.type.getLast().addmean(s);
                                } else {
                                    current.idiom.getLast().add(s);
                                }
                                break;
                            case '=':
                                current.type.getLast().addexample(s);
                                break;
                            default:
                                current.type.getLast().addphonetic(s);
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

    private void InsertWordFromFile(TrieNode current, String s) {
        s = s.substring(1);
        String[] word = s.split(" ");
        LinkedList<Integer> tmp = new LinkedList<Integer>();
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

}