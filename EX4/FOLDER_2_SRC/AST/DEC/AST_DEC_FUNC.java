package AST.DEC;

import AST.*;
import AST.STMT.AST_STMT_LIST;
import Auxillery.*;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;
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
    public static boolean isMain = false;

    public int myPlace;
    public boolean isClassFunc;
    private String myLabel;
    TYPE_CLASS container = null;


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
        this.myPlace = -1;
        this.isClassFunc = false;

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

        if(newFuncDec.getName().equals("main"))
            isMain = true;

        /*******************/
		/* [3] Semant Body */
        /*******************/
        stmtList.SemantMe();

        isMain = false;

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
/*
        the funcLocalVarsCount will store in the format of name_freq:  the name and the order of the var amogs all of the vars in
        the same name under the function (like nested flow controls)
         */
        funcFreqCount = new HashMap<>();
        //add the class's var table
        int count = 1;
        for(String e: AST_DEC_CLASS.classLocalVarsCount.keySet()){
            funcFreqCount.put(e,1);
            funcLocalVarsCount.put(e + "_1",AST_DEC_CLASS.classLocalVarsCount.get(e));
            count++;
        }

        /*******************/
		/* [2] Semant sig */
        /*******************/
        TYPE_FUNCTION newFuncDec = null;
        func_type = Util.stringToType(sig.type);
        newFuncDec = (TYPE_FUNCTION) sig.cSemantMe(name);

        this.myPlace = newFuncDec.myPlace;
        this.isClassFunc = true;
        this.container = sig.container;

        /*******************/
		/* [3] Semant Body */
        /*******************/
        stmtList.SemantMe();

        /*****************/
		/* [4] End Scope */
        /*****************/
        funcFreqCount = null;

        SYMBOL_TABLE.getInstance().endScope();
        SYMBOL_TABLE.getInstance().enter(sig.name,newFuncDec);


        numOfVars = funcLocalVarsCount.size();
        if(AST_DEC_CLASS.classLocalVarsCount != null){
            numOfVars += AST_DEC_CLASS.classLocalVarsCount.size();
        }
        funcLocalVarsCount = null;

        /*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
        /*********************************************************/
        return newFuncDec; // returns the new type to be added to the list of function types

    }

    public TEMP IRme()
    {


        //global function
        if(this.container == null) {
            //if main class, unique label
             this.myLabel = this.isMainClass() ? "main" : IRcommand.getFreshFuncLabel(this.sig.name);
        } else {
            this.myLabel = IRcommand.getFreshFuncLabel(this.container.name + "_" + this.sig.name);
        }
        //update the symbol table to have the new func
        TYPE_FUNCTION funcT;
        if(this.container == null) {
            funcT = (TYPE_FUNCTION) SYMBOL_TABLE.getInstance().find(this.sig.name);
        }else{
            funcT = (TYPE_FUNCTION)container.function_list.findInList(this.sig.name);
        }


        funcT.myLabel = myLabel;

        //print the label
        IR.getInstance().Add_IRcommand(
                new IRcommand_Label(myLabel)
        );


        //main global init
        if(this.isMainClass()) {
            if (AST_PROGRAM.global_count != 0) {
                IR.getInstance().Add_IRcommand(new IRcommand_Jal("GLOBAL_INITS"));
            }
        }

        IR.getInstance().Add_IRcommand(
                new IRcommand_Fun_Prologue(this.numOfVars, this.isMainClass())
        );

        //get the object's variables
        //on $a1 there is the object address
        if(container != null){
            for(int i = 1; i <= container.data_members.getSize(); i++){
                TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
                IR.getInstance().Add_IRcommand(new IRcommand_LoadFromHeap(t,ARGUMENT.getInstance(1),i));
                IR.getInstance().Add_IRcommand(new IRcommand_Store_AddressLocalVar(t,i));
            }
            IR.getInstance().Add_IRcommand(new IRcommand_Move(SAVE_REG.getInstance(7),ARGUMENT.getInstance(1)));

        }

        //IR the arguments
        for (AST_ID_LIST runner = this.sig.idList; runner != null; runner = runner.tail) {
            AST_DEC_VAR local = runner.head;
            if (local == null)
                break;
            local.IRme(); //this line also saves the argument on the FP
        }
        //TODO free memory(?)

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
