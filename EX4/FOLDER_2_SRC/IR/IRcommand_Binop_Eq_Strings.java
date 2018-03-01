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
import TEMP.*;

public class IRcommand_Binop_Eq_Strings extends IRcommand {
    public TEMP t1;
    public TEMP t2;
    public TEMP dst;

    public IRcommand_Binop_Eq_Strings(TEMP dst, TEMP t1, TEMP t2) {
        this.dst = dst;
        this.t1 = t1;
        this.t2 = t2;
    }
    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {

        sir_MIPS_a_lot.getInstance().move(ARGUMENT.getInstance(0),t1);
        sir_MIPS_a_lot.getInstance().move(ARGUMENT.getInstance(1),t2);
        sir_MIPS_a_lot.getInstance().jal("strcmp");
        sir_MIPS_a_lot.getInstance().move(dst,V0_REG.getInstance());

    }
}
