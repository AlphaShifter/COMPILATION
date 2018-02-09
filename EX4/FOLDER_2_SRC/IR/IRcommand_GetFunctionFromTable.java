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
	String className;

	/*
	stores src on stack, in offset @myPlace from FP (saves on the frame)
	 */
	public IRcommand_GetFunctionFromTable(TEMP dest, String className)
	{
		this.dest = dest;
		this.className = className;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{

		sir_MIPS_a_lot.getInstance().getFuncTable(dest,className);
	}
}
