package AST.STMT;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.DEC.AST_DEC_FUNC;
import AST.EXP.AST_EXP;
import Auxillery.Util;
import IR.*;
import TEMP.*;
import TYPES.TYPE;
import TYPES.TYPE_VOID;

public class AST_STMT_RETURN extends AST_STMT {
    public AST_EXP res;
    private boolean myMain = false;

    /*******************/
    /*  CONSTRUCTOR(S) */

    /*******************/
    public AST_STMT_RETURN(AST_EXP res) {

        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (res != null) System.out.print("stmt -> RETURN exp SEMICOLON\n");
        if (res == null) System.out.print("stmt -> RETURN SEMICOLON\n");

        this.res = res;
        right = res;
    }

    public void PrintMe() {
        System.out.print("AST NODE STMT RETURN\n");
        if (res != null) res.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT RETURN");
        if (res != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, res.SerialNumber);
    }


    @Override
    public TYPE SemantMe() {
        TYPE t = null;
        if(AST_DEC_FUNC.isMain)
            this.myMain = true;
        if (res != null) {
            t = res.SemantMe();//TODO - maybe check if t is null
            if (Util.isA(t, AST_DEC_FUNC.func_type)) {
                return t;
            }
        } else {
            if (AST_DEC_FUNC.func_type == TYPE_VOID.getInstance()) {
                return null;
            }
        }
        System.out.println("error incompatible error in function type");
        Util.printError(res.myLine);
        return null;
    }

    @Override
    public TEMP IRme() {
        if(myMain) {
            IR.getInstance().Add_IRcommand(new IRcommand_Return(true));
            return null;
        }else {
            TEMP t = this.res == null ? ZERO_REG.getInstance() : this.res.IRme();
            IR.getInstance().Add_IRcommand(new IRcommand_Return(t));
            return t;
        }
    }

}