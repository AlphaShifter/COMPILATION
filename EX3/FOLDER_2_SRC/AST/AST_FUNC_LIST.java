package AST;

import AST.DEC.AST_DEC_FUNC;

public class AST_FUNC_LIST extends AST_LIST
{
    /****************/
	/* DATA MEMBERS */
    /****************/
    public AST_DEC_FUNC head;
    public AST_FUNC_LIST tail;

    /******************/
	/* CONSTRUCTOR(S) */
    /******************/
    public AST_FUNC_LIST(AST_DEC_FUNC head, AST_FUNC_LIST tail)
    {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("decs -> dec decs\n");
        else System.out.print("decs -> dec\n");

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.head = head;
        this.tail = tail;

        left = head;
        right = tail;
    }

    /******************************************************/
	/* The printing message for a cFIELD list AST node */
    /******************************************************/
    public void PrintMe()
    {
        /**************************************/
		/* AST NODE TYPE = AST cFIELD LIST */
        /**************************************/
        System.out.print("AST NODE DEC LIST\n");

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
                "DEC LIST");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
    }

    @Override
    public AST_DEC_FUNC getHead() {
        return this.head;
    }

    @Override
    public AST_FUNC_LIST getTail() {
        return this.tail;
    }
}
