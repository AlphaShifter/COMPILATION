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

		//if exp == zero - goto end label
		sir_MIPS_a_lot.getInstance().beq(exp,ZERO_REG.getInstance(),endLabel);
	}
}
