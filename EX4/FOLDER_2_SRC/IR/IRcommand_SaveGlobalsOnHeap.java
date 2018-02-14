package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_SaveGlobalsOnHeap  extends IRcommand{
    int count;
    public IRcommand_SaveGlobalsOnHeap(int global_count) {
    count = global_count;
    }

    @Override
    public void MIPSme() {

        sir_MIPS_a_lot.getInstance().mallocHeap(GLOBAL_REG.getInstance(),count);

    }
}
