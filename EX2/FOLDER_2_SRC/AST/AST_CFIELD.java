package AST;

public class AST_CFIELD extends AST_Node{
    AST_DEC dec;
    public AST_CFIELD(AST_DEC v){
        this.dec=v;
        SerialNumber = AST_Node_Serial_Number.getFresh();
    }
    public void PrintMe()
    {
        /********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
        /********************************************/
        System.out.print("AST NODE ASSIGN DEC\n");

        /***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
        /***********************************/
        if (dec != null) dec.PrintMe();

        /***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
//        AST_GRAPHVIZ.getInstance().logNode(
//                SerialNumber,
//                "ASSIGN\nleft := right\n");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,dec.SerialNumber);
    }
}
