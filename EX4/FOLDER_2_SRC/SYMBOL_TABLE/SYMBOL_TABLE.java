/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/****************/
/* SYMBOL TABLE */

/****************/
public class SYMBOL_TABLE {

    /**********************************************/
    /* The actual symbol table data structure ... */
    /**********************************************/
    private List<Map<String,TYPE>> tableList;
    private int scope_index = 0;
    public static int var_count = 0;


    public void enter(String name, TYPE t) {

        //enter a TYPE to the current scope

        Map<String,TYPE> currScope = tableList.get(scope_index);
        currScope.put(name,t);


        /**************************/
		/* [6] Print Symbol Table */
        /**************************/
        PrintMe();
    }

    /***********************************************/
	/* Find the inner-most scope element with name */

    /***********************************************/
    public TYPE find(String name) {
        //start from current scope, end on the global
        for(int i = scope_index; i >=0; i--){
            Map<String,TYPE> currScope = tableList.get(i);
            if(currScope.containsKey(name))
                return currScope.get(name);

        }

        return null;
    }
    //will look for element with name in the current (inner) scope only
    public TYPE findInCurrScope(String name){
        Map<String,TYPE> currScope = tableList.get(scope_index);
        if(currScope.containsKey(name))
            return currScope.get(name);
        else
            return null;
    }

    //start a new scope by adding new map to the list
    public void beginScope() {

        tableList.add(new HashMap<>());
        scope_index++;
        /*********************************************/
		/* Print the symbol table after every change */
        /*********************************************/
        PrintMe();
    }


    public void endScope() {
        //end the scope by deleting the last map
        tableList.remove(scope_index);
        scope_index--;
        var_count=0;
        /*********************************************/
		/* Print the symbol table after every change */
        /*********************************************/
        PrintMe();
    }


    public void PrintMe() {
       //TODO
        return;
    }

    /**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
    /**************************************/
    private static SYMBOL_TABLE instance = null;


    protected SYMBOL_TABLE() {

        //init the global scope
        this.tableList = new ArrayList<>();
        tableList.add(new HashMap<>());


    }

    /******************************/
	/* GET SINGLETON INSTANCE ... */

    /******************************/
    public static SYMBOL_TABLE getInstance() {
        if (instance == null) {
            /*******************************/
			/* [0] The instance itself ... */
            /*******************************/
            instance = new SYMBOL_TABLE();

            /*****************************************/
			/* [1] Enter primitive types int, string */
            /*****************************************/
            instance.enter("int", TYPE_INT.getInstance());
            instance.enter("string", TYPE_STRING.getInstance());

            /*************************************/
			/* [2] How should we handle void ??? */
            /*************************************/

            /***************************************/
			/* [3] Enter library function PrintInt */
            /***************************************/
            instance.enter(
                    "PrintInt",
                    new TYPE_FUNCTION(
                            TYPE_VOID.getInstance(),
                            "PrintInt",
                            new TYPE_LIST(
                                    TYPE_INT.getInstance(),
                                    null)));

        }
        return instance;
    }
}
