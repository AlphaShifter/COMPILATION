/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/
/*******************/
/* PROJECT IMPORTS */
/*******************/

public class IRcommand_SaveOnHeap extends IRcommand
{


	TEMP src;
	TEMP address;
	int offset;
	TEMP rOffset;

	public IRcommand_SaveOnHeap(TEMP src, TEMP address, int offset){

		this.src = src;
		this.address = address;
		this.offset = offset;
		this.rOffset = null;
	}

	public IRcommand_SaveOnHeap(TEMP src, TEMP address, TEMP rOffset){

		this.src = src;
		this.address = address;
		this.rOffset = rOffset;

	}

	public void MIPSme()
	{

		if(rOffset == null)
			sir_MIPS_a_lot.getInstance().saveOnHeap(src,address,offset);
		else{
//			TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
//			sir_MIPS_a_lot.getInstance().li(t,1);
//			sir_MIPS_a_lot.getInstance().add(rOffset,rOffset,t);
//			sir_MIPS_a_lot.getInstance().li(t,4);
//			sir_MIPS_a_lot.getInstance().mult(rOffset,t);
//			sir_MIPS_a_lot.getInstance().getLo(rOffset);
			sir_MIPS_a_lot.getInstance().add(address,rOffset,address);
			sir_MIPS_a_lot.getInstance().saveOnHeap(src,address,0);
		}
	}
}
