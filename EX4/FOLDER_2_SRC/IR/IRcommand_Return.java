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
	int fpSize;

	public IRcommand_Return(int fpSize, TEMP data)
	{
		this.fpSize = fpSize;
		this.data = data;

	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{

		//same data to the empty space
		sir_MIPS_a_lot.getInstance().saveReturnOnStack(fpSize + 2, data); //fpsize + 2 because +1 is the return address in +2 is the saved empty space
		sir_MIPS_a_lot.getInstance().restoreRaFromStack(fpSize + 1); //retrieve the return address
		//jal
		sir_MIPS_a_lot.getInstance().jal();
	}
}
