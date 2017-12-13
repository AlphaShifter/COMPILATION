package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;
import TYPES.TYPE_LIST;

public class AST_FUNC_SIG extends AST_Node {

    String type;
    String name;
    public AST_ID_LIST idList;

    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_FUNC_SIG(String type, String name, AST_ID_LIST idList) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/

        System.out.format("funcDec -> TYPE( %s ) NAME(%s)\n", type, name);

        left = idList;


        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.idList = idList;
    }

    /************************************************/
	/* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE decFUNC ( %s ) (%s)", type, name);
        if (idList != null) idList.PrintMe();


        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Func Declaration TYPE(%s) NAME(%s)", type, name));
        if (idList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, idList.SerialNumber);

    }

    @Override
    public TYPE SemantMe() {

        TYPE t;
        TYPE returnType = null;
        TYPE_LIST type_list = null;

        /*******************/
		/* [0] return type */
        /*******************/
        returnType = SYMBOL_TABLE.getInstance().find(type);


        if (returnType == null) {
            System.out.format(">> ERROR [%d:%d] non existing return type %s\n", 6, 6, returnType);
        }

        for (AST_Node node : idList) {
            AST_DEC_VAR var = (AST_DEC_VAR) node;
            t = SYMBOL_TABLE.getInstance().find(var.type);
            if (t == null) {
                System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, var.type);
            } else {
                type_list = new TYPE_LIST(t, type_list);
                SYMBOL_TABLE.getInstance().enter(var.name, t);
            }
        }

        /***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
        /***************************************************/

        SYMBOL_TABLE.getInstance().enter(name, new TYPE_FUNCTION(returnType, name, type_list));

        return null;

    }


}
