import java.util.LinkedList;

public class Type{
    String type = "";
    LinkedList<Meaning> childList;

    Type(String s) {
        type = s;
        childList = new LinkedList<>();
    }

    Type() {
        childList = new LinkedList<>();
    }

    public void addphonetic(String s) {
        type += s;
    }

    public void addexample(String s){
        if(childList.isEmpty()) {
            //System.out.println("Error at : " + s);
            addmean(s);
        }
        childList.getLast().add(s);
    }

    public void addmean(String s) {
        childList.add(new Meaning(s));
    }

    public String get() {
        String s = type;
        for(Meaning t : childList) {
            s = "           " + t.get() + "\n";
        }
        return s;
    }
}
