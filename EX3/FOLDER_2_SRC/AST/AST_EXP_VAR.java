package AST;

public class AST_EXP_VAR extends AST_EXP {
    public AST_VAR var;

    /******************/
    public AST_EXP_VAR(AST_VAR var) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("exp -> var\n");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.var = var;
    }

    /******************/
    /* CONSTRUCTOR(S) */
    public AST_VAR getVar() {
        return var;
    }

    public void setVar(AST_VAR var) {
        this.var = var;
    }

    /***********************************************/
	/* The default message for an exp var AST node */

    /***********************************************/
    public void PrintMe() {
        /************************************/
		/* AST NODE TYPE = EXP VAR AST NODE */
        /************************************/
        System.out.print("AST NODE EXP VAR\n");

        /*****************************/
		/* RECURSIVELY PRINT var ... */
        /*****************************/
        if (var != null) var.PrintMe();

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "EXP VAR");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);

    }

    @Override
    public AST_Node getLeft() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLeft() {
    }

    @Override
    public AST_Node getRight() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRight() {
    }
}
