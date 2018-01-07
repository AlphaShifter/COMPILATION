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

/*
does second part of epilogue: pops temps from stack and the return value (assumes it has been read)
 */
public class IRcommand_Fun_Epiloge extends IRcommand
{


	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme() {
		sir_MIPS_a_lot.getInstance().setRoomOnStack(-9);
	}
}
