package AST.STMT;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.EXP.AST_EXP_ID;
import AST.EXP.AST_EXP_INT;
import AST.VAR.AST_VAR;
import AST.EXP.AST_EXP;
import AST.VAR.AST_VAR_FIELD;
import AST.VAR.AST_VAR_SIMPLE;
import AST.VAR.AST_VAR_SUBSCRIPT;
import Auxillery.Util;
import IR.*;
import TEMP.*;
import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;
	public static boolean isAssign = false;

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
//		System.out.print("stmt -> var ASSIGN exp SEMICOLON\n");

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
//		System.out.print("AST NODE ASSIGN STMT\n");

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

		if(!assignmentChecker(t1,t2))
		{
			System.out.format(">> ERROR [%d:%d] type mismatch for var := exp\n",6,6);
			Util.printError(var.myLine);
		}
		return null;
	}

	public TEMP IRme(){
		isAssign = true;
		TEMP t1 = var.IRme();
		isAssign = false;
		TEMP t2 = exp.IRme();
//		TEMP address = TEMP_FACTORY.getInstance().getFreshTEMP();
//		int arraySize;
//		IR.getInstance().Add_IRcommand(
//				new IRcommand_Move(t1,t2)
//		);
		if(var instanceof AST_VAR_SIMPLE) {
			//stores t2 on the stack, to the location of var (update var)
			if(!var.isGlobal())
				IR.getInstance().Add_IRcommand(new IRcommand_Store_AddressLocalVar(t2, ((AST_VAR_SIMPLE) var).myPlace));
			else
				IR.getInstance().Add_IRcommand(new IRcommand_SaveOnHeap(t2,GLOBAL_REG.getInstance(),((AST_VAR_SIMPLE) var).myPlace));
		}
		else if(var instanceof AST_VAR_FIELD){
			IR.getInstance().Add_IRcommand(new IRcommand_SaveOnHeap(t2,((AST_VAR_FIELD)var).holder,((AST_VAR_FIELD)var).myPlace));
		} else if (var instanceof AST_VAR_SUBSCRIPT){
			AST_VAR_SUBSCRIPT nVar = (AST_VAR_SUBSCRIPT)var;
			if(nVar.subscript instanceof AST_EXP_INT){
				IR.getInstance().Add_IRcommand(new IRcommand_SaveOnHeap(t2,nVar.holder,((AST_EXP_INT)nVar.subscript).value+1));
			} else {

				IR.getInstance().Add_IRcommand(new IRcommand_SaveOnHeap(t2,nVar.holder,0));

			}
		}


		return t1;
	}

	//t1 := t2
	public static boolean assignmentChecker(TYPE t1, TYPE t2){
		if(t1 == TYPE_NIL.getInstance())
			return false;

//		if(t1.isArray())
//			t1 = ((TYPE_ARRAY)t1).type;

		if (t1 != t2) {
			//we only allow type difference in the following cases:
			//father into son
			//NIL into obj or array
			//case 1: t1 is and array
			if(t1.isArray()){
				TYPE_ARRAY t1Arr = (TYPE_ARRAY)t1;
//				TYPE t1ArrType = t1Arr.type;
//				while(t1ArrType.isArray()) {
//					t1ArrType = ((TYPE_ARRAY)t1ArrType).type;
//				}
				if(t2 == TYPE_NIL.getInstance()) //nil into array
					return true;
				if(t2 instanceof TYPE_ARRAY){ // new array
					if(t1Arr.type == ((TYPE_ARRAY) t2).type)
						return true;
				}

				return false;
//				if(t1ArrType == t2)
//					return true;
//				if(t1ArrType.isClass() && t2.isClass()){
//					if(Util.isFatherOf(t2,t1ArrType))
//						return true;
//				}
			}
			else if (t2 == TYPE_NIL.getInstance()) {
				//if we assign NIL into primitive: error
				if (t1 != TYPE_INT.getInstance() && t1 != TYPE_STRING.getInstance()) { //nil into object
					return true;
				}
			}
//			if(t2.isArray()){
//				if(t1 == ((TYPE_ARRAY)t2).type)
//					return true;
//			}
			//check if t2 is father of t1
			else if (Util.isFatherOf(t2, t1)) {
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
