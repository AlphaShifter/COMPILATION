package AST;

public class AST_CFIELD_LIST extends AST_Node {
    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_CFIELD head;
    public AST_CFIELD_LIST tail;

    /******************/
    public AST_CFIELD_LIST(AST_CFIELD head, AST_CFIELD_LIST tail) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("cfields -> cfield cfields\n");
        if (tail == null) System.out.print("cfields -> cfield\n");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.head = head;
        this.tail = tail;
    }

    /******************/
	/* CONSTRUCTOR(S) */
    public AST_CFIELD getHead() {
        return head;
    }

    public void setHead(AST_CFIELD head) {
        this.head = head;
    }

    public AST.AST_CFIELD_LIST getTail() {
        return tail;
    }

    public void setTail(AST.AST_CFIELD_LIST tail) {
        this.tail = tail;
    }

    /******************************************************/
	/* The printing message for a cFIELD list AST node */

    /******************************************************/
    public void PrintMe() {
        /**************************************/
		/* AST NODE TYPE = AST cFIELD LIST */
        /**************************************/
        System.out.print("AST NODE CFIELD LIST\n");

        /*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "CFIELD LIST");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
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
