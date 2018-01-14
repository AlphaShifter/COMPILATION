package IR;
import TEMP.*;
import MIPS.*;


/**
 * Created by giladi on 14/01/2018.
 */
public class IRcommand_passArgument extends IRcommand {

    TEMP arg;
    int offset;

    public IRcommand_passArgument(TEMP arg, int offset){
        this.arg = arg;
        this.offset = offset;
    }


    @Override
    public void MIPSme() {

        sir_MIPS_a_lot.getInstance().saveOnHeap(arg,ARGUMENT.getInstance(0),offset);

    }
}
