package AST.STMT;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.EXP.AST_EXP;
import Auxillery.Util;
import IR.*;
import IR.IRcommand;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TEMP.TEMP;
import TYPES.TYPE;
import TYPES.TYPE_INT;
public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("stmt -> WHILE (exp) {stmts} \n");

		this.cond = cond;
		this.body = body;
		right = body;
		left = cond;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT WHILE\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"STMT WHILE");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}


	public TYPE SemantMe()
	{
		/****************************/
		/* [0] Semant the Condition */
		/****************************/
		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] condition inside WHILE is not integral\n",2,2);
			Util.printError(cond.myLine);
		}

		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		body.SemantMe();

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}
	@Override
	public TEMP IRme(){
		//get start label
		String startLabel = IRcommand.getFreshWhileStartLabel();
		//get the cond label
		String condLabel = IRcommand.getFreshWhileCondLabel();
		//first, jump to the condition
		IR.getInstance().Add_IRcommand(new IRcommand_Jump(condLabel));
		//enter the start
		IR.getInstance().Add_IRcommand(new IRcommand_Label(startLabel));
		//IR the body
		body.IRme();
		//enter the condition label
		IR.getInstance().Add_IRcommand(new IRcommand_Label(condLabel));
		//IR the condition
		TEMP condTemp = cond.IRme();
		//create the while condition
		IR.getInstance().Add_IRcommand(
				new IRcommand_While(condTemp,startLabel)
		);


		return null;
	}
}