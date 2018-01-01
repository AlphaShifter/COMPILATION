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
import TEMP.*;
public class IRcommand_While extends IRcommand
{
	TEMP exp;
	String statLabel;

	public IRcommand_While(TEMP exp, String statLabel)
	{
		this.exp = exp;
		this.statLabel = statLabel;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{

		//if exp != 0 - goto start label
		sir_MIPS_a_lot.getInstance().bne(exp, ZERO_REG.getInstance(),statLabel);
	}
}
