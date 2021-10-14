import java.util.Iterator;

public class TrieDataStucture2 extends TrieDataStructure{
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
                for(int i = s.length(); i < 30; i++) System.out.print("-");
                System.out.println(node.mean);
                //Test print
            }
            ShowAllWords(node, s);
            s = s.substring(0, s.length() - 1);
        }
    }

    /** This function is used to change the meaning of the word if existed.*/
    public void change(String word, String mean) {
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

    /** This function is used to insert a word in trie.*/
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

    /** This function is used to remove function from trie.*/
    public void remove(String word) {
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
}
