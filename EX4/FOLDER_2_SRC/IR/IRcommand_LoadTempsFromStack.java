/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.SAVE_REG;
import TEMP.TEMP_REG;

/*******************/
/* GENERAL IMPORTS */
/*******************/
/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IRcommand_LoadTempsFromStack extends IRcommand
{

	/***************/
	/* MIPS me !!! */
	/***************/
	/**
	 * load the 8 temps registers from stack
	 */
	public void MIPSme()
	{
		for(int i = 0; i < 8; i++) {
			sir_MIPS_a_lot.getInstance().loadRegfromStack(TEMP_REG.getInstance(i), i+1); //+1 because we havn't loaded the return value
		}
		sir_MIPS_a_lot.getInstance().loadRegfromStack(SAVE_REG.getInstance(7), 9); //+1 because we havn't loaded the return value

	}
}
