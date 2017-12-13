package AST.DEC;

import AST.EXP.AST_EXP;
import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;

public class AST_DEC_VAR extends AST_DEC
{

	public String type;
	public String name;
	public AST_EXP exp;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_VAR(String type, String name, AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(exp != null)
			System.out.format(" decVar -> TYPE( %s ) NAME(%s) ASSIGN EXP SEMICOLON\n", type,name);
		else
			System.out.format(" decVar -> TYPE( %s ) NAME(%s)SEMICOLON\n", type,name);


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
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE decVar ( %s ) (%s)\n",type, name);
		if (exp != null) exp.PrintMe();

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Variable Deceleration: TYPE(%s) NAME(%s)", type, name));
		if (exp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe()
	{
		TYPE t;

		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(type);
				if (t == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,type);
			System.exit(0);
		}

		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().find(name) != null)
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,name);
		}

		/***************************************************/
		/* [3] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}

//    public boolean varScanner() {
//        TYPE t = Util.stringToType(this.type);
//        //TODO Scope
//        if (t == null)
//            return false;
//
//        //check if deceleration is no empty
//        if (this.exp != null) {
//            //deceleration is not empty - check the assignment
//            if(!AST_STMT_ASSIGN.assignmentChecker(t,this.exp))
//                return false; //error is embedded in the checker
//        }
//        MY_SYMBOL_TABLE.getInstance().add(this.name, t);
//        return true;
//    }
}
