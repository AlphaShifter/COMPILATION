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

public class IRcommand_SaveOnHeap extends IRcommand
{


	TEMP src;
	TEMP address;
	int offset;

	public IRcommand_SaveOnHeap(TEMP src, TEMP address, int offset){

		this.src = src;
		this.address = address;
		this.offset = offset;
	}

	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().saveOnHeap(src,address,offset);
	}
}
