package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_Access_Array extends IRcommand {

    public TEMP dst;
    public TEMP arrayStart;
    public int index;

    public IRcommand_Access_Array(TEMP dst, TEMP arrayStart, int index) {
        this.dst = dst;
        this.arrayStart = arrayStart;
        this.index = index;
    }

    public void MIPSme(){

        String label_end         = getFreshLabel("end");
        String label_access_violation    = getFreshLabel("access_violation");

        TEMP indexRegister = TEMP_FACTORY.getInstance().getFreshTEMP();
        sir_MIPS_a_lot.getInstance().li(indexRegister, index);

        TEMP arraySize = TEMP_FACTORY.getInstance().getFreshTEMP();
        sir_MIPS_a_lot.getInstance().LoadFromHeap(arraySize, arrayStart, 0);

        // if offset is greater than arraySize jump to proper label
        sir_MIPS_a_lot.getInstance().bgt(indexRegister, arraySize, label_access_violation);
        sir_MIPS_a_lot.getInstance().LoadFromHeap(dst, arrayStart, index);
        sir_MIPS_a_lot.getInstance().jump(label_end);

        sir_MIPS_a_lot.getInstance().label(label_access_violation);
        sir_MIPS_a_lot.getInstance().printAccessViolation();

        sir_MIPS_a_lot.getInstance().label(label_end);
    }
}
