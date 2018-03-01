package TEMP;

/**
 * Created by giladi on 01/01/2018.
 */
public class V0_REG extends TEMP {

    private static V0_REG instance = null;

    protected V0_REG(int serial) {
        super(serial);
    }

    public static V0_REG getInstance(){
        if(instance == null){
            instance = new V0_REG(-1);
        }
        return instance;
    }


}
