import AST.*;
import SYMBOL_TABLE.MY_SYMBOL_TABLE;
import TYPES.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scanners {

    public static boolean arrayDecScanner(AST_PROGRAM root) {
        //run over decs
        for(AST_Node dec: root.decList) {
            if(dec instanceof AST_DEC_ARRAY){
                AST_DEC_ARRAY decArray = (AST_DEC_ARRAY)dec;
                //check the type
                TYPE t = null;
                if(decArray.type.equals("int"))
                    t = TYPE_INT.getInstance();
                else if (decArray.type.equals("string"))
                    t = TYPE_STRING.getInstance();
                else{
                    TYPE classType = MY_SYMBOL_TABLE.getInstance().get(decArray.type);
                    //undefined class type
                    if(classType == null)
                        return false;
                }
                TYPE_ARRAY newType = new TYPE_ARRAY(t,decArray.name);
                MY_SYMBOL_TABLE.getInstance().add(decArray.name, newType);
            }
        }
        return true;
    }

}
