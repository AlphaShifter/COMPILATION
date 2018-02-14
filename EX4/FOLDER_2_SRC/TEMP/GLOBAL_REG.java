package TEMP;

/**
 * Created by giladi on 01/01/2018.
 */
public class GLOBAL_REG extends TEMP {

    private static GLOBAL_REG instance = null;

    protected GLOBAL_REG(int serial) {
        super(serial);
    }

    public static GLOBAL_REG getInstance(){
        if(instance == null){
            instance = new GLOBAL_REG(-1);
        }
        return instance;
    }


}
