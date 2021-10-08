import sun.text.normalizer.Trie;

import java.util.*;

class TrieNode {
    char data;
    boolean isEnd;
    int count;
    String mean;
    LinkedList<TrieNode> childList;

    /* Constructor */
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

    /* Constructor */
    public TrieDataStructure()
    {
        root = new TrieNode(' ');
    }

    /* This function is used to insert a word in trie*/
    public void insert(String word, String mean) {
        if (search(word) == true)
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

    /* This function is used to search a word in trie*/
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

    /* This function is used to remove function from trie*/
    public void remove(String word) {
        if (search(word) == false) {
            System.out.println(word +" does not present in trie");
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

    public TrieNode getRoot() {
        return root;
    }

    /* This function is used to change the meaning of the word if existed*/
    public void change(String word, String mean) {
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

    public void Lookup(TrieNode root, String s) {
        TrieNode current = root;
        for (char ch : s.toCharArray() ) {
            if (current.getChild(ch) == null)
                return;
            else
                current = current.getChild(ch);
        }

        TrieNode current = root;
        if(root.childList == null || root.childList.size() == 0)
            return;
        Iterator<TrieNode> iter=current.childList.iterator();
        while(iter.hasNext()) {
            TrieNode node = iter.next();
            s += node.data;
            ShowAllWords(node, s);
            if(node.isEnd == true) {
                //Test print
                System.out.print(s);
                for(int i = s.length(); i < 25; i++) System.out.print("-");
                System.out.println(node.mean);
                //Test print
                s = s.substring(0, s.length() - 1);
            }
            else {
                s = s.substring(0, s.length() - 1);
            }
        }
    }

    public void ShowAllWords(TrieNode root, String s) {
        TrieNode current = root;
        if(root.childList==null || root.childList.size()==0)
            return;
        Iterator<TrieNode> iter=current.childList.iterator();
        while(iter.hasNext()) {
            TrieNode node = iter.next();
            s+=node.data;
            ShowAllWords(node,s);
            if(node.isEnd == true) {
                //Test print
                System.out.print(s);
                for(int i = s.length(); i < 25; i++) System.out.print("-");
                System.out.println(node.mean);
                //Test print
                s = s.substring(0, s.length() - 1);
            }
            else {
                s = s.substring(0, s.length() - 1);
            }
        }
    }

}