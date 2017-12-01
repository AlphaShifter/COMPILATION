package AST;

public class AST_CFIELD extends AST_Node{
    public AST_DEC dec;
    public AST_CFIELD(AST_DEC v){

        System.out.println("cField -> varDec | funcDec ");

        this.dec=v;
        SerialNumber = AST_Node_Serial_Number.getFresh();
    }

    public AST_DEC getDec() {
        return dec;
    }

    public void setDec(AST_DEC dec) {
        this.dec = dec;
    }

    public void PrintMe()
    {
        /********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
        /********************************************/
        System.out.print("AST NODE cField\n");

        /***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
        /***********************************/
        if (dec != null) dec.PrintMe();

        /***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "CFIELD ");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,dec.SerialNumber);
    }

	@Override
	public AST_Node getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLeft() {
		// TODO Auto-generated method stub
	}

	@Override
	public AST_Node getRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRight() {
		// TODO Auto-generated method stub
	}
}
