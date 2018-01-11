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
import TEMP.TEMP;

public class IRcommand_mallocHeap extends IRcommand
{
	TEMP dst;
	int size;

	public IRcommand_mallocHeap(TEMP dst, int s)

	{
		this.dst = dst;
		this.size = s;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().mallocHeap(dst,size);

	}
}
