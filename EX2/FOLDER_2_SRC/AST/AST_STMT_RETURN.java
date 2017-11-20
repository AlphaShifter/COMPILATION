package AST;

public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP res;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP res)
	{
		this.res = res;
	}
	public void PrintMe()
	{
		System.out.print("AST NODE RETURN STMT\n");
		if(res != null) res.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"return\n");
		if(res != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,res.SerialNumber);
	}
}