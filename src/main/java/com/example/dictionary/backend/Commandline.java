package com.example.dictionary.backend;

import static com.example.dictionary.backend.TextSpeech.ReadTheWord;

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

    public String Word(String word) {
        if(Dictionary.search(word))
            return Dictionary.get(word);
        return "";
    }
    public void readFromFile() {
        Dictionary.readFromFile();
    }

    public void remove(String word) {
        Dictionary.remove(word);
    }

    public void remove1(String word, Integer id) { Dictionary.remove1(word, id); }

    public void remove12(String word, Integer id) { Dictionary.remove12(word, id); }

    public void remove2(String word, Integer id) { Dictionary.remove2(word, id); }

    public void remove21(String word, Integer id, Integer id2) { Dictionary.remove21(word, id, id2); }

    public void remove22(String word, Integer id, Integer id2) { Dictionary.remove22(word, id, id2); }

    public void remove3(String word, Integer id) { Dictionary.remove3(word, id); }

    public void remove31(String word, Integer id, Integer id2) { Dictionary.remove31(word, id, id2); }

    public void remove32(String word, Integer id, Integer id2) { Dictionary.remove32(word, id, id2); }

    public void add1(String word, String s) { Dictionary.add1(word, s);}

    public void add12(String word, String s) { Dictionary.add12(word, s);}

    public void add2(String word, String s) { Dictionary.add2(word, s);}

    public void add21(String word, Integer id, String s) { Dictionary.add21(word, id, s);}

    public void add22(String word, Integer id, String s) { Dictionary.add22(word, id, s);}

    public void add3(String word, String s) { Dictionary.add3(word, s);}

    public void add31(String word, Integer id, String s) { Dictionary.add31(word, id, s);}

    public void add32(String word, Integer id, String s) { Dictionary.add32(word, id, s);}

    public void create(String word) {
        if(!checkWord(word)) return;
        word = processWord(word);
        create(word);
    }
    public void lookup(String s, int amount) {
        Dictionary.lookup(s, amount);
    }

    public static void main(String[] args) {
        Commandline command = new Commandline();
        command.readFromFile();
        //command.lookup("", 10);
        System.out.println(command.Word("fun"));
        command.add3("fun", "-------");
        System.out.println(command.Word("fun"));
        ReadTheWord("nothing");
        //command.Lookup("h", 2);
    }
}