import sun.text.normalizer.Trie;
import java.lang.*;
import java.io.*;
import java.util.*;

class TrieNode {
    char data;
    boolean isEnd;
    int count;
    String mean;
    LinkedList<TrieNode> childList;

    /** Constructor */
    public TrieNode(char c) {
        childList = new LinkedList<>();
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

public class TrieDataStructure {
    private TrieNode root;
    private int wordlimit;
    /** Constructor */
    public TrieDataStructure()
    {
        root = new TrieNode(' ');
    }

    public TrieNode getRoot() {
        return root;
    }

    /** This function checks if the string doesn't have strange character, only space approved */
    public boolean Checkword(String word) {
        for(char l : word.toCharArray()) {
            if((l > 'z' || l < 'a') && (l > 'Z' || l < 'A') && l != ' ') {
                System.out.println("Illegal character!!!");
                return false;
            }
        }
        return true;
    }

    /** remove unnecessary space characters */
    public String processword(String word) {
        word = word.trim();
        String t = "";
        boolean spaceexisted = false;
        for(char l : word.toCharArray()) {
            if (l == ' ') {
                spaceexisted = true;
            } else {
                if(spaceexisted) {
                    t += ' ';
                    spaceexisted = false;
                }
                t += l;
            }
        }
        return t;
    }

    /** This function is used to insert a word in trie*/
    public void insert(String word, String mean) {
        if (search(word) == true || !Checkword(word))
            return;
        word = processword(word);
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

    /** This function is used to search a word in trie*/
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

    /** This function is used to remove function from trie*/
    public void remove(String word) {
        if(!Checkword(word)) return;
        word = processword(word);
        word.toLowerCase();
        if (search(word) == false) {
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


    /** This function is used to change the meaning of the word if existed*/
    public void change(String word, String mean) {
        if(!Checkword(word)) return;
        word = processword(word);
        word.toLowerCase();
        if (search(word) == false) {
            System.out.println("Word hasn't existed");
            return;
        }
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChild(ch);
        }
        current.mean = mean;
    }

    /** This funtion checks if there is at least one word containing the string s*/
    boolean search2(String s) {
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            if (current.getChild(ch) == null)
                return false;
            else
                current = current.getChild(ch);
        }
        return true;
    }

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
                t += s + "\n";
                --wordlimit;
            }
            t += Lookup(node, s);
            s = s.substring(0, s.length() - 1);
        }
        return t;
    }

    public void Lookup(String s, int amount) {
        if(!Checkword(s)) return;
        s = processword(s);
        s.toLowerCase();
        if(!search2(s)) return;
        wordlimit = amount;
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
                current = current.getChild(ch);
        }
        System.out.print(Lookup(current, s));
        current = root;
        s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
        for (char ch : s.toCharArray() ) {
                current = current.getChild(ch);
        }
        System.out.print(Lookup(current, s));
    }

    public void ShowAllWords(TrieNode root, String s) {
        TrieNode current = root;
        if(root.childList==null || root.childList.size()==0)
            return;
        Iterator<TrieNode> iter=current.childList.iterator();
        while(iter.hasNext()) {
            TrieNode node = iter.next();
            s+=node.data;
            if(node.isEnd == true) {
                //Test print
                System.out.print(s);
                for(int i = s.length(); i < 25; i++) System.out.print("-");
                System.out.println(node.mean);
                //Test print
            }
            ShowAllWords(node,s);
            s = s.substring(0, s.length() - 1);
        }
    }

}