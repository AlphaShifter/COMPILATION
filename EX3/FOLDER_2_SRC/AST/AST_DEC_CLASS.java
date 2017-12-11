package AST;

import Auxillery.Scanners;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_DEC_CLASS extends AST_DEC
{

	String name;
	String ext;
	public AST_CFIELD_LIST cfieldList;
	public AST_FUNC_LIST funcList;
	public AST_VAR_LIST varList;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_CLASS(String name, String ext, AST_CFIELD_LIST cfieldList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("decClass -> name( %s )\n", name);

		right = cfieldList;

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.cfieldList = cfieldList;
		this.ext = ext;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE decClass ( %s )\n",name);
		if(ext != null) System.out.format("extends %s",ext);
		if (cfieldList != null) cfieldList.PrintMe();


		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		if (ext != null)
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration NAME(%s) EXTENDS", name,ext));
		else
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration NAME(%s)", name));
		if (cfieldList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldList.SerialNumber);

	}




	public TYPE SemantMe()
	{
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/*******************
		 * ********/
		//TODO look for a father
		TYPE_CLASS t = new TYPE_CLASS(name,null, cfieldList.SemantMe());

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		//TODO recursion on the lists
		return null;
	}

	public String getName(){
		return this.name;
	}

	public String getExt(){
		return this.ext;
	}

	public AST_FUNC_LIST getFuncList() {
		return funcList;
	}

	public AST_VAR_LIST getVarList() {
		return varList;
	}

	public boolean varScanner(){
		return Scanners.classVarInitScanner(this);
	}

}
