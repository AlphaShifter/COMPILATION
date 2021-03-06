   
import java.io.*;
import java.io.PrintWriter;

import Auxillery.Util;
import java_cup.runtime.Symbol;
import AST.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename;
		String outputFilename;
		if(argv.length==0){
			inputFilename = "FOLDER_4_INPUT/TEST_15_Integral_Allocation_Size_Integral_Subscript_Exp.txt";
			outputFilename = "FOLDER_5_OUTPUT/SemanticStatus.txt";
		}
		else{

			inputFilename = argv[0];
			outputFilename = argv[1];
		}
//		String inputFilename = "C:\\Users\\Ilsar\\Documents\\Compilation\\EX2\\FOLDER_4_INPUT\\TEST_03_Merge_Lists.txt";
//		String outputFilename = "C:\\Users\\Ilsar\\Documents\\Compilation\\EX2\\FOLDER_4_INPUT\\out.txt";
//		String inputFilename = "/Users/giladi/COMPILATION_TAU/EX2/FOLDER_4_INPUT/TEST_03_Merge_Lists.txt";
//		String outputFilename = "/Users/giladi/COMPILATION_TAU/EX2/FOLDER_5_OUTPUT/out.txt";

		try
		{
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


			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/*************************/
			/* [7] Close output file */
			/*************************/

			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();

			Util.treeReduction(AST);
			Util.classSplit(AST);
            AST.SemantMe();
            file_writer.write("OK\n");
            file_writer.close();

						//boolean retValue = Util.logClasses(AST); // try to log the classes of the program
//			if(retValue == true){
//				System.out.println("OK with classes\n");
//			} else{
//				System.out.println("ERROR with classes\n");
//			}
//
//			System.out.println(AST.line);
		}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


