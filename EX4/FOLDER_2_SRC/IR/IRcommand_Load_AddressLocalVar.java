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

public class IRcommand_Load_AddressLocalVar extends IRcommand
{
	TEMP dst;
	int myPlace;

	/**
	Load data in offset @myplace from the current frame pointer into dst
	 offset counted in words (it's numeric place)
	 */
	public IRcommand_Load_AddressLocalVar(TEMP dst, int myPlace)
	{
		this.dst = dst;
		this.myPlace = myPlace;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().loadAddressLocalVar(dst,myPlace);
	}
}
