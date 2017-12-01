package AST;

public class AST_EXP_STRING extends AST_EXP
{
	public String value;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String str)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format(" exp -> String( %s )\n", str);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.value = str;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE STRING( %s )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",value));
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
