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
        // int maxtmp=TEMP_FACTORY.getCount();//TODO - get the number of temps from the temp factory
        // int maxtmp=10000;


        Pattern tempMatch = Pattern.compile("(?<=Temp_)\\d{1,7}");
        Pattern branchMatch = Pattern.compile("(beq|bge|bgt|ble|blt|bne|beqz)");
        Pattern jrMatch = Pattern.compile("(?<=jr ).*");
        Pattern jalMatch = Pattern.compile("(?<=jal ).*");
        Pattern jumpMatch = Pattern.compile("(jalr |jal |j |jr ).*");
        Pattern labelMatch = Pattern.compile(".*(?=:$)");
        ArrayList<Integer> allMatches;
        TreeSet<Integer> succ;
        TreeSet<Integer> leaderList = new TreeSet<Integer>();
        TreeMap<String,Integer> labelMap = new TreeMap<String,Integer>();
        TreeMap<String,Integer> labelToNearestjr = new TreeMap<String,Integer>();

        TreeSet<Integer> jrSet = new TreeSet<Integer>();

        // TreeMap<Integer,Line> lines = new TreeMap<Integer,Line>(Collections.reverseOrder());
        TreeMap<Integer,Line> lines = new TreeMap<Integer,Line>();
        Line curr;
        Matcher m;
        String line;
        // String inputFile = "../FOLDER_5_OUTPUT/MIPS.txt";
        int maxtmp = 0;

        int linecount=1;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            m = jrMatch.matcher(line);
            if(m.find()){
                jrSet.add(linecount);
            }
            linecount++;
        } 

        linecount=1;
        bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            succ = new TreeSet<Integer>();
            if(!jumpMatch.matcher(line).find()){
                succ.add(linecount+1);
            }
            m = labelMatch.matcher(line);
            if(m.find()){
                String currlabel = m.group();
                String[] tmp = line.split("(,| |\t)");
                labelMap.put(currlabel,linecount);
                labelToNearestjr.put(currlabel,jrSet.ceiling(linecount));
            }
            m = tempMatch.matcher(line);
            curr = new Line(linecount,succ,line);
            while (m.find()){ 
                int tmpid = Integer.parseInt(m.group()); 
                maxtmp = tmpid>maxtmp?tmpid:maxtmp; 
                 curr.statment.add(tmpid); 
            } 
            lines.put(linecount,curr);
            linecount++;
        }


        linecount=1;
        bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            m = branchMatch.matcher(line);
            if(m.find()){
            String[] tmp = line.split("(,| |\t)");
            if(lines.get(linecount)!=null&&tmp!=null&&labelMap.get(tmp[tmp.length-1])!=null){
                
            lines.get(linecount).succ.add(labelMap.get(tmp[tmp.length-1]));
            }
            m=jalMatch.matcher(line);
            if(m.find()){
                String r = m.group();
                // lines.get(labelToNearestjr.get(r)).succ.add(linecount+1);
                }
            }
            linecount++;
        }
        for(Line l : lines.values()){
            l.update();
        } 
        while(true){
            boolean sent=true;
            for(Line l : lines.values()){
                l.intag=new HashSet<Integer>(l.in);
                l.outtag=new HashSet<Integer>(l.out);
                HashSet<Integer> tmp=(new HashSet<Integer>(l.out));
                tmp.addAll(l.use);
                if(l.def>0){
                    tmp.remove(l.def);
                    }
                l.in=tmp;
                l.out = new HashSet<Integer>();
                for(Integer s: l.succ){
                    if(lines.get(s)!=null)l.out.addAll(lines.get(s).in);
                    }
            }
            for(Line l : lines.values()){
                sent=sent&&l.in.equals(l.intag)&&l.out.equals(l.outtag);
            }
            if(sent)break;


        }
        // System.out.println(lines);
        Graph g1 = new Graph(maxtmp+2);
        for(Line l : lines.values()){
            for(int i : l.in){
                for(int j : l.in){
                    if(i!=j){
                        g1.addEdge(i,j);
                    }
                }
            }
        }
        // int total = 0;
        // for (TmpRegister t : tmp_list) {
        //     for (TmpRegister k : tmp_list) {
        //         if (k.isInterfering(t) && k.id != t.id) {
        //             k.addInterferance(t);
        //             g1.addEdge(t.id, k.id);
        //         }
        //         total++;
        //     }
        // }

        PrintWriter fileWriter;
        HashMap<Integer, Integer> assignMap = g1.greedyColoring();
        // System.out.println(assignMap);


        bufferedReader = new BufferedReader(new FileReader("FOLDER_5_OUTPUT/MIPS.txt"));
        // String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            for (int i = maxtmp; i >= 0; i--) {
                Pattern pat = Pattern.compile("Temp_" + i);
                // String oldstring="Temp_"+i;
                 m = pat.matcher(line);
                // line = m.replaceAll("\\$t" + assignMap.get(i));
                // String newstring="$t"+assignMap.get(i);
                // line=line.replaceAll(oldstring,newstring);
            }
            psuFile.add(line);
        }
        // System.out.println(psuFile);
        for (String str1 : psuFile) {
            //System.out.println(str1);
        }

        Files.write(Paths.get(outputPath),psuFile);
    }


}

//     public static void oldGarphColor(String outputPath) throws IOException, InterruptedException {

//         ArrayList<TmpRegister> tmp_list = new ArrayList<TmpRegister>();
//         ArrayList<String> psuFile = new ArrayList<String>();
//         Pattern p = Pattern.compile("(,|\\s|\\(|\\()");
//         String inputFile = "FOLDER_5_OUTPUT/MIPS.txt";
//         // int maxtmp=TEMP_FACTORY.getCount();//TODO - get the number of temps from the temp factory
//         int maxtmp = 10000;
//         for (int tmp_index = 0; tmp_index <= maxtmp; tmp_index++) {

//             String line = null;
//             BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
//             int start = 0;
//             int end = 0;
//             int i = 1;
//             while ((line = bufferedReader.readLine()) != null) {
//                 // System.out.println(Arrays.toString(p.split(line)));
//                 for (String splited : p.split(line)) {

//                     String s = splited;
//                     if (splited.contains(")")) {
//                         s = splited.substring(
//                                 0, splited.indexOf(")")
//                         );
//                     }
//                     if (s.equals("Temp_" + tmp_index)) {
//                         if (start == 0)
//                             start = i++;
//                         end = i;
//                         break;
//                     }
//                 }
//                 i++;
//             }
//             while ((line = bufferedReader.readLine()) != null) {
//                 //System.out.println(Arrays.toString(p.split(line)));
//                 for (String splited : p.split(line)) {
//                     String s = splited;
//                     if (splited.contains(")")) {
//                         s = splited.substring(
//                                 0, splited.indexOf(")")
//                         );
//                     }
//                     if (s.equals("Temp_" + tmp_index)) {
//                         end = i;
//                     }
//                 }
//                 i++;
//             }
//             // close the BufferedReader when we're done
//             bufferedReader.close();
//             if (start == 0 && end == 0)
//                 break;
//             tmp_list.add(new TmpRegister(tmp_index, start, end));
//         }

//         //end parameter is too large by 1, fix:

//         for (TmpRegister t : tmp_list) {
//             t.end = t.end - 1;
//         }
//         //clear non-used temps (range 0:0)


//         // for (TmpRegister t : tmp_list) {
//         //     //System.out.println("v:" + t.id + " [" + t.start + ": " + t.end + "]");
//         // }

//         int total = 0;
//         int inter_count = 0;
//         Graph g1 = new Graph(tmp_list.size());
//         for (TmpRegister t : tmp_list) {
//             for (TmpRegister k : tmp_list) {
//                 if (k.isInterfering(t) && k.id != t.id) {

//                     k.addInterferance(t);
//                     g1.addEdge(t.id, k.id);
//                     inter_count++;
//                 }
// //            System.out.println(t);
//                 total++;
//             }
//         }
//         tmp_list.sort(Comparator.comparingInt(tmpRegister -> tmpRegister.inter_list.size()));
//         // for (TmpRegister t:tmp_list) {
//         //     System.out.println(t);
//         //   // for (int i : t.inter_list ) {
//         //   // }
//         //   //   System.out.println(t);
//         // }
//         PrintWriter fileWriter;
//         HashMap<Integer, Integer> assignMap = g1.greedyColoring();
//         // System.out.println(assignMap);


//         BufferedReader bufferedReader = new BufferedReader(new FileReader("FOLDER_5_OUTPUT/MIPS.txt"));
//         String line = null;
//         while ((line = bufferedReader.readLine()) != null) {
//             for (int i = maxtmp; i >= 0; i--) {
//                 Pattern pat = Pattern.compile("Temp_" + i);
//                 // String oldstring="Temp_"+i;
//                 Matcher m = pat.matcher(line);
//                 line = m.replaceAll("\\$t" + assignMap.get(i));
//                 // String newstring="$t"+assignMap.get(i);
//                 // line=line.replaceAll(oldstring,newstring);
//             }
//             psuFile.add(line);
//         }
//         // System.out.println(psuFile);
//         for (String str1 : psuFile) {
//             //System.out.println(str1);
//         }

//         Files.write(Paths.get(outputPath),psuFile);
//     }


// }


/*
    StringBuilder sb = new StringBuilder();
    for (int i=maxtmp;i>=0 ;i-- ) {
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
