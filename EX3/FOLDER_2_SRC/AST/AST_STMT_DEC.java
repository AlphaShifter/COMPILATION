package AST;

public class AST_STMT_DEC extends AST_STMT
{
	public AST_DEC_VAR dec;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_DEC(AST_DEC_VAR dec)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("stmt -> varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.dec = dec;
		right = dec;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
				/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SINGLE dec\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (dec != null) dec.PrintMe();



		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber,"Var Declaration");
		if (dec  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,dec.SerialNumber);
	}
}
