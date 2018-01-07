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

public class IRcommand_Store_AddressLocalVar extends IRcommand
{
	TEMP src;
	int myPlace;

	/*
	stores src on stack, in offset @myPlace from FP (saves on the frame)
	 */
	public IRcommand_Store_AddressLocalVar(TEMP src, int myPlace)
	{
		this.src = src;
		this.myPlace = myPlace;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().storeAddressLocalVar(src,myPlace);
	}
}
