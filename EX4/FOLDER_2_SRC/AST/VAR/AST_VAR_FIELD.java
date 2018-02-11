package AST.VAR;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.STMT.AST_STMT_ASSIGN;
import Auxillery.Util;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import TEMP.*;
import MIPS.*;
import IR.*;

public class AST_VAR_FIELD extends AST_VAR {
    public AST_VAR var;
    public String fieldName;
    public TYPE_CLASS myclass = null;
    public int myPlace;
    public TEMP holder;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_VAR_FIELD(AST_VAR var, String fieldName) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("var -> var DOT ID( %s )\n", fieldName);

        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.var = var;
        this.fieldName = fieldName;
        right = var;
    }

    /*************************************************/
	/* The printing message for a field var AST node */

    /*************************************************/
    public void PrintMe() {
        /*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("AST NODE FIELD VAR\n");

        /**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        if (var != null) var.PrintMe();
        System.out.format("FIELD NAME( %s )\n", fieldName);

        /***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("FIELD VAR...->%s", fieldName));

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }

    public TYPE SemantMe() {
        TYPE t = null;
        TYPE_CLASS tc = null;

        /******************************/
		/* [1] Recursively semant var */
        /******************************/
        if (var != null) t = var.SemantMe();

        /*********************************/
		/* [2] Make sure type is a class */
        /*********************************/
        if(t.isArray())
            t = ((TYPE_ARRAY)t).type;
        if (!(t.isClass())) {
            System.out.format(">> ERROR [%d:%d] access %s field of a non-class variable\n", 6, 6, fieldName);
            Util.printError(this.myLine);
        } else {
            tc = (TYPE_CLASS) t;
            myclass = tc;
        }

        /************************************/
		/* [3] Look for fiedlName inside tc */
        /************************************/

        for (TYPE_LIST it = tc.data_members; it != null; it = it.tail) {
            TYPE_CLASS_VAR_DEC dec = (TYPE_CLASS_VAR_DEC)it.head;
            if (dec.name.equals(fieldName)) {
                myPlace = ((TYPE_CLASS_VAR_DEC) it.head).myPlace;
                return ((TYPE_CLASS_VAR_DEC) it.head).t;
            }
        }


        /*********************************************/
		/* [4] fieldName does not exist in class var */
        /*********************************************/
        System.out.format(">> ERROR [%d:%d] field %s does not exist in class\n", 6, 6, fieldName);
        Util.printError(this.myLine);
        return null;
    }

    @Override
    public TEMP IRme(){
        //IR the var
        holder = var.IRme();

        //if holder is zero, than it is uninited: throw exception
        if(!AST_STMT_ASSIGN.isAssign) {
            String legal = IRcommand.getFreshLegal();
            IR.getInstance().Add_IRcommand(new IRcommand_CheckPointerAccess(holder, legal));
            IR.getInstance().Add_IRcommand(new IRcommand_Label(legal));
        }
        //return the value in the right offset
        TEMP dest = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(
                new IRcommand_LoadFromHeap(dest,holder,myPlace)
        );
        return dest;
    }


    @Override
    public String getName() {
        //TODO sement before getting the name
        return fieldName;
    }
}
