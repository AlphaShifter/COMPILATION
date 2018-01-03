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

public class IRcommand_Fun_Prologue extends IRcommand
{

	public int numOfVars;
	public boolean isMain;

	public IRcommand_Fun_Prologue(int numOfVars, boolean isMain)
	{
		this.numOfVars = numOfVars;
		this.isMain = isMain;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if(!isMain) {
			//save 2 space on stack
			sir_MIPS_a_lot.getInstance().setRoomOnStack(2);
			//fill one of the, with RA
			sir_MIPS_a_lot.getInstance().saveRaOnStack();
		}
		//open the frame
		sir_MIPS_a_lot.getInstance().openNewFP(numOfVars);

	}
}
