package AST;

public abstract class AST_Node
{

	public AST_Node left = null;
	public AST_Node right = null;

	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}

	public AST_Node getLeft() {
		return left;
	}
	public AST_Node getRight(){
		return right;
	}
}
