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

	public IRcommand_Return(TEMP data)
	{
		this.data = data;

	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().closeFrame();
		//restore old FP
		sir_MIPS_a_lot.getInstance().resotreOldFp(0);
		//pop
		sir_MIPS_a_lot.getInstance().setRoomOnStack(-1);
		//restore return location
		sir_MIPS_a_lot.getInstance().restoreRaFromStack(0); //retrieve the return address
		//pop
		sir_MIPS_a_lot.getInstance().setRoomOnStack(-1);
		//save return value
		sir_MIPS_a_lot.getInstance().saveReturnOnStack(0, data);
		//jal
		sir_MIPS_a_lot.getInstance().jr();
	}
}
