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

public class IRcommand_Return extends IRcommand
{
	TEMP data;


	/**
	 * returns from the function and does the epilogue
	 * @param data to return, ZERO_REG if its void or null
	 */
	public IRcommand_Return(TEMP data)
	{
		this.data = data;

	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//close func's frame
		sir_MIPS_a_lot.getInstance().closeFrame();
		//restore old FP
		sir_MIPS_a_lot.getInstance().resotreOldFp(0);
		//pop
		sir_MIPS_a_lot.getInstance().setRoomOnStack(-1);
		//restore return location
		sir_MIPS_a_lot.getInstance().restoreRaFromStack(0); //retrieve the return address
		//pop
		sir_MIPS_a_lot.getInstance().setRoomOnStack(-1);
		//save return value to stack
		sir_MIPS_a_lot.getInstance().saveReturnOnStack(0, data);
		//jump back
		sir_MIPS_a_lot.getInstance().jr();
	}
}
