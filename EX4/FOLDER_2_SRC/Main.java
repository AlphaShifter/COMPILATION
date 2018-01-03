
import java.io.*;
import java.io.PrintWriter;

import Auxillery.Util;
import java_cup.runtime.Symbol;
import AST.*;
import IR.*;
import MIPS.*;

public class Main {
    static public void main(String argv[]) {
        Lexer l;
        Parser p;
        Symbol s;
        AST_PROGRAM AST;
        FileReader file_reader;
        PrintWriter file_writer;
        String inputFilename;
        String outputFilename;
        if (argv.length == 0) {
            inputFilename = "FOLDER_4_INPUT/TEST_00_Print_Primes_Simple.txt";
            outputFilename = "FOLDER_5_OUTPUT/mips_prime_output.txt";
        } else {
            inputFilename = argv[0];
            outputFilename = argv[1];
        }

        try {
            /********************************/
            /* [1] Initialize a file reader */
            /********************************/
            /********************************/
			/* [1] Initialize a file reader */
            /********************************/
            file_reader = new FileReader(inputFilename);

            /********************************/
			/* [2] Initialize a file writer */
            /********************************/
            file_writer = new PrintWriter(outputFilename);
            Util.setWriter(file_writer);

            /******************************/
			/* [3] Initialize a new lexer */
            /******************************/
            l = new Lexer(file_reader);

            /*******************************/
			/* [4] Initialize a new parser */
            /*******************************/
            p = new Parser(l,file_writer);

            /***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
            /***********************************/
            AST = (AST_PROGRAM) p.parse().value;

            Util.treeReduction(AST);
            Util.classSplit(AST);

            /*************************/
			/* [6] Print the AST ... */
            /*************************/
           // AST.PrintMe();

            /**************************/
			/* [7] Semant the AST ... */
            /**************************/
            AST.SemantMe();

            /**********************/
			/* [8] IR the AST ... */
            /**********************/
            AST.IRme();

            /***********************/
			/* [9] MIPS the IR ... */
            /***********************/
            IR.getInstance().MIPSme();

            /**************************************/
			/* [10] Finalize AST GRAPHIZ DOT file */
            /**************************************/
            AST_GRAPHVIZ.getInstance().finalizeFile();

            /***************************/
			/* [11] Finalize MIPS file */
            /***************************/
            sir_MIPS_a_lot.getInstance().finalizeFile();

            /**************************/
			/* [12] Close output file */
            /**************************/
            file_writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


