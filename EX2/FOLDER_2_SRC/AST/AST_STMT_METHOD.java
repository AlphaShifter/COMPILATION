package AST;

public class AST_STMT_METHOD extends AST_STMT {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_STMT_METHOD(String id, AST_VAR var, AST_EXP_LIST args){
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if ((var != null) && (args != null))
            System.out.print("====================== stmt -> var. %s (exps)\n", id);
        else if ((var != null) && (args == null))
            System.out.print("====================== stmt -> var. %s (exps)\n", id);

        //TODO: finish the current implementation - also, implement AST_EXP_METHOD
        this.cond = cond;
        this.body = body;

    }
}
