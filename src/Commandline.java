import jdk.nashorn.internal.lookup.Lookup;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.Scanner;

/** Các chức năng đã có của code.
 * Thêm bớt xóa sửa cơ bản.
 * Kiểm tra xâu với ký tự không hợp lệ.
 * Xử lý xâu với những dấu cách bị thừa.
 * Đưa ra toàn bộ từ điển.
 * Đưa ra một số từ mà có xâu đang nhập là tiền tố.
 * Các từ viết hoa vẫn có thể lặp.
 * Đọc được dữ liệu từ txt.
 */
public class Commandline {
    public TrieDataStructure Dictionary = new TrieDataStructure();
    public void ReadFromFile() {
        try {
            File myObj = new File("Dictionary.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                /** Thay đổi format từ sẽ ở đây, tạm thời là ':' Ví dụ: apple:quả táo.*/
                String[] word = data.split(":");
                Dictionary.insert(word[0], word[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void ShowAllWords() {
        Dictionary.ShowAllWords(Dictionary.getRoot(), "");
    }


    public static void main(String[] args) {
        Commandline command = new Commandline();
        command.ReadFromFile();
        command.Dictionary.insert("adf", "123");
        command.ShowAllWords();
    }
}