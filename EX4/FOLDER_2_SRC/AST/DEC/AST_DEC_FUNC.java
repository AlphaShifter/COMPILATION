package AST.DEC;

import AST.AST_FUNC_SIG;
import AST.AST_GRAPHVIZ;
import AST.AST_ID_LIST;
import AST.AST_Node_Serial_Number;
import AST.STMT.AST_STMT_LIST;
import Auxillery.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_FUNCTION;
import TEMP.*;
import IR.*;
import MIPS.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AST_DEC_FUNC extends AST_DEC {


    public AST_STMT_LIST stmtList;
    public AST_FUNC_SIG sig;
    public static TYPE func_type = null;
    public static Map<String,Integer>funcLocalVarsCount = null;
    public static Map<String,Integer>funcFreqCount = null;

    public int numOfVars = 0;


    /*********************************************************/
    /* The default message for an unknown AST DECLERATION node */

    /*********************************************************/
    public AST_DEC_FUNC(AST_FUNC_SIG sig, AST_STMT_LIST list) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/

        System.out.format("funcDec -> SIG BODY)\n");

        left = sig;
        right = list;


        /*******************************/
		/* COPY INPUT DATA NENBERS ... */
        /*******************************/

        this.stmtList = list;
        this.sig = sig;
    }

    /************************************************/
	/* The printing message for an INT EXP AST node */

    /************************************************/
    public void PrintMe() {
        /*******************************/
		/* AST NODE TYPE = AST ID EXP */
        /*******************************/
        System.out.format("AST NODE decFUNC");
        if (sig != null) sig.PrintMe();
        if (stmtList != null) stmtList.PrintMe();


        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Func Declaration"));
        if (sig != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, sig.SerialNumber);
        if (stmtList != null)
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, stmtList.SerialNumber);

    }

    public TYPE SemantMe() {

        /****************************/
		/* [1] Begin Function Scope */
        /****************************/

        /*******************/
		/* [2] Semant sig */
        /*******************/
        SYMBOL_TABLE.getInstance().beginScope();
        funcLocalVarsCount = new HashMap<>();
        /*
        the funcLocalVarsCount will store in the format of name_freq:  the name and the order of the var amogs all of the vars in
        the same name under the function (like nested flow controls)
         */
        funcFreqCount = new HashMap<>();

        TYPE_FUNCTION newFuncDec = null;
        func_type = Util.stringToType(sig.type);
        newFuncDec = (TYPE_FUNCTION) sig.SemantMe();

        /*******************/
		/* [3] Semant Body */
        /*******************/
        stmtList.SemantMe();

        /*****************/
		/* [4] End Scope */
        /*****************/
        funcFreqCount = null;
        SYMBOL_TABLE.getInstance().endScope();
        numOfVars = funcLocalVarsCount.size();
        funcLocalVarsCount = null;

        SYMBOL_TABLE.getInstance().enter(sig.name, newFuncDec);


        /*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
        /*********************************************************/
        return newFuncDec; // returns the new type to be added to the list of function types
    }

    public TYPE cSemantMe(String name) {

        /****************************/
		/* [1] Begin Function Scope */
        /****************************/
        SYMBOL_TABLE.getInstance().beginScope();
        funcLocalVarsCount = new HashMap<>();


        /*******************/
		/* [2] Semant sig */
        /*******************/
        TYPE_FUNCTION newFuncDec = null;
        func_type = Util.stringToType(sig.type);
        newFuncDec = (TYPE_FUNCTION) sig.cSemantMe(name);


        /*******************/
		/* [3] Semant Body */
        /*******************/
        stmtList.SemantMe();

        /*****************/
		/* [4] End Scope */
        /*****************/
        SYMBOL_TABLE.getInstance().endScope();
        numOfVars = funcLocalVarsCount.size();
        funcLocalVarsCount = null;

        /*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
        /*********************************************************/
        return newFuncDec; // returns the new type to be added to the list of function types

    }

    public TEMP IRme()
    {


        //global function
        if(this.sig.container == null){

            //if main class, unique label
            String newLabel = this.isMainClass() ? "main" : IRcommand.getFreshFuncLabel(this.sig.name);

            //update the symbol table to have the new func
            TYPE_FUNCTION funcT = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(this.sig.name);
            funcT.myLabel = newLabel;

            //print the label
            IR.getInstance().Add_IRcommand(
                    new IRcommand_Label(newLabel)
            );


            IR.getInstance().Add_IRcommand(
                    new IRcommand_Fun_Prologue(this.numOfVars,this.isMainClass())
            );

            //IR the arguments
            for(AST_ID_LIST runner = this.sig.idList; runner != null; runner = runner.tail){
                AST_DEC_VAR local = runner.head;
                if(local == null)
                    break;
                local.IRme(); //this line also saves the argument on the FP
            }
        }
        if (stmtList != null) stmtList.IRme();

        //default return
        if(!this.isMainClass()){
            IR.getInstance().Add_IRcommand(new IRcommand_Return(ZERO_REG.getInstance()));
        } else{
            //end program
            IR.getInstance().Add_IRcommand(new IRcommand_Jump("END_OF_PROGRAM"));
        }

        IR.getInstance().Add_IRcommand(new IRcommand_Comment("End of " + this.sig.name + " function"));
        return null;
    }

    private boolean isMainClass(){
        return this.sig.name.toLowerCase().trim().equals("main");
    }
}
