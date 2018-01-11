package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import Auxillery.Util;
import TYPES.*;
import TEMP.*;
import IR.*;
import MIPS.*;
public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP leftExp;
	public AST_EXP rightExp;
	boolean isString = false;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		rightExp = right;
		leftExp = left;
		this.OP = OP;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		String sOP="";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}


		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
/*	Testing equality between two expressions is legal whenever the two have the same type or when one type is  derived from the other.
	 class variable or array variable can be tested for equality with NULL.
	 illegal to compare a string variable to NULL
	resulting type of a semantically valid comparison is the primitive type int*/
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;

		if (leftExp  != null) t1 = leftExp.SemantMe();
		if (rightExp != null) t2 = rightExp.SemantMe();

		if (t1==null){
			Util.printError(leftExp.myLine);
			return null;
		}
		if (t2==null){
			Util.printError(rightExp.myLine);
			return null;
		}
		if(t1.isFunction() || t2.isFunction()){
			System.out.println("Error: invalid arguments in binop");
			Util.printError(this.myLine);
		}
		//while(t1.isArray()) // if t1 is an array of arrays, keep digging for the underlying type
		while(t1.isArray() && t2.isArray()) { // if t2 is an array of arrays, keep digging for the underlying type
			t2 = ((TYPE_ARRAY) t2).type;
			t1 = ((TYPE_ARRAY)t1).type;

		}

		if(t1 == t2){
            if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
            {
                return TYPE_INT.getInstance();
            }
            if(t1 == TYPE_STRING.getInstance() && t2 == TYPE_STRING.getInstance() && OP == 0) {
            	this.isString = true;
				return TYPE_STRING.getInstance();
			}if(OP == 6){
            	return TYPE_INT.getInstance();
			}
        }
        else if(OP==6){
			if(t1.isArray() && t2.isArray()){
				t1 = ((TYPE_ARRAY)t1).type;
				t2 = ((TYPE_ARRAY)t2).type;
			}
			if(t1 == TYPE_NIL.getInstance() && t2 == TYPE_NIL.getInstance()){
				return TYPE_INT.getInstance();
			}
            if(TYPE.eqByString(t1,"TYPE_NIL")){//they are not of the same type
                //check if one of them is null and the other is either a class or an array
                if(TYPE.eqByString(t2,"TYPE_ARRAY")||TYPE.eqByString(t2,"TYPE_CLASS")){
                    return TYPE_INT.getInstance();
                }
            }
            else if(TYPE.eqByString(t2,"TYPE_NIL")){
                if(TYPE.eqByString(t1,"TYPE_ARRAY")||TYPE.eqByString(t1,"TYPE_CLASS")){
                    return TYPE_INT.getInstance();
                }
            }
            else if (Util.areRelated(t1,t2)){
            	return TYPE_INT.getInstance();
            }

        }
		System.out.println("Error: invalid parameters for binop");
		Util.printError(this.myLine);
		return null;
	}


	public TEMP IRme() {
		TEMP t1 = null;
		TEMP t2 = null;
		TEMP dst = TEMP_FACTORY.getInstance().getFreshTEMP();

		assert (false);

		if (left != null) t1 = left.IRme();
		if (right != null) t2 = right.IRme();

		//TODO implement stuff other than integers
		switch (OP) {
			case 0:
				if(!isString)
					IR.getInstance().Add_IRcommand(new IRcommand_Binop_Add_Integers(dst, t1, t2));
				else
					IR.getInstance().Add_IRcommand(new IRcommand_StringCon(dst,t1,t2));
				break;
			case 1:
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Sub_Integers(dst, t1, t2));
				break;
			case 2:
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Mult_Integers(dst, t1, t2));
				break;
			case 3:
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Div_Integers(dst, t1, t2));
				break;
			case 4:
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_LT_Integers(dst, t1, t2));
				break;
			case 5:
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_GT_Integers(dst, t1, t2));
				break;
			case 6:
				IR.getInstance().Add_IRcommand(new IRcommand_Binop_Eq_Integers(dst, t1, t2));
				break;
			default:
				break;
		}

		return dst;
	}
}
