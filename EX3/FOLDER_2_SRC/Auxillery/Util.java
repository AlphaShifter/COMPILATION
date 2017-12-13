package Auxillery;

import SYMBOL_TABLE.MY_SYMBOL_TABLE;
import AST.*;
import TYPES.*;

public class Util {

    /*
    splits the list of fields in a AST_DEC_CLASS object into variables and methods
     */
    public static void classSplit(AST_PROGRAM root){
        AST_DEC_LIST runner = root.decList;
        AST_CFIELD_LIST classRunner;
        while(runner != null){ // run on classes in program
            if (runner.head instanceof AST_DEC_CLASS){
                AST_DEC_CLASS head =  (AST_DEC_CLASS)runner.head;
                classRunner = head.cfieldList;

                AST_VAR_LIST varList = head.varList;
                AST_FUNC_LIST funcList = head.funcList;

                while(classRunner != null){ // run on members in class

                    AST_DEC currDec = classRunner.head.dec;

                    if (currDec instanceof AST_DEC_VAR){
                        AST_VAR_LIST newVarList = new AST_VAR_LIST((AST_DEC_VAR)currDec, null);
                        //insertion
                        if(varList == null){
                            varList = newVarList;
                            head.varList = varList;
                        } else {
                            varList.tail = newVarList;
                            varList = varList.tail;
                        }
                    } else {
                        AST_FUNC_LIST newFuncList = new AST_FUNC_LIST((AST_DEC_FUNC)currDec, null);
                        //insertion
                        if(funcList == null){
                            funcList = newFuncList;
                            head.funcList = funcList;
                        } else {
                            funcList.tail = newFuncList;
                            funcList = funcList.tail;
                        }
                    }

                    classRunner = classRunner.tail;
                }
                head.left = head.varList;
                head.right = head.funcList;
            }
            runner = runner.tail;
        }
    }


    private static void decReduction(AST_PROGRAM root){
       AST_DEC_LIST runner = root.decList;
       while(runner != null){
           if(runner.head instanceof AST_DEC_SINGLE)
               runner.head = ((AST_DEC_SINGLE) runner.head).dec;
           runner = runner.tail;
       }
    }



    public static void treeReduction(AST_PROGRAM root){
        //reduce the decs
        decReduction(root);
        //break down to decs
        for(AST_Node node: root.decList) {
            treeReduction_rec(node);
        }
    }

    private static void treeReduction_rec(AST_Node node){
        if(node == null) return;
        AST_Node left = node.left;
        AST_Node right = node.right;
        if(left != null){
            if(left instanceof AST_EXP_SINGLE){
                node.left = ((AST_EXP_SINGLE) left).exp;
            }
        }
        if(right != null){
            if (right instanceof AST_EXP_SINGLE){
                node.right = ((AST_EXP_SINGLE) right).exp;
            }
        }
        treeReduction_rec(node.left);
        treeReduction_rec(node.right);
    }


    public static boolean logClasses(AST_PROGRAM root){
        MY_SYMBOL_TABLE symbolTable = MY_SYMBOL_TABLE.getInstance();
        for(AST_Node node : root.decList){
            if(node instanceof AST_DEC_CLASS){
                AST_DEC_CLASS currNode = (AST_DEC_CLASS) node; // cast the node to a class declaration
                if(symbolTable.get(currNode.getName()) != null){
                    // the class has already been declared
                    return false;
                } else if((currNode.getExt() != null) && (symbolTable.get(currNode.getExt()) == null)){
                    // the extended class (father) has not been declared yet
                    return false;
                } else{
                    // add the new class to the symbol table
                    TYPE_CLASS father = (TYPE_CLASS) symbolTable.get(currNode.getExt());
                    symbolTable.add(currNode.getName(), new TYPE_CLASS(currNode.getName(), father, null));
                }
            }
        }
        return true;
    }

    public static TYPE stringToType(String str){
        TYPE t;
        if (str.equals("int"))
            return TYPE_INT.getInstance();
        else if (str.equals("string"))
            return TYPE_STRING.getInstance();
        else {
            t = MY_SYMBOL_TABLE.getInstance().get(str);
            return t;
        }
    }

    public static boolean isFatherOf(TYPE son, TYPE father){
        if( !(son instanceof TYPE_CLASS) || !(father instanceof TYPE_CLASS))
            return false;
        TYPE_CLASS sonClass = (TYPE_CLASS)son;
        TYPE_CLASS fatherClass = (TYPE_CLASS)father;
        TYPE_CLASS ext = sonClass.father;
        while(ext != null){
            if(ext == fatherClass)
                return true;
            ext = ext.father;
        }
        return false;
    }
}
