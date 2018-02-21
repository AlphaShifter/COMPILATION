package AST;

import AST.DEC.AST_DEC_VAR;

public class AST_ID_LIST extends AST_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_DEC_VAR head;
	public AST_ID_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ID_LIST(String type, String name, AST_ID_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
//		if (tail != null) System.out.print("ids -> ID ID ids\n");
//		if (tail == null) System.out.print("ids -> ID ID     \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = new AST_DEC_VAR(type, name, null);
		this.tail = tail;

		left = head;
		right = tail;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.format("AST NODE ID LIST:");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ID LIST");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}

	@Override
	public AST_DEC_VAR getHead() {
		return this.head;
	}

	@Override
	public AST_ID_LIST getTail() {
		return this.tail;
	}
}
