package IR;
import TEMP.*;

import java.util.ArrayList;
import java.util.List;

public class IRcommand_func_dec extends IRcommand{

    List<TEMP>argsList;
    String label;

    public IRcommand_func_dec(String label, List<TEMP> argsList){
        this.label = label;
        this.argsList = argsList;
    }

    @Override
    public void MIPSme() {



    }
}
