package AST.VAR;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.DEC.AST_DEC_CLASS;
import AST.DEC.AST_DEC_FUNC;
import Auxillery.Util;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TEMP.*;
import IR.*;
import MIPS.*;
public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	public int myPlace;
	public boolean isGloabl = false;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
//		System.out.format("var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE VAR(%s)",name));
	}

	@Override
	public boolean isGlobal(){
		return this.isGloabl;
	}

	public TYPE SemantMe()
	{
		if(SYMBOL_TABLE.getInstance().find(name) == null){
			System.out.println(">> ERROR: variable " + name + " has not been defined\n");
			Util.printError(myLine);
		}
		TYPE t = SYMBOL_TABLE.getInstance().find(name);
		if(t.name.equals(this.name)){
			System.out.println("Error: static reference");
			Util.printError(myLine);
		}

		if(!SYMBOL_TABLE.getInstance().isVarGlobal(name)) {


			int freq = AST_DEC_FUNC.funcFreqCount.get(name);
			String newName = name + "_" + freq;

			//check if we are at function
			if (AST_DEC_FUNC.funcLocalVarsCount != null) {
				myPlace = AST_DEC_FUNC.funcLocalVarsCount.get(newName);
			}
//		else if(AST_DEC_CLASS.classLocalVarsCount != null){
//			myPlace = AST_DEC_CLASS.classLocalVarsCount.get(newName);
//		}
		} else {

			myPlace = SYMBOL_TABLE.globalMap.get(name);
			this.isGloabl = true;

		}
		return t;

	}

	@Override
	public String getName() {
		return this.name;
	}

	public TEMP IRme()
	{
		//System.out.println("My place is " + myPlace);


		if(!isGloabl) {

			TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
			TEMP memory = TEMP_FACTORY.getInstance().getFreshTEMP();

			//load from stack (frame) the data
			IR.getInstance().Add_IRcommand(new IRcommand_Load_AddressLocalVar(memory, myPlace));
			//save on temp and return
			IR.getInstance().Add_IRcommand(new IRcommand_Move(
					t,
					memory));

			return t;
		} else {
			TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
			IR.getInstance().Add_IRcommand(new IRcommand_LoadFromHeap(t,GLOBAL_REG.getInstance(),myPlace));
			return t;
		}
	}
}
