package TEMP;

/**
 * Created by giladi on 01/01/2018.
 */
public class SAVE_REG extends TEMP {

    private static SAVE_REG arg0 = null;
    private static SAVE_REG arg1 = null;
    private static SAVE_REG arg2 = null;
    private static SAVE_REG arg3 = null;

    private int local = -1;

    public int getLocal() {
        return local;
    }

    protected SAVE_REG(int serial) {
        super(serial);
    }

    public static SAVE_REG getInstance(int i){
        switch (i){
            case 0:
                if(arg0 == null){
                    arg0 = new SAVE_REG(-1);
                    arg0.local = 0;
                }
                return arg0;
            case 1:
                if(arg1 == null){
                    arg1 = new SAVE_REG(-1);
                    arg1.local = 1;
                }
                return arg1;
            case 2:
                if(arg2 == null){
                    arg2 = new SAVE_REG(-1);
                    arg2.local = 2;
                }
                return arg2;
            case 3:
                if(arg3 == null){
                    arg3 = new SAVE_REG(-1);
                    arg3.local = 3;

                }
                return arg3;
            default: return null;
        }
    }


}
