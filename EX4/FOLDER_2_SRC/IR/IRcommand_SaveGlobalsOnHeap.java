package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_SaveGlobalsOnHeap  extends IRcommand{
    int count;
    public IRcommand_SaveGlobalsOnHeap(int global_count) {
    count = global_count;
    }

    @Override
    public void MIPSme() {
//        sir_MIPS_a_lot.getInstance().mallocHeap(dst,size);

    }
}
