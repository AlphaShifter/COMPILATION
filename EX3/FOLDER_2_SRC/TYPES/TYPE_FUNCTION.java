package TYPES;

/**
 * Created by giladi on 03/12/2017.
 */
public class TYPE_FUNCTION extends TYPE{

    public TYPE returnType;
    public String name;
    public TYPE_CLASS_VAR_DEC_LIST arguments;

    public TYPE_FUNCTION (TYPE returnType, String name, TYPE_CLASS_VAR_DEC_LIST arguments){
        this.arguments = arguments;
        this.name = name;
        this.returnType = returnType;
    }

}
