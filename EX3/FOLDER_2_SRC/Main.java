   
import java.io.*;
import java.io.PrintWriter;
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
		String inputFilename = argv[0];
		String outputFilename = argv[1];

/*		String inputFilename = "C:\\Users\\Gilad\\Documents\\Compilation\\EX2\\FOLDER_4_INPUT\\TEST_06_Print_Primes_Error.txt";
		String outputFilename = "C:\\Users\\Gilad\\Documents\\Compilation\\EX2\\FOLDER_4_INPUT\\out.txt";
		String inputFilename = "/Users/giladi/COMPILATION_TAU/EX2/FOLDER_4_INPUT/TEST_03_Merge_Lists.txt";
		String outputFilename = "/Users/giladi/COMPILATION_TAU/EX2/FOLDER_5_OUTPUT/out.txt";   */

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
			file_writer.write("OK\n");
			if(p.wasSuccessful){
				
			}
			else{
				file_writer.write("ERROR("+p.lineNumber+")\n");
			}
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();
			
			/*************************/
			/* [7] Close output file */
			/*************************/
			file_writer.close();
			
			/*************************************/
			/* [8] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();


    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


