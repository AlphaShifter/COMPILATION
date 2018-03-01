package TEMP;

/**
 * Created by giladi on 01/01/2018.
 */
public class RA_REG extends TEMP {

    private static RA_REG instance = null;

    protected RA_REG(int serial) {
        super(serial);
    }

    public static RA_REG getInstance(){
        if(instance == null){
            instance = new RA_REG(-1);
        }
        return instance;
    }


}
