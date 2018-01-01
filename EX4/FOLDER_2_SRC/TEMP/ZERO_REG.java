package TEMP;

/**
 * Created by giladi on 01/01/2018.
 */
public class ZERO_REG extends TEMP {

    private static ZERO_REG instance = null;

    protected ZERO_REG(int serial) {
        super(serial);
    }

    public static ZERO_REG getInstance(){
        if(instance == null){
            instance = new ZERO_REG(-1);
        }
        return instance;
    }


}
