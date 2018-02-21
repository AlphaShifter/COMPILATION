package SYMBOL_TABLE;

import TYPES.TYPE;
import TYPES.TYPE_CLASS;

import java.util.*;

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

    public void debugClasses(){
        Set<Map.Entry<String,TYPE>> s = table.entrySet();
        for(Map.Entry<String,TYPE> e: s){
            TYPE type = e.getValue();
            if(type instanceof TYPE_CLASS){
                TYPE_CLASS c = (TYPE_CLASS)type;
                String out = "======Class: " + c.name;
                if(c.father != null){
                    out = out + " Extends " + c.father.name;
                }
                out += "=======";
                //System.out.println(out);
                //System.out.println("***Vars:");
               // c.data_members.print();
            }
        }
    }

}
