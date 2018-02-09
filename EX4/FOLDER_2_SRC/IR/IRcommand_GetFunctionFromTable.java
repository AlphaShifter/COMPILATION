/***********/
/* PACKAGE */
/***********/
package IR;
import TEMP.*;
/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

public class IRcommand_GetFunctionFromTable extends IRcommand
{
	TEMP dest;
	int myPlace;
	String className;
	TEMP t;

	/*
	stores src on stack, in offset @myPlace from FP (saves on the frame)
	 */
	public IRcommand_GetFunctionFromTable(TEMP dest, String className, int myPlace)
	{
		this.dest = dest;
		this.myPlace = myPlace;
		this.className = className;
		this.t = TEMP_FACTORY.getInstance().getFreshTEMP();
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{

		sir_MIPS_a_lot.getInstance().getFuncFromTable(dest,t,className,myPlace);
	}
}
