package AST;

import Auxillery.Util;
import SYMBOL_TABLE.MY_SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_INT;
import TYPES.TYPE_NIL;
import TYPES.TYPE_STRING;

public class AST_DEC_VAR extends AST_DEC
{

	public String type;
	public String name;
	public AST_EXP exp;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */
	/*********************************************************/
	public AST_DEC_VAR(String type, String name, AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if(exp != null)
			System.out.format(" decVar -> TYPE( %s ) NAME(%s) ASSIGN EXP SEMICOLON\n", type,name);
		else
			System.out.format(" decVar -> TYPE( %s ) NAME(%s)SEMICOLON\n", type,name);


		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.name = name;
		this.exp = exp;

		right = exp;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE decVar ( %s ) (%s)\n",type, name);
		if (exp != null) exp.PrintMe();

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Variable Deceleration: TYPE(%s) NAME(%s)", type, name));
		if (exp != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

    public boolean varScanner() {
        TYPE t = Util.stringToType(this.type);
        //TODO Scope
        if (t == null)
            return false;

        if (this.exp != null) {
            TYPE expType = this.exp.getExpType();
            if(expType == null){
                //TODO ERROR
                return false;
            }
            if (t != expType) {
                //we only allow type difference in the following cases:
                //father into son
                //NIL into obj or array
                if (expType == TYPE_NIL.getInstance()) {
                    //if we assign NIL into primitive: error
                    if (t == TYPE_INT.getInstance() || t == TYPE_STRING.getInstance()) {
                        //TODO ERROR
                        return false;
                    }
                }
                //if expType is a primitive: error
                if (expType == TYPE_INT.getInstance() || expType == TYPE_STRING.getInstance()) {
                    //TODO ERROR
                    return false;
                }
                //check if expType is father of t
                if (!Util.isFatherOf(t, expType)) {
                    //TODO ERROR
                    return false;
                }
            }
        }
        MY_SYMBOL_TABLE.getInstance().add(this.name, t);
        return true;
    }
}
