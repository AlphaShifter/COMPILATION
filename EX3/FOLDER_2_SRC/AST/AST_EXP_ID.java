package AST;

import TYPES.TYPE;

public class AST_EXP_ID extends AST_EXP
{
	public String value;
	public AST_EXP exp;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_ID(String id, AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(exp != null)
			System.out.format("exp -> NEW ID( %s ) [exp}\n", id);
		else
			System.out.format("exp -> NEW ID( %s )\n", id);


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.value = id;
		this.exp = exp;
		right = exp;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE NEW ID( %s )\n",value);
		if (exp != null) exp.PrintMe();

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/


		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NEW ID(%s)", value));
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	@Override
	public TYPE getExpType() {
		//TODO the type is ID look for it in table and return it
		return null;
	}

}
