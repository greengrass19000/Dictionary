import java.util.LinkedList;

public class Meaning {
    String mean = "";
    LinkedList<String> childList;

    Meaning(String s) {
        mean = s;
        childList = new LinkedList<>();
    }

    public void add(String mean) {
        childList.add(mean);
    }

    public String get() {
        String s = mean;
        for(String t : childList) {
            s +=   "            " + t + "\n";
        }
        return s;
    }
}
