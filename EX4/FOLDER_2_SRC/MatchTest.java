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
        Pattern p = Pattern.compile("(?<=Temp_)\\d{1,7}");
        ArrayList<Integer> allMatches;
        ArrayList<Line> lines = new ArrayList<Line>();
        Line curr;
        String line;
         String inputFile = "../FOLDER_5_OUTPUT/MIPS.txt";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            Matcher m = p.matcher(line);
            curr = new Line();
            // allMatches = new ArrayList<String>();
            while (m.find()) curr.statment.add(Integer.parseInt(m.group()));
            lines.add(curr);
        }
        //reverse list
        // Collections.reverse(lines);
        System.out.println(lines);

        while(true){
            for(Line l : lines){
            l.intag=l.in.clone();
            l.outtag=l.out.clone();
            HashSet<Integer> tmp=l.out.clone().removeAll(l.def);
            tmp.addAll(l.use);
            l.in=tmp;
            l.out = new HashSet<Integer>();
            for(Line s: l.succ){
                l.out.addAll(s.in);
                }
            }
            for(Line l : lines){
                if(!l.in.equals(l.intag)) break;
                if(!l.out.equals(l.outtag)) break;
            }
        }
    }
}
