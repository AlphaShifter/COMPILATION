/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
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
	public void MIPSme()
	{
		for(int i = 0; i < 8; i++) {
			sir_MIPS_a_lot.getInstance().loadRegfromStack(TEMP_REG.getInstance(i), i+1); //+1 because we havn't loaded the return value
		}
	}
}
