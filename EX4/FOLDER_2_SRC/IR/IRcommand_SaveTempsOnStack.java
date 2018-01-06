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

public class IRcommand_SaveTempsOnStack extends IRcommand
{

	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().setRoomOnStack(8); //8 temps
		for(int i = 0; i < 8; i++) {
			sir_MIPS_a_lot.getInstance().saveRegOnStack(TEMP_REG.getInstance(i), i);
		}
	}
}
