import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.*;

public class Line {
    HashSet<Integer> in;
    HashSet<Integer> out;
     HashSet<Integer> intag;
    HashSet<Integer> outtag;
    TreeSet<Integer> succ;
    ArrayList<Integer> statment;


    ArrayList<Integer> use;
    int def=-1;



    int linecount;
    String line;
    public Line(int c,TreeSet<Integer> succsessor,String l) {
        in= new HashSet<Integer>();
        out= new HashSet<Integer>();
        intag= new HashSet<Integer>();
        outtag= new HashSet<Integer>();
        statment= new ArrayList<Integer>();
        use= new ArrayList<Integer>();
        succ= succsessor;
        linecount=c;
        line = l;
    }

      @Override
    public String toString() {
        return linecount+","+statment.toString()+","+succ.toString()+in.toString()+"\n";
    }
    public void update() {
        if(Pattern.compile("(addi|la|mflo|lb|li|mfhi|move|lw|sub|add) ").matcher(line).find()){
            if(statment.size()>=1) def=statment.get(0);
            if(statment.size()>=2) use.add(statment.get(1));
            if(statment.size()>=3) use.add(statment.get(2));
        }
        else if(Pattern.compile("(beqz|jalr) ").matcher(line).find()){
            if(statment.size()>=1) use.add(statment.get(0));
            if(statment.size()>=2) def=statment.get(1);
            // if(statment.size()>=3) use.add(statment.get(2));
        }
        else {
            if(statment.size()>=1) use.add(statment.get(0));
            if(statment.size()>=2) use.add(statment.get(1));
        }

        // use     (beqz)

        // use def (jalr)


        // use use (beq|bge|bgt|ble|blt|bne|div|mult|sb|sw)


        
    }

}
