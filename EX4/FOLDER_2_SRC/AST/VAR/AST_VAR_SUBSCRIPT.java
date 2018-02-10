package AST.VAR;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.DEC.AST_DEC_CLASS;
import AST.DEC.AST_DEC_FUNC;
import AST.EXP.AST_EXP;
import AST.EXP.AST_EXP_INT;
import Auxillery.Util;
import IR.*;
import IR.IRcommandPrintString;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_INT;
import TEMP.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR {
    public AST_VAR var;
    public AST_EXP subscript;
    public int myPlace;
    public TEMP holder;
    public TEMP value;


    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_VAR_SUBSCRIPT(AST_VAR var, AST_EXP subscript) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("var -> var [ exp ]\n");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.var = var;
        this.subscript = subscript;
        left = var;
        right = subscript;
    }

    /*****************************************************/
	/* The printing message for a subscript var AST node */

    /*****************************************************/
    public void PrintMe() {
        /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
        /*************************************/
        System.out.print("AST NODE SUBSCRIPT VAR\n");

        /****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
        /****************************************/
        if (var != null) var.PrintMe();
        if (subscript != null) subscript.PrintMe();

        /***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "SUBSCRIPT VAR...[...]");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, subscript.SerialNumber);
    }


    @Override
    public TYPE SemantMe() {
        TYPE expType = subscript.SemantMe();
        if (expType != TYPE_INT.getInstance()) {
            System.out.println("Error: array access is not with integer value");
            Util.printError(this.myLine);
        }

        TYPE_ARRAY array =(TYPE_ARRAY)var.SemantMe();


        return array.type;
    }

    @Override
    public String getName() {
        return this.var.getName();
    }

    public TEMP IRme() {

        TEMP t;
        TEMP arrayStart;

        if(subscript instanceof AST_EXP_INT) {
            t = TEMP_FACTORY.getInstance().getFreshTEMP();
            arrayStart = var.IRme();
            IR.getInstance().Add_IRcommand(
                    new IRcommand_Access_Array(t, arrayStart, ((AST_EXP_INT) subscript).value)
            );
        } else {
            this.value = subscript.IRme();
            t = TEMP_FACTORY.getInstance().getFreshTEMP();
            arrayStart = var.IRme();
            IR.getInstance().Add_IRcommand(
                    new IRcommand_Access_Array(t, arrayStart, value)
            );
        }

        this.holder = arrayStart;

        return t;
    }

}
