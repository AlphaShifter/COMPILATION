package AST.DEC;

import AST.AST_FUNC_SIG;
import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.STMT.AST_STMT_LIST;
import Auxillery.Util;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;

public class AST_DEC_FUNC extends AST_DEC {


    public AST_STMT_LIST stmtList;
    public AST_FUNC_SIG sig;
    public static TYPE func_type = null;


    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_DEC_FUNC(AST_FUNC_SIG sig, AST_STMT_LIST list) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/

        System.out.format("funcDec -> SIG BODY)\n");

        left = sig;
        right = list;


        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/

        this.stmtList = list;
        this.sig = sig;
    }

    /************************************************/
	/* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE decFUNC");
        if (sig != null) sig.PrintMe();
        if (stmtList != null) stmtList.PrintMe();


        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Func Declaration"));
        if (sig != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, sig.SerialNumber);
        if (stmtList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, stmtList.SerialNumber);

    }

    public TYPE SemantMe() {

        /****************************/
		/* [1] Begin Function Scope */
        /****************************/

        /*******************/
		/* [2] Semant sig */
        /*******************/
        SYMBOL_TABLE.getInstance().beginScope();

        TYPE_FUNCTION newFuncDec = null;
        func_type = Util.stringToType(sig.type);
        newFuncDec = (TYPE_FUNCTION) sig.SemantMe();

        /*******************/
		/* [3] Semant Body */
        /*******************/
        stmtList.SemantMe();

        /*****************/
		/* [4] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();

        SYMBOL_TABLE.getInstance().enter(sig.name, newFuncDec);


        /*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
        /*********************************************************/
        return newFuncDec; // returns the new type to be added to the list of function types
    }

    public TYPE cSemantMe(String name) {

        /****************************/
		/* [1] Begin Function Scope */
        /****************************/
        SYMBOL_TABLE.getInstance().beginScope();


        /*******************/
		/* [2] Semant sig */
        /*******************/
        TYPE_FUNCTION newFuncDec = null;
        newFuncDec = (TYPE_FUNCTION) sig.cSemantMe(name);


        /*******************/
		/* [3] Semant Body */
        /*******************/
        stmtList.SemantMe();

        /*****************/
		/* [4] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();


        /*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
        /*********************************************************/
        return newFuncDec; // returns the new type to be added to the list of function types

    }
}
