/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;
import TEMP.*;
public class IRcommand_If_Start extends IRcommand
{
	TEMP exp;
	String endLabel;
	public IRcommand_If_Start(TEMP exp, String endLabel)
	{
		this.exp = exp;
		this.endLabel = endLabel;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//if cond == 0, go to the end
		TEMP zero =  TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().li(zero,0);
		//if exp == zero - goto end label
		sir_MIPS_a_lot.getInstance().beq(exp,zero,endLabel);
	}
}
