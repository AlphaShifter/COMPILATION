import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.regex.*;


public class GraphColor {



public static void main(String args[]) throws IOException {
  
    ArrayList<TmpRegister> tmp_list= new ArrayList<TmpRegister>();
    Pattern p = Pattern.compile("(,|\\s|\\(|\\()");
    int tmp_num=5000;
      for (int tmp_index=0;tmp_index<=1;tmp_index++){

          String line = null;
          BufferedReader bufferedReader = new BufferedReader(new FileReader("../FOLDER_5_OUTPUT/MIPS.txt"));
          int start=0;
          int end=0;
          int i=1;
          while ((line = bufferedReader.readLine()) != null){
            System.out.println(Arrays.toString(p.split(line)));
            for (String splited : p.split(line)) {
              // System.out.println(splited);
              if(splited.equals("Temp_"+tmp_index)){
                  start=i++;
                  end=i;
                  break;
              }
            }
              i++;
          }
          while ((line = bufferedReader.readLine()) != null){
            System.out.println(Arrays.toString(p.split(line)));
              for (String splited : p.split(line)) {
                 System.out.println(splited);
                if(splited.equals("Temp_"+tmp_index)){
                  end=i;
              }
            }
              i++;
          }
          // close the BufferedReader when we're done
          bufferedReader.close();
          if(start == 0 && end == 0)
              break;
          tmp_list.add(new TmpRegister(tmp_index,start,end));
      }
    //clear non-used temps (range 0:0)

    int total=0;
    int inter_count=0;
    Graph g1 = new Graph(tmp_list.size());
    for (TmpRegister t:tmp_list) {
        for (TmpRegister k:tmp_list) {
            if(k.isInterfering(t)&&k.id!=t.id){

                k.addInterferance(t);
                g1.addEdge(t.id, k.id);
                inter_count++;
            }
//            System.out.println(t);
            total++;
        }
    }
    tmp_list.sort(Comparator.comparingInt(tmpRegister -> tmpRegister.inter_list.size()));
    for (TmpRegister t:tmp_list) {
        System.out.println(t);
      // for (int i : t.inter_list ) {
      // }
      //   System.out.println(t);
    }

  g1.greedyColoring();
}


}
