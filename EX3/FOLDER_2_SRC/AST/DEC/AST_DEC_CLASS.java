package AST.DEC;

import AST.*;
import AST.VAR.AST_VAR_LIST;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_DEC_CLASS extends AST_DEC {

    public AST_CLASS_SIG sig;
    public AST_CFIELD_LIST cfieldList;
    public AST_FUNC_LIST funcList;
    public AST_VAR_LIST varList;

    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_DEC_CLASS(AST_CLASS_SIG sig, AST_CFIELD_LIST cfieldList) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/

        System.out.format("decClass -> SIG BODY )\n");

        right = cfieldList;
        left = sig;

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.cfieldList = cfieldList;
        this.sig = sig;
    }

    /************************************************/
	/* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST ID EXP */
        /*******************************/
        if (sig != null) sig.PrintMe();
        if (cfieldList != null) cfieldList.PrintMe();


        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration"));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, sig.SerialNumber);
        if (cfieldList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldList.SerialNumber);

    }


    public TYPE SemantMe() {
        /*************************/
		/* [1] Begin Class Scope */
        /*************************/
        SYMBOL_TABLE.getInstance().beginScope();

        //Sement the sig
        TYPE_CLASS t = sig.SemantMe();
        //Sement the body
        //TODO sement class vars
        //type.data_members = varList.SemantMe();
        //Sement the functions
        t.function_list = funcList.SemantMe();

        /*****************/
		/* [3] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();


        /*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
        /*********************************************************/
        return null;
    }


    public AST_FUNC_LIST getFuncList() {
        return funcList;
    }

    public AST_VAR_LIST getVarList() {
        return varList;
    }
//
//    public boolean varScanner() {
//        return Scanners.classVarInitScanner(this);
//    }

}
