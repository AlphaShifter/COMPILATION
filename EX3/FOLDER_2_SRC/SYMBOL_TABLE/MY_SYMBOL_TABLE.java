package SYMBOL_TABLE;

import TYPES.TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MY_SYMBOL_TABLE {

    private HashMap<String, TYPE> table = new HashMap<String, TYPE>();
    private static MY_SYMBOL_TABLE instance = null;
    /*****************************/
    /* PREVENT INSTANTIATION ... */

    /*****************************/
    protected MY_SYMBOL_TABLE() {
    }
    /******************************/
    /* GET SINGLETON INSTANCE ... */
    /******************************/
    public static MY_SYMBOL_TABLE getInstance() {
        if (instance == null) {
            instance = new MY_SYMBOL_TABLE();

        }
        return instance;
    }

    public void add(String name, TYPE t) {
        table.put(name,t);
    }


    public TYPE get(String name) {
        return table.get(name);
    }



}
