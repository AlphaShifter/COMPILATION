/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

/*******************/
/* GENERAL IMPORTS */
/*******************/
/*******************/
/* PROJECT IMPORTS */

/*******************/

public class IRcommand_LoadFromHeap extends IRcommand
{


	TEMP dest;
	TEMP address;
	int offset;

	public IRcommand_LoadFromHeap(TEMP dest, TEMP address, int offset){

		this.dest = dest;
		this.address = address;
		this.offset = offset;
	}

	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().LoadFromHeap(dest,address,offset);
	}
}
