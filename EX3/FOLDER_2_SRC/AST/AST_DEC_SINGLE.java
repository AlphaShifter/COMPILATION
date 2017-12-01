package AST;

public class AST_DEC_SINGLE extends AST_DEC {
    public AST_DEC dec;

    /******************/
    public AST_DEC_SINGLE(AST_DEC dec) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.print("dec -> funcDec | varDec | classDec | arrayDec \n");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.dec = dec;
    }

    /******************/
    /* CONSTRUCTOR(S) */
    public AST_DEC getDec() {
        return dec;
    }

    public void setDec(AST_DEC dec) {
        this.dec = dec;
    }

    /*************************************************/
	/* The printing message for a binop exp AST node */

    /*************************************************/
    public void PrintMe() {
        /*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
        /*************************************/
        System.out.print("AST NODE SINGLE dec\n");

        /**************************************/
		/* RECURSIVELY PRINT left + right ... */
        /**************************************/
        if (dec != null) dec.PrintMe();


        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "Single Declaration");
        if (dec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dec.SerialNumber);
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
