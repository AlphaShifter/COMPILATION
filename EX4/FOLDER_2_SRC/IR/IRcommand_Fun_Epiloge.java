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

public class IRcommand_Fun_Epiloge extends IRcommand
{


	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().setRoomOnStack(-9);
	}
}
