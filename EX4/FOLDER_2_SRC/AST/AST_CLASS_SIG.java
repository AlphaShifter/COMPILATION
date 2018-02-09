package AST;

import Auxillery.Util;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_CLASS;

public class AST_CLASS_SIG extends AST_Node
{

	public String name;
	public String ext;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_CLASS_SIG(String name, String ext)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("decClass -> name( %s )\n", name);


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
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


		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		if (ext != null)
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration NAME(%s) EXTENDS", name,ext));
		else
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration NAME(%s)", name));

	}


	public TYPE_CLASS SemantMe()
	{

		/***************************/
		/* [2] Semant Data Members */
		/*******************
		 * ********/
		TYPE father_temp = null;
		TYPE_CLASS father = null;
		if(ext != null){
			father_temp =  SYMBOL_TABLE.getInstance().find(ext);
			if(father_temp == null){
				System.out.format(">> ERROR [%d,%d] extended class %s has not been defined\n", 2, 2, ext);
				Util.printError(myLine);
			}
			else if(!father_temp.isClass()){
				System.out.format(">> ERROR [%d,%d] extended class %s is not a class Type\n", 2, 2, ext);
				Util.printError(myLine);
			} else {
				father = (TYPE_CLASS)father_temp;
			}

		}
		TYPE_CLASS t = new TYPE_CLASS(name,father,null);
		//get father's vars
		if(father != null){
			t.data_members.addAll(father.data_members);
			t.function_list.addAll(father.function_list);
			t.funcCount = father.funcCount;
		}


		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return t;
	}

	public String getName(){
		return this.name;
	}

	public String getExt(){
		return this.ext;
	}

}
