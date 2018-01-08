package TEMP;


/**
 * Created by giladi on 01/01/2018.
 */
public class TEMP_REG extends TEMP {

    private static TEMP_REG arg0 = null;
    private static TEMP_REG arg1 = null;
    private static TEMP_REG arg2 = null;
    private static TEMP_REG arg3 = null;
    private static TEMP_REG arg4= null;
    private static TEMP_REG arg5 = null;
    private static TEMP_REG arg6 = null;
    private static TEMP_REG arg7 = null;


    private int local = -1;

    public int getLocal() {
        return local;
    }

    protected TEMP_REG(int serial) {
        super(serial);
    }

    public static TEMP_REG getInstance(int i){
        switch (i){
            case 0:
                if(arg0 == null){
                    arg0 = new TEMP_REG(-1);
                    arg0.local = 0;
                }
                return arg0;
            case 1:
                if(arg1 == null){
                    arg1 = new TEMP_REG(-1);
                    arg1.local = 1;
                }
                return arg1;
            case 2:
                if(arg2 == null){
                    arg2 = new TEMP_REG(-1);
                    arg2.local = 2;
                }
                return arg2;
            case 3:
                if(arg3 == null){
                    arg3 = new TEMP_REG(-1);
                    arg3.local = 3;

                }
                return arg3;
            case 4:
                if(arg4 == null){
                    arg4 = new TEMP_REG(-1);
                    arg4.local = 4;

                }
                return arg4;
            case 5:
                if(arg5 == null){
                    arg5 = new TEMP_REG(-1);
                    arg5.local = 5;

                }
                return arg5;
            case 6:
                if(arg6 == null){
                    arg6 = new TEMP_REG(-1);
                    arg6.local = 6;

                }
                return arg6;
            case 7:
                if(arg7 == null){
                    arg7 = new TEMP_REG(-1);
                    arg7.local = 7;

                }
                return arg7;
            default: return null;
        }
    }


}
