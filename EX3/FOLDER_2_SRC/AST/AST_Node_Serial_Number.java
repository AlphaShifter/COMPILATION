package AST;

public class AST_Node_Serial_Number {
    /*******************************************/
    /* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
    /**************************************/
    private static AST_Node_Serial_Number instance = null;

    /**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
    /*******************************************/
    public int SerialNumber;

    /*****************************/
	/* PREVENT INSTANTIATION ... */

    /*****************************/
    protected AST_Node_Serial_Number() {
    }

    /******************************/
	/* GET SINGLETON INSTANCE ... */

    /******************************/
    private static AST_Node_Serial_Number getInstance() {
        if (instance == null) {
            instance = new AST_Node_Serial_Number();
            instance.SerialNumber = 0;

        }
        return instance;
    }

    /**********************************/
	/* GET A UNIQUE SERIAL NUMBER ... */

    /**********************************/
    public static int getFresh() {
        return AST_Node_Serial_Number.getInstance().get();
    }

    /**********************************/
	/* GET A UNIQUE SERIAL NUMBER ... */

    /**********************************/
    public int get() {
        return SerialNumber++;
    }
}
