import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;
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

public class MatchTest {
    public static void main(String[] args)throws IOException, InterruptedException {
        Liveness();
        // Leader();


    }

    // public static void replaceTemp(){

    //     Pattern p2 = Pattern.compile("(,|\\s|\\(|\\()");

    //     // Pattern p3 = Pattern.compile("Temp_" + tmp_id);
    //     String test = " lw Temp_30,Temp_30";
    //     // String str = m.replaceAll("\\$t" + assignment);
    //     // String str2 = m.replaceAll("{\\1}");
    //      // Matcher m = Pattern.compile("your regular expression here").matcher(yourStringHere);


    //     Pattern p = Pattern.compile("(?<=Temp_)\\d{1,7}");
    //     String test2 = "    li Temp_1,100   sw (Temp_1), -8($fp)  lw (Temp_3), -4($fp)  move Temp_2,Temp_3  sw Temp_2, -12($fp)";
    //     Matcher m = p.matcher(test2);
    //      ArrayList<String> allMatches = new ArrayList<String>();
    //      while (m.find()) allMatches.add(m.group());
    //     System.out.println(allMatches);
         
    //     // String[] command = {"sed", "-i", "'s/Temp_" + tmp_id + "\\$\\$t" + assignment + "/g'", filePath};
    //     // ProcessBuilder pb = new ProcessBuilder(command);
    //     // Process process = pb.start();
    //     // process.waitFor();


    // }

    public static void Liveness() throws IOException, InterruptedException{
        Pattern tempMatch = Pattern.compile("(?<=Temp_)\\d{1,7}");
        Pattern branchMatch = Pattern.compile("(beq|blt|ble|bgt|bge|bne|jal|j )");
        Pattern jrMatch = Pattern.compile("(?<=jr ).*");
        Pattern jalMatch = Pattern.compile("(?<=jal ).*");
        Pattern jumpMatch = Pattern.compile("(j |jr |jal).*");
        // Pattern Match = Pattern.compile("(?<=j ).*");
        Pattern labelMatch = Pattern.compile(".*(?=:$)");
        ArrayList<Integer> allMatches;
        TreeSet<Integer> succ;
        TreeSet<Integer> leaderList = new TreeSet<Integer>();
        TreeMap<String,Integer> labelMap = new TreeMap<String,Integer>();
        TreeMap<String,Integer> labelToNearestjr = new TreeMap<String,Integer>();
        // TreeMap<Integer,TreeSet<Integer>> edges = new TreeMap<Integer,TreeSet<Integer>>();

        TreeSet<Integer> jrSet = new TreeSet<Integer>();

        // leaderList.add(1);
        TreeMap<Integer,Line> lines = new TreeMap<Integer,Line>(Collections.reverseOrder());
        Line curr;
        Matcher m;
        String line;

        String inputFile = "../FOLDER_5_OUTPUT/MIPS.txt";
        // BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        // int totalLines = 0;
        // while (reader.readLine() != null) {
        //     totalLines++;
        // }
        // int[][] edges = new int[totalLines+1][totalLines+1];
        // reader.close();

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
                String[] tmp = line.split("(,| )");
                labelMap.put(currlabel,linecount);
                labelToNearestjr.put(currlabel,jrSet.ceiling(linecount));
            }
            m = tempMatch.matcher(line);
            curr = new Line(linecount,succ);
            while (m.find()) curr.statment.add(Integer.parseInt(m.group()));
            lines.put(linecount,curr);
            linecount++;
        }


        linecount=1;
        bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            m = branchMatch.matcher(line);
            if(m.find()){
            String[] tmp = line.split("(,| )");
            lines.get(linecount).succ.add(labelMap.get(tmp[tmp.length-1]));
            m=jalMatch.matcher(line);
            if(m.find()){
                String r = m.group();
                // System.out.println(line+" :="+r);
                // System.out.println(labelToNearestjr.get(r));
                lines.get(labelToNearestjr.get(r)).succ.add(linecount+1);
                // lines.get(labelToNearestjr.get(m.group())).succ.add(linecount+1);

                }
            }
            linecount++;
        }




        //reverse list
        // Collections.reverse(lines);
        // System.out.println(leaderList);
        // System.out.println(labelMap);
        // System.out.println(lines);
        // System.out.println(labelToNearestjr);
        // System.out.println(jrSet);
        // return;
        //TODO - calc succsessor and use
        for(Line l : lines.values()){
            // System.out.println(l);
            l.update();
        } 


        // System.exit(0);
        int counting=0;
        // Collections.reverseOrder(lines);

        // new TreeMap<Integer,Line>(Collections.reverseOrder()).
        // ArrayList<Line> linesList = new ArrayList<Line>(lines.values());
        // lines=(TreeMap<Integer,Line>)lines.descendingMap();
        while(true){
            boolean sent=true;
            for(Line l : lines.values()){
                l.intag=new HashSet<Integer>(l.in);
                l.outtag=new HashSet<Integer>(l.out);
                HashSet<Integer> tmp=(new HashSet<Integer>(l.out));
                tmp.removeAll(l.def);
                tmp.add(l.use);
                l.in=tmp;
                l.out = new HashSet<Integer>();
                for(Integer s: l.succ){
                    if(lines.get(s)!=null)l.out.addAll(lines.get(s).in);
                    }
            }
            for(Line l : lines.values()){
                sent=sent&&l.in.equals(l.intag)&&l.out.equals(l.outtag);
                // sent=sent||!l.out.equals(l.outtag);
            }
            if(sent)break;
            if ((counting++)%10==0) {
            System.out.println(counting);
            }

        }
            System.out.println(lines.values());
    }

    public static void Leader() throws IOException, InterruptedException{
        ArrayList<Integer> allMatches;
        ArrayList<Line> lines = new ArrayList<Line>();
        Pattern jalMatch = Pattern.compile("(?<=jal ).*");

        Line curr;
        String[] test = {" li Temp_1,100 ","  bge (Temp_1), -8($fp) "," j (Temp_3), -4($fp) "," jal Temp_2,Temp_3 "," sw Temp_2, -12($fp)","man:\n"};
        String line;
        String inputFile = "../FOLDER_5_OUTPUT/MIPS.txt";
        // for(String s : test){
        //     Matcher m = p2.matcher(s);
        //     System.out.println(s+":"+m.find());
            
        // }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            Pattern branchMatch = Pattern.compile("(beq|blt|ble|bgt|bge|bne|jr|j|jal)");
            Pattern labelMatch = Pattern.compile(":$");
            Matcher m = labelMatch.matcher(line);
            System.out.println(line+":"+((m.find())?m.group():""));

        }
    }
    
}
            // System.out.println(line+"----"+tmp[tmp.length-1]);
            // System.out.println(labelMap.get(tmp[tmp.length-1]));
            // System.out.println(tmp[tmp.length-1]);

            // labelMap.put(tmp[tmp.length-1],linecount);
            // leaderList.add(linecount+1);
            // System.out.println(curr+" "+line);
            // m = jalMatch.matcher(line);
            // System.out.println(line+":"+((m.find())?m.group():""));
            // m = jumpMatch.matcher(line);
            // System.out.println(line+":"+((m.find())?m.group():""));
            // System.out.println(line);