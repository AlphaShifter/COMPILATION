package AST.DEC;

import AST.EXP.AST_EXP;
import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.STMT.AST_STMT_ASSIGN;
import Auxillery.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import AST.EXP.*;
import TEMP.*;
import IR.*;
import MIPS.*;

public class AST_DEC_VAR extends AST_DEC {

    public String type;
    public String name;
    public AST_EXP exp;
    public int myPlace;
    public VAR_KIND varKind;

    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_DEC_VAR(String type, String name, AST_EXP exp) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (exp != null)
            System.out.format(" decVar -> TYPE( %s ) NAME(%s) ASSIGN EXP SEMICOLON\n", type, name);
        else
            System.out.format(" decVar -> TYPE( %s ) NAME(%s)SEMICOLON\n", type, name);


        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.type = type;
        this.name = name;
        this.exp = exp;

        right = exp;
    }

    /************************************************/
	/* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE decVar ( %s ) (%s)\n", type, name);
        if (exp != null) exp.PrintMe();

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Variable Deceleration: TYPE(%s) NAME(%s)", type, name));
        if (exp != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }

    public TYPE SemantMe() {
        TYPE t;

        /****************************/
		/* [1] Check If Type exists */
        /****************************/
        t = SYMBOL_TABLE.getInstance().find(type);
        if (t == null) {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, type);
            Util.printError(myLine);
        }
        TYPE nameType = Util.stringToType(name);
        if (nameType != null) {
            if (nameType.name.equals(this.name)) {
                System.out.println("Error: illegal name for var");
                Util.printError(myLine);
            }
        }

        /**************************************/
		/* [2] Check That Name does NOT exist */
        /**************************************/
        if (SYMBOL_TABLE.getInstance().findInCurrScope(name) != null) {
            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", 2, 2, name);
            Util.printError(this.myLine);
        }

        if (exp != null) {
            //check if we can assign the exp into the dec
            TYPE expType = exp.SemantMe();
            if (!AST_STMT_ASSIGN.assignmentChecker(t, expType)) {
                System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n", 6, 6);
                Util.printError(exp.myLine);
            }
        }

        /***************************************************/
		/* [3] Enter the Function Type to the Symbol Table */
        /***************************************************/
        SYMBOL_TABLE.getInstance().enter(name, t);


        //check if we are at function. if we do, get the count
        if (AST_DEC_FUNC.funcLocalVarsCount != null) {
            AST_DEC_FUNC.funcLocalVarsCount.put(name, AST_DEC_FUNC.funcLocalVarsCount.size() + 1);
            myPlace = AST_DEC_FUNC.funcLocalVarsCount.size();
            varKind = VAR_KIND.LOCAL;
        }
        //check if we are at class. if we do, get the count
        else if (AST_DEC_CLASS.classLocalVarsCount != null) {
            AST_DEC_CLASS.classLocalVarsCount.put(name, AST_DEC_CLASS.classLocalVarsCount.size() + 1);
            myPlace = AST_DEC_CLASS.classLocalVarsCount.size();
            varKind = VAR_KIND.DATA_MEMBER;
        } else {
            //TODO singlton that counts how many locals we have seen? place it inside symbol table
            myPlace = SYMBOL_TABLE.var_count++;
            varKind = VAR_KIND.LOCAL;
        }

        /*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
        /*********************************************************/
        return null;
    }

    public TYPE cSemantMe(String containingClassName) {
        TYPE t;

        /****************************/
		/* [1] Check If Type exists */
        /****************************/
        t = SYMBOL_TABLE.getInstance().find(type);
        TYPE_CLASS_VAR_DEC newDec = null;
        if (t == null) {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n", 2, 2, type);
            Util.printError(this.myLine);

        }

        /**************************************/
		/* [2] Check That Name does NOT exist */
        /**************************************/
        if (SYMBOL_TABLE.getInstance().findInCurrScope(name) != null) {
            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", 2, 2, name);
            Util.printError(this.myLine);

        }


        TYPE_CLASS containingClass = (TYPE_CLASS) SYMBOL_TABLE.getInstance().find(containingClassName);

        //check if the class already has it
        for (TYPE_LIST typeList = containingClass.data_members; typeList != null; typeList = typeList.tail) {
            TYPE_CLASS_VAR_DEC varDec = (TYPE_CLASS_VAR_DEC) typeList.head;
            if (varDec != null) {
                if (varDec.name.equals(this.name)) {
                    System.out.println("Error: variable shadowing is illegal");
                    Util.printError(this.myLine);
                }
            }
        }

        if (exp instanceof AST_EXP_INT) {
            if (!(t instanceof TYPE_INT)) { // if assignment is of type 'int' check that variable is of type 'int'
                System.out.format(">> ERROR [%d:%d] assignment to " +
                        "variable %s is of invalid type\n", 2, 2, name);
                Util.printError(exp.myLine);
            } else {
                newDec = new TYPE_CLASS_VAR_DEC(TYPE_INT.getInstance(), name);
            }
        } else if (exp instanceof AST_EXP_STRING) {
            if (!(t instanceof TYPE_STRING)) {    // if assignment is of type 'string'
                // check that variable is of type 'string'
                System.out.format(">> ERROR [%d:%d] assignment to " +
                        "variable %s is of invalid type\n", 2, 2, name);
                Util.printError(exp.myLine);
            } else {
                newDec = new TYPE_CLASS_VAR_DEC(TYPE_STRING.getInstance(), name);
            }
        } else if (exp instanceof AST_EXP_NIL) {
            // if assignment is NIL check that variable is of pointer type
            if (Util.isPrimitive(t)) {
                System.out.format(">> ERROR [%d:%d] assignment to " +
                        "variable %s is of invalid type\n", 2, 2, name);
                Util.printError(exp.myLine);
            } else {
                newDec = new TYPE_CLASS_VAR_DEC(t, name);
            }
        } else if (exp == null) {
            newDec = new TYPE_CLASS_VAR_DEC(t, name);
        }
        containingClass.data_members.add(newDec);


        /***************************************************/
		/* [3] Enter the Variable to the Symbol Table */
        /***************************************************/
        SYMBOL_TABLE.getInstance().enter(name, t);

        return newDec;
    }

    public TEMP IRme() {
        if (exp != null) {

            TEMP t = exp.IRme();
            IR.getInstance().Add_IRcommand(
                    new IRcommand_Store_AddressLocalVar(t, myPlace)
            );
//			IR.getInstance().Add_IRcommand(new IRcommand_Move(
//					sir_MIPS_a_lot.getInstance().addressLocalVar(myPlace),
//					exp.IRme()));
        } else { // no exp: either default or an argument
            //local decs, store it with 0 value
            if (this.varKind == VAR_KIND.LOCAL) {
                IR.getInstance().Add_IRcommand(
                        new IRcommand_Store_AddressLocalVar(ZERO_REG.getInstance(), myPlace)
                );
            }
            //else if it an argument, get it value from the $a registers
            //TODO change from 0-4 arguments to list that starts on a0
            else if (this.varKind == VAR_KIND.ARGUMENT) {
                IR.getInstance().Add_IRcommand(
                        new IRcommand_Store_AddressLocalVar(ARGUMENT.getInstance(myPlace-1), myPlace)
                );
            }

        }
        return null;
    }
}
