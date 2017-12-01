package AST;

public class AST_EXP_INT extends AST_EXP
{
	public int value;
	
	/******************/
	/* CONSTRUCTOR(S) */

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	/******************/
	public AST_EXP_INT(int value)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("exp -> INT( %d )\n", value);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.value = value;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE INT( %d )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("INT(%d)",value));
	}

	@Override
	public AST_Node getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLeft(){}

	@Override
	public AST_Node getRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRight(){}
}
