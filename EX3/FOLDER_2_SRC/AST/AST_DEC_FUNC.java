package AST;

public class AST_DEC_FUNC extends AST_DEC
{

	String type;
	String name;
	public AST_STMT_LIST stmtList;
	public AST_ID_LIST idList;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_FUNC(String type, String name, AST_ID_LIST idList, AST_STMT_LIST stmtList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("funcDec -> TYPE( %s ) NAME(%s)\n", type,name);

		left = idList;
		right = stmtList;


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.name = name;
		this.stmtList = stmtList;
		this.idList = idList;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE decFUNC ( %s ) (%s)",type, name);
		if (idList != null) idList.PrintMe();
		if (stmtList != null) stmtList.PrintMe();


		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Func Declaration TYPE(%s) NAME(%s)", type, name));
		if (idList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, idList.SerialNumber);
		if (stmtList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, stmtList.SerialNumber);

	}}
