package AST;

import TYPES.TYPE;

public abstract class AST_EXP extends AST_Node
{
	public int moish;
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST DECELERATION NODE");
	}
	public abstract TYPE getExpType();
	public abstract TYPE SemantMe();

}