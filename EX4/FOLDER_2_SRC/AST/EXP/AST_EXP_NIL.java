package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import TYPES.TYPE;
import TYPES.TYPE_NIL;
import TEMP.*;
import IR.*;

public class AST_EXP_NIL extends AST_EXP {

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_EXP_NIL() {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("exp -> NIL\n");

    }

    /************************************************/
	/* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE NILL \n");

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NIL"));
    }


    public TYPE SemantMe() {
        return TYPE_NIL.getInstance();
    }

    @Override
    public TEMP IRme(){
        return ZERO_REG.getInstance();
    }

}
