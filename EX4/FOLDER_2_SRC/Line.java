import java.util.ArrayList;
import java.util.HashSet;
public class Line {
    HashSet<Integer> in;
    HashSet<Integer> out;
    ArrayList<Integer> succ;
    ArrayList<Integer> statment;


    public Line() {
        in= new HashSet<Integer>();
        out= new HashSet<Integer>();
        statment= new ArrayList<Integer>();
    }

      @Override
    public String toString() {
        return statment.toString();
    }

}
