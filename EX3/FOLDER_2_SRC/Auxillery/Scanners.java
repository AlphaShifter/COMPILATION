package Auxillery;

import AST.*;
import SYMBOL_TABLE.MY_SYMBOL_TABLE;
import TYPES.*;

public class Scanners {

    public static boolean arrayDecScanner(AST_PROGRAM root) {
        //run over decs
        for (AST_Node dec : root.decList) {
            if (dec instanceof AST_DEC_ARRAY) {
                AST_DEC_ARRAY decArray = (AST_DEC_ARRAY) dec;
                //check the type
                TYPE t = null;
                if (decArray.type.equals("int"))
                    t = TYPE_INT.getInstance();
                else if (decArray.type.equals("string"))
                    t = TYPE_STRING.getInstance();
                else {
                    TYPE classType = MY_SYMBOL_TABLE.getInstance().get(decArray.type);
                    //undefined class type
                    if (classType == null)
                        return false;
                }
                TYPE_ARRAY newType = new TYPE_ARRAY(t, decArray.name);
                MY_SYMBOL_TABLE.getInstance().add(decArray.name, newType);
            }
        }
        return true;
    }

    public static boolean expSubscriptChecker(AST_PROGRAM root) {
        boolean res = true;
        for (AST_Node dec : root.decList) {
            //check that all nodes are OK
            res = (res && expSubscriptChecker_rec(dec));
        }
        return res;
    }

    //node is OK iff it is not and subscript node
    //OR it is subscript node and it's expression type is an integer
    private static boolean expSubscriptChecker_rec(AST_Node node) {
        if (node == null) return true;
        boolean left;
        boolean right;
        boolean res = true;
        if (node instanceof AST_VAR_SUBSCRIPT) {
            AST_VAR_SUBSCRIPT as = (AST_VAR_SUBSCRIPT) node;
            res = (as.subscript.getExpType() == TYPE_INT.getInstance());
        }
        left = expSubscriptChecker_rec(node.left);
        right = expSubscriptChecker_rec(node.right);
        return (left && res && right);
    }

    //check the class's varDecs that if they are inited - they are inited with a primitive type
    //also inserts them to the table
    public static boolean classVarInitScanner(AST_DEC_CLASS decClass) {
        //iterate over the vars
        for (AST_Node var : decClass.varList) {
            //check if the var as assignment
            AST_DEC_VAR decVar = (AST_DEC_VAR) var;
            //get the var type
            TYPE varType = null;
            if (decVar.type.equals("int"))
                varType = TYPE_INT.getInstance();
            else if (decVar.type.equals("string"))
                varType = TYPE_STRING.getInstance();
            else {
                // an object Type - find it on the table
                varType = MY_SYMBOL_TABLE.getInstance().get(decVar.type);
                if (varType == null) {
                    //TODO ERROR
                    return false;
                }
            }
            //check if the init has basic assignment
            if (decVar.exp != null) {
                if (decVar.exp instanceof AST_EXP_INT && varType != TYPE_INT.getInstance()) {
                    //TODO ERROR
                    return false;
                }
                if (decVar.exp instanceof AST_EXP_STRING && varType != TYPE_STRING.getInstance()) {
                    //TODO ERROR
                    return false;
                }
                if (varType != TYPE_STRING.getInstance() && varType != TYPE_INT.getInstance() && !(decVar.exp instanceof AST_EXP_NIL)) {
                    //TODO ERROR
                    return false;
                }
            }
            //TODO remove the class getter
            TYPE_CLASS classType = (TYPE_CLASS) MY_SYMBOL_TABLE.getInstance().get(decClass.getName());
            classType.addDataMember(new TYPE_CLASS_VAR_DEC(varType, decVar.name));
        }
        return true;
    }
}
