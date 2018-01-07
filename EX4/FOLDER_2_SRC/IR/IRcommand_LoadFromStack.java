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

public class IRcommand_LoadFromStack extends IRcommand
{

	TEMP dst;
	int offset;

	/**
	 load word in offset (in words) from stack into dst
	 */
	public IRcommand_LoadFromStack(TEMP dst, int offset)
	{
		this.dst = dst;
		this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().loadRegfromStack(dst,this.offset);
	}
}
