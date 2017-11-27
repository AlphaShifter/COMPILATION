package AST;

public class AST_STMT_METHOD extends AST_STMT {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_STMT_METHOD(AST_VAR var, String id, AST_EXP_LIST args){
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if ((var != null) && (args != null))
            System.out.printf("====================== stmt -> var. %s (exps)\n", id);
        else if ((var != null) && (args == null))
            System.out.printf("====================== stmt -> var. %s (exps)\n", id);

        //TODO: finish the current implementation - also, implement AST_EXP_METHOD
        this.var = var;
        this.id = id;
        this.args = args;

    }

    public void PrintMe()
    {
        /*******************************/
		/* AST NODE TYPE = AST INT METHOD */
        /*******************************/
        System.out.format("AST NODE METHOD( %s )\n", id);

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NAME(%s)",id));
        if(var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        if(args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
    }
}
