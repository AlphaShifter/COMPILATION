package AST;

public class AST_EXP_METHOD extends AST_EXP {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_EXP_METHOD(AST_VAR var, String id, AST_EXP_LIST args){
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
        this. args = args;

    }
}
