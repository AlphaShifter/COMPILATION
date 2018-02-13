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

public class IRcommand_Store_AddressGlobalVar extends IRcommand
{
    TEMP src;
    int myPlace;

  
    public IRcommand_Store_AddressGlobalVar(TEMP src, int myPlace)
    {
        this.src = src;
        this.myPlace = myPlace;
    }

    /***************/
	/* MIPS me !!! */
    /***************/
    public void MIPSme()
    {
        sir_MIPS_a_lot.getInstance().storeAddressGlobalVar(src,myPlace);
    }
}
