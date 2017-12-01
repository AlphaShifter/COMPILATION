package AST;

public class AST_PROGRAM extends AST_Node {
    /************************/
    /* simple variable name */
    /************************/
    public AST_DEC_LIST decList;

    /******************/
    public AST_PROGRAM(AST_DEC_LIST decList) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("Program -> [decList]+");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.decList = decList;
    }

    /******************/
	/* CONSTRUCTOR(S) */
    public AST_DEC_LIST getDecList() {
        return decList;
    }

    public void setDecList(AST_DEC_LIST decList) {
        this.decList = decList;
    }

    /**************************************************/
	/* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() {
        /**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE PROGRAM");


        decList.PrintMe();
        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("START OF PROGRAM"));

        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, decList.SerialNumber);
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
