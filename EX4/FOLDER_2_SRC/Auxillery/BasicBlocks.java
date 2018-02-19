package Auxillery;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// TODO: INCOMPLETE! IF NEEDED - COMPLETE!

public class BasicBlocks {
    public ArrayList<Integer> leaders;

    public BasicBlocks() {
        leaders = new ArrayList<Integer>();
    }

    public int identifyLeaders(String inputPath) throws IOException {
        boolean isLeader = false;
        Integer lineCounter = 1;
        String currLine;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputPath));
        while ((currLine = bufferedReader.readLine()) != null) {
            if (currLine.startsWith("#")) {
                lineCounter++;
                continue;
            }
            for (String s : currLine.split("_")) {
                // if current line is a label, mark the next line as a leader
                if (s.equals("Label") || s.equals("main:") /*line after the main label is a leader*/) {
                    leaders.add(lineCounter + 1); // the line that appears right after a label is a leader
                }
            }
            for (String s : currLine.split(" ")) {
                // if current line is a jump/branch, mark the next line as a leader
                if (s.equals("b") || s.equals("beq") || s.equals("blt") || s.equals("ble") || s.equals("bgt")
                        || s.equals("bge") || s.equals("bne") || s.equals("j") || s.equals("jr") || s.equals("jal")) {
                    leaders.add(lineCounter + 1);
                }
            }
            lineCounter++; // advance line counter
        }
        return lineCounter;
    }
}
