import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
public class Line {
    HashSet<Integer> in;
    HashSet<Integer> out;
     HashSet<Integer> intag;
    HashSet<Integer> outtag;
    TreeSet<Integer> succ;
    ArrayList<Integer> statment;
    ArrayList<Integer> def;
    int use;
    int linecount;

    public Line(int c,TreeSet<Integer> succsessor) {
        in= new HashSet<Integer>();
        out= new HashSet<Integer>();
        intag= new HashSet<Integer>();
        outtag= new HashSet<Integer>();
        statment= new ArrayList<Integer>();
        def= new ArrayList<Integer>();
        succ= succsessor;
        linecount=c;
    }

      @Override
    public String toString() {
        return linecount+","+statment.toString()+","+succ.toString()+out.toString()+"\n";
    }
    public void update() {
        if(statment.size()>=1) use=statment.get(0);
        if(statment.size()>=2) def.add(statment.get(1));
        if(statment.size()>=3) def.add(statment.get(2));

        
    }

}
