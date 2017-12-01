package AST;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_EXP cond,AST_STMT_LIST body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("stmt -> IF (exp) {stmts} \n");

		this.cond = cond;
		this.body = body;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */

	public AST_EXP getCond() {
		return cond;
	}

	public void setCond(AST_EXP cond) {
		this.cond = cond;
	}

	public AST_STMT_LIST getBody() {
		return body;
	}

	public void setBody(AST_STMT_LIST body) {
		this.body = body;
	}

	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT IF\n");

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
				"STMT IF");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
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