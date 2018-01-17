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
import TEMP.*;

public class IRcommand_CheckPointerAccess extends IRcommand
{
	public TEMP t1;
	public String label;

	public IRcommand_CheckPointerAccess(TEMP t1,String label)
	{
		this.t1 = t1;
		this.label = label;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().bne(t1, ZERO_REG.getInstance(),label);
		sir_MIPS_a_lot.getInstance().printIllegalPointer();

	}
}
