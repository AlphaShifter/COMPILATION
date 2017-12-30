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

public class IRcommand_Binop_Eq_Integers extends IRcommand {
    public TEMP t1;
    public TEMP t2;
    public TEMP dst;

    public IRcommand_Binop_Eq_Integers(TEMP dst, TEMP t1, TEMP t2) {
        this.dst = dst;
        this.t1 = t1;
        this.t2 = t2;
    }
    /***************/
    /* MIPS me !!! */

    /***************/
    public void MIPSme() {
        //if t1 = t2 than dest := 1
        //else dest := 0
        String eqLabel = getFreshLabel("equal");
        String neqLAbel = getFreshLabel("not_equal");
        String label_end = getFreshLabel("end");


        sir_MIPS_a_lot.getInstance().beq(t1, t2, eqLabel);
        sir_MIPS_a_lot.getInstance().bne(t1, t2, neqLAbel);

        //they are equal
        sir_MIPS_a_lot.getInstance().label(eqLabel);
        sir_MIPS_a_lot.getInstance().li(dst,1);
        sir_MIPS_a_lot.getInstance().jump(label_end);
        //they are not equal
        sir_MIPS_a_lot.getInstance().label(neqLAbel);
        sir_MIPS_a_lot.getInstance().li(dst,0);
        sir_MIPS_a_lot.getInstance().jump(label_end);



        sir_MIPS_a_lot.getInstance().label(label_end);
    }
}
