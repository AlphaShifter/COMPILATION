package AST.VAR;

import AST.AST_Node;
import TYPES.TYPE;

public abstract class AST_VAR extends AST_Node
{

    static int varIndex = 0;

    int currVarIndex;

    /*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
    /*********************************************************/
    public void PrintMe()
    {
        System.out.print("UNKNOWN AST DECELERATION NODE");
    }
    public abstract  String getName();
    public boolean isGlobal(){return  false;}
    //public abstract TYPE SemantMe();

}
