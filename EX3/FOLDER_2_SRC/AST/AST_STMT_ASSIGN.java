package AST;

import Auxillery.Util;
import SYMBOL_TABLE.MY_SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_INT;
import TYPES.TYPE_NIL;
import TYPES.TYPE_STRING;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
		left = var;
		right = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN left := right");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}


	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;

		if (var != null) t1 = var.SemantMe();
		if (exp != null) t2 = exp.SemantMe();

		if (t1 != t2)
		{
			System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n",6,6);
		}
		return null;
	}

	//TODO refactor the name and return type
	public boolean PLACEHOLDER_SEMENT_NAME(){
		//TODO Scope
		TYPE t = MY_SYMBOL_TABLE.getInstance().get(var.getName());
		return assignmentChecker(t,this.exp);
	}

	public static boolean assignmentChecker(TYPE t, AST_EXP exp){

		//TODO redsign this
		TYPE expType = exp.getExpType();
		if(expType == null){
			//TODO ERROR
			return false;
		}
		if (t != expType) {
			//we only allow type difference in the following cases:
			//father into son
			//NIL into obj or array
			if (expType == TYPE_NIL.getInstance()) {
				//if we assign NIL into primitive: error
				if (t == TYPE_INT.getInstance() || t == TYPE_STRING.getInstance()) {
					//TODO ERROR
					return false;
				}
			}
			//if expType is a primitive: error
			if (expType == TYPE_INT.getInstance() || expType == TYPE_STRING.getInstance()) {
				//TODO ERROR
				return false;
			}
			//check if expType is father of t
			if (!Util.isFatherOf(t, expType)) {
				//TODO ERROR
				return false;
			}
			//TODO Array assignment
		}
		return true;
	}
}
