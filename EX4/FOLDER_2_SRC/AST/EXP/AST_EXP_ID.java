package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import Auxillery.Util;
import IR.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import TEMP.*;

public class AST_EXP_ID extends AST_EXP {
    public String value;
    public AST_EXP exp;
    public TYPE_CLASS myClass;


    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_EXP_ID(String id, AST_EXP exp) {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (exp != null)
            System.out.format("exp -> NEW ID( %s ) [exp]\n", id);
        else
            System.out.format("exp -> NEW ID( %s )\n", id);


        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.value = id;
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
        System.out.format("AST NODE NEW ID( %s )\n", value);
        if (exp != null) exp.PrintMe();

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/


        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NEW ID(%s)", value));
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }

    @Override
    public TYPE SemantMe() {
        if (exp != null) { //array assignment
            TYPE expType = exp.SemantMe();
            if (expType != TYPE_INT.getInstance()) { //array init can be only with integer size
                System.out.println("ERROR: creating an array with non-integer size");
                Util.printError(myLine);
                return null;
            }
            TYPE arrT = Util.stringToType(value); //return the array type
            if (arrT == null) {
                System.out.println("ERROR: unknown object " + value);
                Util.printError(myLine);
            }
            return new TYPE_ARRAY(arrT, null);
        }
        TYPE t = SYMBOL_TABLE.getInstance().find(value);
        if (t == null) {
            System.out.println("ERROR: unknown object " + value);
            Util.printError(myLine);
        }
        if(Util.isPrimitive(t) || t == TYPE_NIL.getInstance()){
            System.out.println("Error: cannot initialize new primitive type");
            Util.printError(myLine);
        }
        if(t.isArray() && exp == null){
            System.out.println("Error: cannot initialize new array without specifying the size");
            Util.printError(myLine);
        }
        if(t.isClass())
            myClass = (TYPE_CLASS)t;

        return t;
    }

    @Override
    public TEMP IRme() {
        TEMP address = TEMP_FACTORY.getInstance().getFreshTEMP();
        if (exp != null) {
            TEMP defaultValue = TEMP_FACTORY.getInstance().getFreshTEMP();
            //if its a new array
            TEMP size = exp.IRme(); // find the size of the array through the expression in the brackets
            int arraySize;
//            if (value.equals("int")) {
                // assume that exp is an integer
                arraySize = ((AST_EXP_INT) exp).value;
                IR.getInstance().Add_IRcommand(
                        new IRcommand_mallocHeap(address, arraySize + 1)
                );

                IR.getInstance().Add_IRcommand(
                        new IRcommand_SaveOnHeap(size, address, 0)
                );

                IR.getInstance().Add_IRcommand(
                        new IRcommandConstInt(defaultValue, 0)
                );

                for (int i = 1; i < arraySize + 1; i++) {
                    // fill array with zero values
                    IR.getInstance().Add_IRcommand(
                            new IRcommand_SaveOnHeap(defaultValue /* should substitute for zero */, address, i)
                    );
                }

                return address;
           // }
        } else { // a class

            //malloc the heap
            IR.getInstance().Add_IRcommand(
                    new IRcommand_mallocHeap(address,  myClass.data_members.getSize()+ 1)
            );
            //init the funcs

            if(myClass.function_list.getSize() > 0){
                //save the correct func table
                TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
                IR.getInstance().Add_IRcommand(new IRcommand_GetFunctionFromTable(t,myClass.name));
                IR.getInstance().Add_IRcommand(new IRcommand_SaveOnHeap(t,address,0));
            }

            //init the vars
            int offset = 1;
            for(Object e: myClass.inits){
                if(e instanceof Integer){
                    TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
                    IR.getInstance().Add_IRcommand(new IRcommandConstInt(t,(Integer)e));
                    IR.getInstance().Add_IRcommand(
                            new IRcommand_SaveOnHeap(t,address,offset)
                    );
                    offset++;
                } else if(e instanceof String){
                    TEMP s = AST_EXP_STRING.irString((String)e);
                    IR.getInstance().Add_IRcommand(
                            new IRcommand_SaveOnHeap(s,address,offset)
                    );
                    offset++;
                }
            }

            return address;
        }





     //   return null;
    }
}
