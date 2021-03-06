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
	/*
	saves the 8 registers on stack (done in function prologue
	 */
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().setRoomOnStack(9); //8 temps
		for(int i = 0; i < 8; i++) {
			sir_MIPS_a_lot.getInstance().saveRegOnStack(TEMP_REG.getInstance(i), i);
		}
		sir_MIPS_a_lot.getInstance().saveRegOnStack(SAVE_REG.getInstance(7), 8);

	}
}
