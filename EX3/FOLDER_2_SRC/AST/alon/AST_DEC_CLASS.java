package AST.alon;

import AST.*;
import Auxillery.Scanners;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class AST_DEC_CLASS extends AST_DEC
{

	public AST_CLASS_SIG sig;
	public AST_CFIELD_LIST cfieldList;
	public AST_FUNC_LIST funcList;
	public AST_VAR_LIST varList;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_CLASS(AST_CLASS_SIG sig, AST_CFIELD_LIST cfieldList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("decClass -> name( %s )\n", this.sig.name);

		right = cfieldList;

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.sig = sig;
		this.cfieldList = cfieldList;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE decClass ( %s )\n",this.sig.name);
		if(this.sig.ext != null) System.out.format("extends %s",this.sig.ext);
		if (cfieldList != null) cfieldList.PrintMe();


		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		if (this.sig.ext != null)
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
					String.format("Class Declaration NAME(%s) EXTENDS", this.sig.name, this.sig.ext));
		else
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
					String.format("Class Declaration NAME(%s)", this.sig.name));
		if (cfieldList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldList.SerialNumber);

	}




	public TYPE SemantMe()
	{
		/*************************/
		/* [1] Begin Class Scope */
<<<<<<< Updated upstream
        /*************************/
        SYMBOL_TABLE.getInstance().beginScope();

        //Sement the sig
        TYPE_CLASS t = sig.SemantMe();
        //Sement the body
        //TODO sement class vars
        //type.data_members = varList.SemantMe();
        //Sement the functions
        t.function_list = funcList.SemantMe();

        /*****************/
=======
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/*******************
		 * ********/
		TYPE_CLASS father = sig.SemantMe(); // if there is a father class, will be returned by SemantMe
		//TODO recursion on the lists
		if(this.varList != null) varList.cSemantMe(sig.name /* passes name of class containing the variables*/);
		if(this.funcList != null) funcList.SemantMe();

		TYPE_CLASS t = new TYPE_CLASS(sig.name,father,null);


		/*****************/
>>>>>>> Stashed changes
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(sig.name,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}

	public String getName(){
		return this.sig.name;
	}

	public String getExt(){
		return this.sig.ext;
	}

	public AST_FUNC_LIST getFuncList() {
		return funcList;
	}

<<<<<<< Updated upstream
    public AST_VAR_LIST getVarList() {
        return varList;
    }
//
//    public boolean varScanner() {
//        return Scanners.classVarInitScanner(this);
//    }
=======
	public AST_VAR_LIST getVarList() {
		return varList;
	}

	public boolean varScanner(){
		return Scanners.classVarInitScanner(this);
	}
>>>>>>> Stashed changes

}
