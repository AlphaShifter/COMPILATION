/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/
/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IRcommand_SaveRegOnStack extends IRcommand
{

	TEMP reg;

	public IRcommand_SaveRegOnStack(TEMP reg){
		this.reg = reg;
	}


	/***************/
	/* MIPS me !!! */
	/***************/
	/*
	saves some reg on the top of the stack
	 */
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().setRoomOnStack(1);
		sir_MIPS_a_lot.getInstance().saveRegOnStack(reg, 0);

	}
}
