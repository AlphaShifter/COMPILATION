import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.regex.*;

import TEMP.TEMP_FACTORY;

public class GraphColor {


    public static void garphColor(String outputPath) throws IOException, InterruptedException {

        ArrayList<TmpRegister> tmp_list = new ArrayList<TmpRegister>();
        ArrayList<String> psuFile = new ArrayList<String>();
        Pattern p = Pattern.compile("(,|\\s|\\(|\\()");
        String inputFile = "FOLDER_5_OUTPUT/MIPS.txt";
        // int tmp_num=TEMP_FACTORY.getCount();//TODO - get the number of temps from the temp factory
        int tmp_num = 10000;
        for (int tmp_index = 0; tmp_index <= tmp_num; tmp_index++) {

            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            int start = 0;
            int end = 0;
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println(Arrays.toString(p.split(line)));
                for (String splited : p.split(line)) {

                    String s = splited;
                    if (splited.contains(")")) {
                        s = splited.substring(
                                0, splited.indexOf(")")
                        );
                    }
                    if (s.equals("Temp_" + tmp_index)) {
                        if (start == 0)
                            start = i++;
                        end = i;
                        break;
                    }
                }
                i++;
            }
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(Arrays.toString(p.split(line)));
                for (String splited : p.split(line)) {
                    String s = splited;
                    if (splited.contains(")")) {
                        s = splited.substring(
                                0, splited.indexOf(")")
                        );
                    }
                    if (s.equals("Temp_" + tmp_index)) {
                        end = i;
                    }
                }
                i++;
            }
            // close the BufferedReader when we're done
            bufferedReader.close();
            if (start == 0 && end == 0)
                break;
            tmp_list.add(new TmpRegister(tmp_index, start, end));
        }

        //end parameter is too large by 1, fix:

        for (TmpRegister t : tmp_list) {
            t.end = t.end - 1;
        }
        //clear non-used temps (range 0:0)


        for (TmpRegister t : tmp_list) {
//            System.out.println("v:" + t.id + " [" + t.start + ": " + t.end + "]");
        }


        int total = 0;
        int inter_count = 0;
        Graph g1 = new Graph(tmp_list.size());
        for (TmpRegister t : tmp_list) {
            for (TmpRegister k : tmp_list) {
                if (k.isInterfering(t) && k.id != t.id) {

                    k.addInterferance(t);
                    g1.addEdge(t.id, k.id);
                    inter_count++;
                }
//            System.out.println(t);
                total++;
            }
        }
        tmp_list.sort(Comparator.comparingInt(tmpRegister -> tmpRegister.inter_list.size()));
        // for (TmpRegister t:tmp_list) {
        //     System.out.println(t);
        //   // for (int i : t.inter_list ) {
        //   // }
        //   //   System.out.println(t);
        // }
        PrintWriter fileWriter;
        HashMap<Integer, Integer> assignMap = g1.greedyColoring();
        // System.out.println(assignMap);


        BufferedReader bufferedReader = new BufferedReader(new FileReader("FOLDER_5_OUTPUT/MIPS.txt"));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            for (int i = tmp_num; i >= 0; i--) {
                Pattern pat = Pattern.compile("Temp_" + i);
                // String oldstring="Temp_"+i;
                Matcher m = pat.matcher(line);
                line = m.replaceAll("\\$t" + assignMap.get(i));
                // String newstring="$t"+assignMap.get(i);
                // line=line.replaceAll(oldstring,newstring);
            }
            psuFile.add(line);
        }
        // System.out.println(psuFile);
        for (String str1 : psuFile) {
//            System.out.println(str1);
        }

        Files.write(Paths.get(outputPath),psuFile);


/*
    StringBuilder sb = new StringBuilder();
    for (int i=tmp_num;i>=0 ;i-- ) {
        // String[] command={"sed", "-i", "'s/Temp_"+i+"/$$t"+assignMap.get(i)+"/g'", "../FOLDER_5_OUTPUT/SPIM.txt"};
        Process process= Runtime.getRuntime().exec(command);
        process.waitFor();
        // sb.append(command+" & ");
      // System.out.println(Arrays.toString(command));
    }


*/
        // sb.append(" echo hi");
        // System.out.println(sb);
        // ProcessBuilder pb = new ProcessBuilder(sb.toString());
        // Process process = pb.start();
        // process.waitFor();
    }


}
