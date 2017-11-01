   
import java.io.*;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s;
		FileReader file_reader;
		PrintWriter file_writer;
		//String inputFilename = argv[0];
		//String outputFilename = argv[1];
		String inputFilename = "C:\\Users\\Ilsar\\Documents\\COMPILATION_TAU\\EX1\\FOLDER_4_INPUT\\TEST_01_Print_Primes.txt";
		String outputFilename = "C:\\Users\\Ilsar\\Documents\\COMPILATION_TAU\\EX1\\FOLDER_5_OUTPUT\\output.txt";

		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			Path path = Paths.get(outputFilename);
			File f = new File(outputFilename);
			if(!f.exists())
				f.createNewFile();
			List<String>lines = new ArrayList<>();
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/***********************/
			/* [4] Read next token */
			/***********************/
			s = l.next_token();

			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			while (s.sym != TokenNames.EOF)
			{
				if(s.sym == TokenNames.error) {
					lines.clear();
					lines.add("ERROR");
					break;
				}
				if(s.sym != TokenNames.COMMENT) {

					/************************/
				/* [6] Print to console */
					/************************/
					String out = "";
					out += TokenNames.numToName(s.sym);
					if (s.value != null) {
						out += ("(" + s.value + ")");
					}
					out += "[";
					out += l.getLine();
					out += ",";
					out += l.getTokenStartPosition();
					out += "]";

					/*********************/
				/* [7] Print to file */
					/*********************/
					System.out.println(out);
					lines.add(out);

					/***********************/
				/* [8] Read next token */
					/***********************/
				}
				s = l.next_token();
			}
			Files.write(path, lines);
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


