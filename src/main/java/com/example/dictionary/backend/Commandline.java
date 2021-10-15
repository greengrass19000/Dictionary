package com.example.dictionary.backend;
/** Các chức năng đã có của code:
 * Thêm bớt xóa sửa cơ bản.
 * Kiểm tra xâu với ký tự không hợp lệ.
 * Xử lý xâu với những dấu cách bị thừa.
 * Đưa ra toàn bộ từ điển.
 * Đưa ra một số từ mà có xâu đang nhập là tiền tố.
 * Tránh lặp từ.
 * Đọc được dữ liệu từ file text.
 */
public class Commandline {
    public TrieDataStructure Dictionary = new TrieDataStructure();

    /** This function checks if the string doesn't have strange character, only space approved.*/
    public boolean checkWord(String word) {
        for(char l : word.toCharArray()) {
            if((l > 'z' || l < 'a') && (l > 'Z' || l < 'A') && l != ' ') {
                System.out.println("Illegal character!!!");
                return false;
            }
        }
        return true;
    }

    /** remove unnecessary space characters.*/
    public String processWord(String word) {
        word = word.trim();
        StringBuilder t = new StringBuilder();
        boolean spaceexisted = false;
        for(char l : word.toCharArray()) {
            if (l == ' ') {
                spaceexisted = true;
            } else {
                if(spaceexisted) {
                    t.append(' ');
                    spaceexisted = false;
                }
                t.append(l);
            }
        }
        return t.toString();
    }

    public void insert(String word, String mean) {
        if(!checkWord(word)) return;
        word = processWord(word);
//        Dictionary.insert(word, mean);
    }


    public void readFromFile() {
        Dictionary.readFromFile();
    }

    public void lookup(String s, int amount) {
        Dictionary.lookup(s, amount);
    }

    public static void main(String[] args) {
        Commandline command = new Commandline();
        command.readFromFile();
        command.lookup("a", 10);
        //command.Lookup("h", 2);
        //command.ShowAllWords();
    }
}