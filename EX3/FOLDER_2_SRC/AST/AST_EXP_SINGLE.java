package AST;

public class AST_EXP_SINGLE extends AST_EXP {
    public AST_EXP exp;
    int OP;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_EXP_SINGLE(AST_EXP son) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("exp -> (exp)\n");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.exp = son;
    }

    public int getOP() {
        return OP;
    }

    public void setOP(int OP) {
        this.OP = OP;
    }

    public AST_EXP getExp() {
        return exp;
    }

    public void setExp(AST_EXP exp) {
        this.exp = exp;
    }

    /*************************************************/
	/* The printing message for a binop exp AST node */

    /*************************************************/
    public void PrintMe() {
        /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
        /*************************************/
        System.out.print("AST NODE SINGLE EXP\n");

        /**************************************/
		/* RECURSIVELY PRINT left + right ... */
        /**************************************/
        if (exp != null) exp.PrintMe();

        /***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("Single Expression"));

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
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
