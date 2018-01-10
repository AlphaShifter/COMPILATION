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
import TEMP.TEMP_FACTORY;

public class IRcommand_StringCon extends IRcommand
{
	public TEMP s1;
	public TEMP s2;
	public TEMP dst;

	public IRcommand_StringCon(TEMP dst, TEMP s1, TEMP s2)
	{
		this.dst = dst;
		this.s1 = s1;
		this.s2 = s2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//calc the size
		TEMP size1 = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().getStrLen(size1,s1);
		TEMP size2 = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().getStrLen(size2,s2);
		//add
		TEMP totalSize = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().add(totalSize,size1,size2);
		//concate
		sir_MIPS_a_lot.getInstance().strCon(dst,s1,s2,totalSize);
	}
}
