import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;
import java.util.*;
import java.util.LinkedList;


public class GraphColor {



public static void main(String args[]) throws IOException {
  
    ArrayList<TmpRegister> tmp_list= new ArrayList<TmpRegister>();

    int tmp_num=80;
      for (int tmp_index=0;tmp_index<=tmp_num;tmp_index++){

          String line = null;
          BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/ohad/workspace/Courses/2017A/Compilation/Compilation_Homework/EX4/FOLDER_5_OUTPUT/MIPS.txt"));
          int start=0;
          int end=0;
          int i=1;
          while ((line = bufferedReader.readLine()) != null){
              if(line.contains("Temp_"+tmp_index)){
                  start=i++;
                  end=i;
                  break;
              }
              i++;
          }
          while ((line = bufferedReader.readLine()) != null){
              if(line.contains("Temp_"+tmp_index)){
                  end=i;
              }
              i++;
          }
          // close the BufferedReader when we're done
          bufferedReader.close();
          System.out.println("Temp_"+tmp_index+" liveness range:["+start+","+end+")");
          tmp_list.add(new TmpRegister(tmp_index,start,end));
      }
    int total=0;
    int inter_count=0;
    Graph g1 = new Graph(tmp_num+1);
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
    // tmp_list.sort(Comparator.comparingInt(tmpRegister -> tmpRegister.inter_list.size()));
    // for (TmpRegister t:tmp_list) {
    //   for (int i : t.inter_list ) {
    //     System.out.println(i);
    //   }
    //     System.out.println(t);
    // }

  g1.greedyColoring();
}


}
