package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_Access_Array extends IRcommand {

    public TEMP dst;
    public TEMP arrayStart;
    public int index;
    public TEMP regIndex;
    boolean hasIndex;

    public IRcommand_Access_Array(TEMP dst, TEMP arrayStart, int index) {
        this.dst = dst;
        this.arrayStart = arrayStart;
        this.index = index;
        hasIndex = true;
    }

    public IRcommand_Access_Array(TEMP dst, TEMP arrayStart, TEMP regIndex) {
        this.dst = dst;
        this.arrayStart = arrayStart;
        this.regIndex = regIndex;
        hasIndex = false;

    }


    public void MIPSme(){
        String label_end = getFreshLabel("end");
        String label_access_violation = getFreshLabel("access_violation");

        TEMP arraySize = TEMP_FACTORY.getInstance().getFreshTEMP();
        sir_MIPS_a_lot.getInstance().LoadFromHeap(arraySize, arrayStart, 0);

        if(hasIndex){
            TEMP indexRegister = TEMP_FACTORY.getInstance().getFreshTEMP();
            sir_MIPS_a_lot.getInstance().li(indexRegister, index);
            // if offset is greater than arraySize jump to proper label
            sir_MIPS_a_lot.getInstance().bgt(indexRegister, arraySize, label_access_violation);
        } else{
            sir_MIPS_a_lot.getInstance().bgt(regIndex, arraySize, label_access_violation);
        }

        if(hasIndex) {
            sir_MIPS_a_lot.getInstance().LoadFromHeap(dst, arrayStart, index+1);
        }else{
            //add the regIndex * word size into the start
            TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();

            sir_MIPS_a_lot.getInstance().li(t, 1);
            sir_MIPS_a_lot.getInstance().add(regIndex,t,regIndex);
            sir_MIPS_a_lot.getInstance().li(t, 4);
            sir_MIPS_a_lot.getInstance().mult(regIndex,t);
            sir_MIPS_a_lot.getInstance().getLo(regIndex);
            sir_MIPS_a_lot.getInstance().add(arrayStart,arrayStart,regIndex);

            sir_MIPS_a_lot.getInstance().LoadFromHeap(dst, arrayStart, 0);
        }
        sir_MIPS_a_lot.getInstance().jump(label_end);

        sir_MIPS_a_lot.getInstance().label(label_access_violation);
        sir_MIPS_a_lot.getInstance().printAccessViolation();

        sir_MIPS_a_lot.getInstance().label(label_end);
    }
}
