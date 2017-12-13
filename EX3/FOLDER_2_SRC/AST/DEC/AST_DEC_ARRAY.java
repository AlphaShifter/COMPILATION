package AST.DEC;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import AST.DEC.AST_DEC;
import Auxillery.Util;
import SYMBOL_TABLE.MY_SYMBOL_TABLE;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_INT;
import TYPES.TYPE_STRING;

public class AST_DEC_ARRAY extends AST_DEC
{

	public String name;
	public String type;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_ARRAY(String name, String type)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("arrayDec ->  ARRAY ID( %s ) EQ ID(%s)\n", name,type);



		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.type = type;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE ARRAY ID( %s ) EQ ID(%s)", name,type);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Array Declaration NAME1(%s) NAME2(%s)", name, type));


	}

	@Override
	public TYPE_ARRAY SemantMe(){
		TYPE t = Util.stringToType(this.type);
		if(t == null){
			Util.printError(this.myLine);
			return null;
		}
		TYPE_ARRAY newType = new TYPE_ARRAY(t, this.name);
		SYMBOL_TABLE.getInstance().enter(this.name,newType);
		return newType;
	}

}
