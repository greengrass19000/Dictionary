import jdk.nashorn.internal.lookup.Lookup;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** Các chức năng đã có của code
 * Thêm bớt xóa sửa cơ bản
 * Kiểm tra xâu với ký tự không hợp lệ
 * Xử lý xâu với những dấu cách bị thừa
 * Đưa ra toàn bộ từ điển
 * Đưa ra một số từ mà có xâu đang nhập là tiền tố
 * Các từ viết hoa vẫn có thể lặp
 * TODO : chuyển một số hàm từ private về public
 * TODO : cho chương trình biết khi nào thì gửi request cho API (Có thể cho vào một phần riêng là dịch câu
 * TODO : Đọc dữ liệu từ nguồn như txt, ...
 */
public class Commandline {
    public static void Readfile() {
        try {
            File myObj = new File("Dictionary.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        TrieDataStructure Dictionary = new TrieDataStructure();
        Dictionary.insert("dear u", "ôi bạn ơi");
        Dictionary.insert("Dear u", "ôi bạn ơi");
        Dictionary.insert("deal", "b");
        Dictionary.insert("do", "c");
        Dictionary.insert("hen", "d");
        Dictionary.insert("he", "e");
        Dictionary.insert("heat", "f");
        Dictionary.change("heat", "gg");
      //  Dictionary.ShowAllWords(Dictionary.getRoot(), "");
        Dictionary.Lookup("d", 2);
        Readfile();
    }
}
