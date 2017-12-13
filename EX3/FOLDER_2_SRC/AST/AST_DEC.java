package AST;

import TYPES.TYPE;

public abstract class AST_DEC extends AST_Node
{
	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST DECELERATION NODE");
	}
//	public abstract TYPE SemantMe();

}
