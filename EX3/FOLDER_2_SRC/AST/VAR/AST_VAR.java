package AST.VAR;

import AST.AST_Node;
import TYPES.TYPE;

public abstract class AST_VAR extends AST_Node
{
    /*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
    /*********************************************************/
    public void PrintMe()
    {
        System.out.print("UNKNOWN AST DECELERATION NODE");
    }
    public abstract  String getName();
    //public abstract TYPE SemantMe();

}
