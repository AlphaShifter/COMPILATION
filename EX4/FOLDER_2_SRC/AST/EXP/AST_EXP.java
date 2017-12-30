package AST.EXP;

import AST.AST_Node;
import TYPES.TYPE;
import TEMP.*;
import IR.*;
import MIPS.*;
public abstract class AST_EXP extends AST_Node
{
	public int moish;
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST DECELERATION NODE");
	}
//	public abstract TYPE getExpType();
//	public abstract TYPE SemantMe();
	public TEMP IRme()
{
	return null;
}
}