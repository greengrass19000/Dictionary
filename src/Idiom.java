import java.util.LinkedList;

public class Idiom {
    String idiom = "";
    LinkedList<String> childList;

    Idiom(String s) {
        childList = new LinkedList<>();
        idiom = s;
    }

    public void add(String s) {
        childList.add(s);
    }

    public String get() {
        String s = idiom;
        for(String t : childList) {
            s += "            " + t  + "\n";
        }
        return s;
    }
}
