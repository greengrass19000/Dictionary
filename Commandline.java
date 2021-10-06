public class Commandline {
    //Check if the word has illegal character
    boolean Checkword(String word) {
        for(char l : word.toCharArray()) {
            if((l > 'z' || l < 'a') && (l > 'Z' || l < 'A')) {
                System.out.println("Illegal character!!!");
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args)
    {
        TrieDataStructure Dictionary = new TrieDataStructure();
        Dictionary.insert("dear", "1");
        Dictionary.insert("deal", "2");
        Dictionary.insert("do", "3");
        Dictionary.insert("he", "4");
        Dictionary.insert("hen", "5");
        Dictionary.insert("heat", "6");
        
        Dictionary.ShowAllWords(Dictionary.getRoot(), "");
    }
}
