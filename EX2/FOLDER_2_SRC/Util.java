import AST.*;

public class Util {


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
            }
            runner = runner.tail;
        }
    }
}
