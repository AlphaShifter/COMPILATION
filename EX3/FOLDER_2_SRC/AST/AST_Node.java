package AST;

import TYPES.TYPE;

import java.util.ArrayList;
import java.util.List;

public abstract class AST_Node
{
	public static int currLine = 0;

	public int myLine = currLine;

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

	public TYPE SemantMe() {return null;}

	public void printLineNum(){
		System.out.print(this.myLine + "   ");
		System.out.println(this.getClass().getSimpleName());
		if(left!=null)left.printLineNum();
		if(right!=null)right.printLineNum();
	}
}
