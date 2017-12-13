package AST;

import AST.DEC.AST_DEC_LIST;
import TYPES.TYPE;

public class AST_PROGRAM extends AST_Node
{
	/************************/
	/* simple variable name */
	/************************/
	public AST_DEC_LIST decList;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_PROGRAM(AST_DEC_LIST decLis)
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
		return this.decList.SemantMe();
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
