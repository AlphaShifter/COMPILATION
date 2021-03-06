package AST;

import AST.DEC.AST_DEC_LIST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import AST.EXP.*;
import TEMP.*;
import IR.*;

import java.util.ArrayList;
import java.util.List;

public class AST_PROGRAM extends AST_Node
{
	/************************/
	/* simple variable name */
	/************************/
	public AST_DEC_LIST decList;
	public static int global_count;

	public static List<String>globalsList;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_PROGRAM(AST_DEC_LIST decList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("Program -> [decList]+");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.decList = decList;
		right = decList;

		globalsList = new ArrayList<>();
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE PROGRAM");


		decList.PrintMe();
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("START OF PROGRAM"));

		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,decList.SerialNumber);
	}

	@Override
	public TYPE SemantMe() {
		TYPE res = this.decList.SemantMe();
		global_count= SYMBOL_TABLE.global_count;
		return res;
	}

	public TEMP IRme(){
		if(global_count != 0) {
			IR.getInstance().Add_IRcommand(new IRcommand_Label("GLOBAL_INITS"));
			//save ra
			IR.getInstance().Add_IRcommand(new IRcommand_Move(SAVE_REG.getInstance(3),RA_REG.getInstance()));
			IR.getInstance().Add_IRcommand(new IRcommand_SaveGlobalsOnHeap(global_count));
			for (String labal : globalsList)
				IR.getInstance().Add_IRcommand(new IRcommand_Jal(labal));
			//restore ra
			IR.getInstance().Add_IRcommand(new IRcommand_Move(RA_REG.getInstance(),SAVE_REG.getInstance(3)));
			//back
			IR.getInstance().Add_IRcommand(new IRcommand_JR());
		}
		return this.decList.IRme();
	}

//	public boolean concreateMixer(){
//		boolean res = true;
//		for(AST_Node dec: decList){
//			if(dec instanceof AST_DEC_CLASS){
//				res = res && ((AST_DEC_CLASS) dec).varScanner();
//			}
//			if (dec instanceof AST_DEC_ARRAY){
//				res = res && ((AST_DEC_ARRAY) dec).arrayScan();
//			}
//
//		}
//		return res;
//	}
}
