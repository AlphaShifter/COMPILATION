package SYMBOL_TABLE;

import TYPES.*;

import java.util.ArrayList;
import java.util.List;

public class SYMBOL_TABLE {
    /**********************************************/
    /* The actual symbol table data structure ... */
    /**********************************************/
    private List<SYMBOL_TABLE_ENTRY> table = new ArrayList<>();
    private SYMBOL_TABLE_ENTRY top;

    /**************************************************************/
	/* A very primitive hash function for exposition purposes ... */

    /**************************************************************/
    private int hash(String s) {
        if (s.charAt(0) == 'a') {
            return 2;
        }
        if (s.charAt(0) == 'b') {
            return 3;
        }
        if (s.charAt(0) == 'c') {
            return 8;
        }
        return 6;
    }

    /****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */

    /****************************************************************************/
    public void enter(String name, TYPE t) {
        /*************************************************/
		/* [1] Compute the hash type for this new entry */
        /*************************************************/
        int hashValue = hash(name);

        /******************************************************************************/
		/* [2] Extract what will eventually be the next entry in the hashed position  */
		/*     NOTE: this entry can very well be null, but the behaviour is identical */
        /******************************************************************************/
        SYMBOL_TABLE_ENTRY next = table.get(hashValue);

        /**************************************************************************/
		/* [3] Prepare a new symbol table entry with name, type, next and prevtop */
        /**************************************************************************/
        SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name, t, next, top);

        /**********************************************/
		/* [4] Update the top of the symbol table ... */
        /**********************************************/
        top = e;

        /****************************************/
		/* [5] Enter the new entry to the table */
        /****************************************/
        table.set(hashValue,e);
    }

    /***********************************************/
	/* Find the inner-most scope element with name */

    /***********************************************/
    public TYPE find(String name) {
        SYMBOL_TABLE_ENTRY e = table.get(hash(name));

        while (e.name != name) {
            e = e.next;
        }

        return e.type;
    }

    /***************************************************************************/
	/* begine scope = Enter the <SCOPE-BOUNDARY> element to the data structure */

    /***************************************************************************/
    public void begin_scope() {
        enter("<SCOPE-BOUNDARY>", null);
    }

    /********************************************************************************/
	/* end scope = Keep popping elements out of the data structure,                 */
	/* from most recent element entered, until a <NEW-SCOPE> element is encountered */

    /********************************************************************************/
    public void end_scope() {
        SYMBOL_TABLE_ENTRY last = top;

        while (last.name != "<SCOPE-BOUNDARY>") {
            last = last.prev;

        }
    }

    /**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
    /**************************************/
    private static SYMBOL_TABLE instance = null;

    /*****************************/
	/* PREVENT INSTANTIATION ... */

    /*****************************/
    protected SYMBOL_TABLE() {
    }

    /******************************/
	/* GET SINGLETON INSTANCE ... */

    /******************************/
    public static SYMBOL_TABLE getInstance() {
        if (instance == null) {
            instance = new SYMBOL_TABLE();

        }
        return instance;
    }
}
