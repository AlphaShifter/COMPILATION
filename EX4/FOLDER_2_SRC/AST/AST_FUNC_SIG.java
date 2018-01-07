package AST;

import AST.DEC.AST_DEC_FUNC;
import AST.DEC.AST_DEC_VAR;
import Auxillery.Util;
import Auxillery.VAR_KIND;
import IR.IR;
import IR.IRcommand;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import TEMP.*;
import IR.*;

import java.util.ArrayList;
import java.util.List;

public class AST_FUNC_SIG extends AST_Node {

    public String type;
    public String name;
    public AST_ID_LIST idList;
    public TYPE_CLASS container = null;

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
        if(this.type.equals("void"))
            returnType = TYPE_VOID.getInstance();
        else
            returnType = SYMBOL_TABLE.getInstance().find(type);

        if (returnType == null) {
            System.out.println("Error: no such type " + type);
            Util.printError(myLine);
        }

        // check whether a function of same name already exists
        if (SYMBOL_TABLE.getInstance().find(name) != null) {
            System.out.format(">> ERROR [%d:%d] function of name %s has already been declared\n", 6, 6, name);
            Util.printError(myLine);
        }

        if (returnType == null) {
            System.out.format(">> ERROR [%d:%d] non existing return type %s\n", 6, 6, returnType);
            Util.printError(myLine);
        }

        if (idList != null) {
            type_list = new TYPE_LIST(null,null);
            for (AST_Node node : idList) {
                AST_DEC_VAR var = (AST_DEC_VAR) node;
                t = SYMBOL_TABLE.getInstance().find(var.type);
                if (t == null) {
                    System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, var.type);
                } else {

                    type_list.add(t);
                    SYMBOL_TABLE.getInstance().enter(var.name, t);
                    if (AST_DEC_FUNC.funcLocalVarsCount != null) {
                        AST_DEC_FUNC.funcLocalVarsCount.put(var.name, AST_DEC_FUNC.funcLocalVarsCount.size() + 1);
                        var.myPlace = AST_DEC_FUNC.funcLocalVarsCount.size();
                        var.varKind = VAR_KIND.ARGUMENT;
                    }

                }
            }
        }

        /***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
        /***************************************************/

        TYPE_FUNCTION newFuncDec = new TYPE_FUNCTION(returnType, this.name, type_list);

        SYMBOL_TABLE.getInstance().enter(this.name, newFuncDec);

        return newFuncDec; // returns the newly created type to the ast function class

    }


    public TYPE cSemantMe(String className) {

        TYPE t;
        TYPE returnType = null;
        TYPE_LIST type_list = null;

        /*******************/
		/* [0] return type */
        /*******************/
        if(this.type.equals("void"))
            returnType = TYPE_VOID.getInstance();
        else
            returnType = SYMBOL_TABLE.getInstance().find(type);

        if (returnType == null) {
            System.out.println("Error: no such type " + type);
            Util.printError(myLine);
        }

        // check whether a function of same name already exists
        if (SYMBOL_TABLE.getInstance().find(name) != null) {
            System.out.format(">> ERROR [%d:%d] function of name %s has already been declared\n", 6, 6, name);
            Util.printError(myLine);
        }

        if (returnType == null) {
            System.out.format(">> ERROR [%d:%d] non existing return type %s\n", 6, 6, "null");
            Util.printError(myLine);
        }

        if (idList != null) {
            for (AST_Node node : idList) {
                AST_DEC_VAR var = (AST_DEC_VAR) node;
                t = SYMBOL_TABLE.getInstance().find(var.type);
                if (t == null) {
                    System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, var.type);
                } else {
                    type_list = new TYPE_LIST(t, type_list);
                    SYMBOL_TABLE.getInstance().enter(var.name, t);
                    if (AST_DEC_FUNC.funcLocalVarsCount != null) {
                        AST_DEC_FUNC.funcLocalVarsCount.put(var.name, AST_DEC_FUNC.funcLocalVarsCount.size() + 1);
                        var.myPlace = AST_DEC_FUNC.funcLocalVarsCount.size();
                        var.varKind = VAR_KIND.ARGUMENT;
                    }
                }
            }
        }

        // check whether function of same name exists in father class
        TYPE_CLASS containingClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(className);
        // check if the father has a function by the same name

        if(containingClass.localFuncs.findInList(this.name) != null){
            System.out.println("Error duplicate function declaration");
            Util.printError(this.myLine);
        }


        TYPE_FUNCTION overloadedFunc = (TYPE_FUNCTION) containingClass.function_list.findInList(this.name);
        if (overloadedFunc != null) { // if function by same name exists in father
            // if return type is different that is an error
            if (overloadedFunc.returnType != returnType) {
                System.out.format(">> ERROR [%d:%d] function overloading\n", 6, 6);
                Util.printError(myLine);
            } else {
                // if return type is same but args are different that is an error
                if (type_list == null) {
                    if (overloadedFunc.arguments != null) {
                        System.out.format(">> ERROR [%d:%d] function overloading\n", 6, 6);
                        Util.printError(myLine);
                    }
                } else if (!type_list.compareFuncArgsByType(containingClass.function_list)) {
                    System.out.format(">> ERROR [%d:%d] function overloading\n", 6, 6);
                    Util.printError(myLine);
                }
                // if return type and args correspond that is fine
            }
        }


        /***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
        /***************************************************/

        TYPE_FUNCTION newFuncDec = new TYPE_FUNCTION(returnType, name, type_list);

        SYMBOL_TABLE.getInstance().enter(name, newFuncDec);
        containingClass.localFuncs.add(newFuncDec);

        this.container = containingClass;

        return newFuncDec; // returns the newly created type to the ast function class

    }

}
