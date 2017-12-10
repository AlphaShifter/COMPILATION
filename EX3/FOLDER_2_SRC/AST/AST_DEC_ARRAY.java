package AST;

import SYMBOL_TABLE.MY_SYMBOL_TABLE;
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
	public boolean arrayScan(){
		TYPE t;
		if (this.type.equals("int"))
			t = TYPE_INT.getInstance();
		else if (this.type.equals("string"))
			t = TYPE_STRING.getInstance();
		else {
			//TODO Scope
			t = MY_SYMBOL_TABLE.getInstance().get(this.type);
			//undefined class type
			if (t == null)
				return false;
		}
		TYPE_ARRAY newType = new TYPE_ARRAY(t, this.name);
		MY_SYMBOL_TABLE.getInstance().add(this.name, newType);
		return true;
	}
}
