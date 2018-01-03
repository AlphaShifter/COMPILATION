package TEMP;

import TEMP.*;
/**
 * Created by giladi on 01/01/2018.
 */
public class ARGUMENT extends TEMP {

    private static ARGUMENT arg0 = null;
    private static ARGUMENT arg1 = null;
    private static ARGUMENT arg2 = null;
    private static ARGUMENT arg3 = null;

    private int local = -1;

    public int getLocal() {
        return local;
    }

    protected ARGUMENT(int serial) {
        super(serial);
    }

    public static ARGUMENT getInstance(int i){
        switch (i){
            case 0:
                if(arg0 == null){
                    arg0 = new ARGUMENT(-1);
                    arg0.local = 0;
                }
                return arg0;
            case 1:
                if(arg1 == null){
                    arg1 = new ARGUMENT(-1);
                    arg1.local = 1;
                }
                return arg1;
            case 2:
                if(arg2 == null){
                    arg2 = new ARGUMENT(-1);
                    arg2.local = 2;
                }
                return arg2;
            case 3:
                if(arg3 == null){
                    arg3 = new ARGUMENT(-1);
                    arg3.local = 3;

                }
                return arg3;
            default: return null;
        }
    }


}
