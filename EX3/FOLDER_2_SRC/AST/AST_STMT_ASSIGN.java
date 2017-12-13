package AST;

import Auxillery.Util;
import TYPES.*;

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

		if(t1 == null || t2 == null)
			return null;

		if(assignmentChecker(t1,t2))
		{
			System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n",6,6);
			Util.printError(this.myLine);
		}
		return null;
	}

	//t1 := t2
	public static boolean assignmentChecker(TYPE t1, TYPE t2){
		if(t1 == TYPE_NIL.getInstance())
			return false;

		if (t1 != t2) {
			//we only allow type difference in the following cases:
			//father into son
			//NIL into obj or array
			//case 1: t1 is and array
			if(t1 instanceof TYPE_ARRAY){
				TYPE_ARRAY t1Arr = (TYPE_ARRAY)t1;
				if(t2 == TYPE_NIL.getInstance()) //nil into array
					return true;
				if(t2 instanceof TYPE_ARRAY){ // new array
					if(t1Arr.type == ((TYPE_ARRAY) t2).type)
						return true;
				}
			}
			else if (t2 == TYPE_NIL.getInstance()) {
				//if we assign NIL into primitive: error
				if (t1 != TYPE_INT.getInstance() && t1 != TYPE_STRING.getInstance()) { //nil into object
					return true;
				}
			}
			//check if t2 is father of t1
			else if (Util.isFatherOf(t1, t2)) {
				return true;
			} else {
				//none of the above: return false
				return false;
			}
		}
		//else: t1==t2 -> return true
		return true;
	}
}
