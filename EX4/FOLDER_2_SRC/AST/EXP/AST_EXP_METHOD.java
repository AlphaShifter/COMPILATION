package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node;
import AST.AST_Node_Serial_Number;
import AST.VAR.AST_VAR;
import Auxillery.Util;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_EXP_METHOD extends AST_EXP {

    public String id;
    public AST_VAR var;
    public AST_EXP_LIST args;

    public AST_EXP_METHOD(AST_VAR var, String id, AST_EXP_LIST args) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if ((var != null) && (args != null))
            System.out.printf("exp -> var. %s (exps)\n", id);
        else if ((var != null) && (args == null))
            System.out.printf("exp -> var. %s ()\n", id);
        else if ((var == null) && (args != null))
            System.out.printf("exp -> %s (exps)\n", id);
        else
            System.out.printf("exp -> %s ()\n", id);

        this.var = var;
        this.id = id;
        this.args = args;
        left = var;
        right = args;
    }

    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST INT METHOD */
        /*******************************/
        System.out.format("AST NODE METHOD( %s )\n", id);
        if (var != null) var.PrintMe();
        if (args != null) args.PrintMe();
        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Call for method: NAME(%s)", id));
        if (var != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
        }
        if (args != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
        }
    }

    @Override
    public TYPE SemantMe() {
        TYPE_FUNCTION func = null;
        if(var != null) {
            TYPE c = var.SemantMe();
            //check if c is a class
            if (!c.isClass()) {
                System.out.println("Error: calling a method from non-class object");
                Util.printError(this.myLine);
                return null;
            }
            TYPE_CLASS classType = (TYPE_CLASS) c;


            func =(TYPE_FUNCTION)classType.function_list.findInList(this.id);

            //check if we got the func
            if (func == null) {
                System.out.println("ERROR: no such func " + id + " in class" + classType.name);
                Util.printError(this.myLine);
                return null;
            }
        } else{
            func =(TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(this.id);
            if (func == null) {
                System.out.println("ERROR: no such func " + id);
                Util.printError(this.myLine);
                return null;
            }
        }
        //we found the func, now check the args
        //check case: no args
        if (args == null) {
            if (func.arguments != null) {
                System.out.println("Error: too few arguments sent to function");
                Util.printError(myLine);
            }
        }
        if (func.arguments == null) {
            if (args != null) {
                System.out.println("Error: too many arguments sent to function");
                Util.printError(myLine);
            }
        }
        //if both are null: end
        if(!(func.arguments == null && args == null))
            funcCallSemanter(args, func);
        //the args are good, return the type
        return func.returnType;

    }


    public static void funcCallSemanter(AST_EXP_LIST args, TYPE_FUNCTION func) {
        //run on both
        AST_EXP_LIST argRunner = args;
        TYPE_LIST funcRunner = func.arguments;
        while (argRunner != null && funcRunner != null) {
            //check if the current argument is compatible with the current func argument
            TYPE currArgType = argRunner.head.SemantMe();
            TYPE currFuncType = funcRunner.head;
            if(currArgType.isArray() && currFuncType.isArray()){
                currArgType = ((TYPE_ARRAY)currArgType).type;
                currFuncType = ((TYPE_ARRAY)currFuncType).type;
            }
            if (currArgType != currFuncType) { //if they are the same - no problem
                if (!Util.isA(currArgType, currFuncType)) {
                    // the arg is not compatible with the function args
                    System.out.println("Error: invalid parameter type sent to function");
                    Util.printError(argRunner.head.myLine);
                }

            }
            //move both runners
            argRunner = argRunner.tail;
            funcRunner = funcRunner.tail;
        }
        //check that both are empty
        if (argRunner != null) {
            System.out.println("Error: too many arguments sent to function");
            Util.printError(args.myLine);
        }
        if (funcRunner != null) {
            System.out.println("Error: too few arguments sent to function");
            Util.printError(args.myLine);
        }

        //we got here - all good

    }

    public TEMP IRme()
    {

        if(this.var == null) { // not a class function
            if(this.id.equals("PrintInt")){
                IR.getInstance().Add_IRcommand(new IRcommandPrintInt(this.args.getHead().IRme()));
                return ZERO_REG.getInstance();
            }

            //save the temps on stack
            IR.getInstance().Add_IRcommand(new IRcommand_SaveTempsOnStack());
            //save the arguments to $a registers
            //TODO make this an array
            int count = 0;
            if(this.args != null) {
                for (AST_Node runner : this.args) {
                    AST_EXP head = (AST_EXP) runner;
                    IR.getInstance().Add_IRcommand(
                            new IRcommand_Move(ARGUMENT.getInstance(count), head.IRme()
                            ));
                    count++;
                }
            }
            //jump
            String targetLabel = ((TYPE_FUNCTION)SYMBOL_TABLE.getInstance().find(this.id)).myLabel;
            IR.getInstance().Add_IRcommand(new IRcommand_Jal(targetLabel));

            //we are back

            //restore the temps
            IR.getInstance().Add_IRcommand(new IRcommand_LoadTempsFromStack());
            //load the return value
            TEMP returnValue = TEMP_FACTORY.getInstance().getFreshTEMP();
            IR.getInstance().Add_IRcommand(new IRcommand_LoadFromStack(returnValue,0));
            //pop the return value and temps from stack
            IR.getInstance().Add_IRcommand(new IRcommand_Fun_Epiloge());
            return returnValue;
        }
        return null;
    }

}
