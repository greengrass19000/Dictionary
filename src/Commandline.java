import jdk.nashorn.internal.lookup.Lookup;

import java.util.Dictionary;


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
    public boolean Checkword(String word) {
        for(char l : word.toCharArray()) {
            if((l > 'z' || l < 'a') && (l > 'Z' || l < 'A') && l != ' ') {
                System.out.println("Illegal character!!!");
                return false;
            }
        }
        return true;
    }

    /** remove unnecessary space characters.*/
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

    public void insert(String word, String mean) {
        if(!Checkword(word)) return;
        word = processword(word);
        //Dictionary.insert(word, mean);
    }


    public void ReadFromFile() {
        Dictionary.ReadFromFile();
    }

    public void Lookup(String s, int amount) {
        Dictionary.Lookup(s, amount);
    }
    public static void main(String[] args) {
        Commandline command = new Commandline();
        command.ReadFromFile();
        command.Lookup("a", 10);
        //command.Lookup("h", 2);
        //command.ShowAllWords();
    }
}