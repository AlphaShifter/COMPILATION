package AST;

public class AST_DEC_ARRAY extends AST_DEC
{

	String name1;
	String name2;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_ARRAY(String name1, String name2)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("arrayDec ->  ARRAY ID( %s ) EQ ID(%s)\n", name1,name2);



		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name1 = name1;
		this.name2 = name2;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE ARRAY ID( %s ) EQ ID(%s)", name1,name2);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Array Declaration NAME1(%s) NAME2(%s)", name1, name2));


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
	public void setRight(){}}
