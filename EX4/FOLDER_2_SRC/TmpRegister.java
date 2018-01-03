import java.util.ArrayList;
import java.util.HashSet;
public class TmpRegister {
    int id;
    int des;//Desegnation from [t0,t7]
    int start;
    int end;
    HashSet<Integer> inter_list;

    public TmpRegister(int id, int start, int end) {
        this.id = id;
        this.start = start;
        this.end = end;
        inter_list= new HashSet<Integer>();
    }
    public void setDesegnation(int des) {
        this.des = des;
    }

    public void addInterferance(TmpRegister t){
        inter_list.add(t.id);
    }

    public boolean isInterfering(TmpRegister t){
    int x1=t.start;
    int x2=t.end;
    int y1=this.start;
    int y2=this.end;
    return (x1 >= y1 && x1 <= y2) ||
                    (x2 >= y1 && x2 <= y2) ||
                    (y1 >= x1 && y1 <= x2) ||
                    (y2 >= x1 && y2 <= x2);


//        if(t.start<this.end||this.start<t.end){
////            System.out.println("t.start:"+t.start+" , "+"this.end:"+this.end+" , "+"this.start:"+this.start+" , "+"t.end:"+t.end);
//            return true;
//        }
//        return false;
    }

    @Override
    public String toString() {
        return "[id:"+id+", inter_list:"+inter_list.size()+"]";
    }
}
