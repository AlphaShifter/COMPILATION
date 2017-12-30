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

public class IRcommand_While extends IRcommand
{
	TEMP exp;
	String statLabel;

	public IRcommand_While(TEMP exp, String statLabel)
	{
		this.exp = exp;
		this.statLabel = statLabel;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{

		//if cond >= 1, go to the startLable
		TEMP one =  TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().li(one,1);
		//if exp >= 1 - goto start label
		sir_MIPS_a_lot.getInstance().bge(exp,one,statLabel);
	}
}
