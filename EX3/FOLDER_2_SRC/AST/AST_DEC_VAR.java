package AST;

public class AST_DEC_VAR extends AST_DEC
{

	String type;
	String name;
	AST_EXP exp;

	/*********************************************************/
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AST_EXP getExp() {
		return exp;
	}

	public void setExp(AST_EXP exp) {
		this.exp = exp;
	}
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

	@Override
	public AST_Node getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLeft(){}

	@Override
	public AST_Node getRight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRight(){}}
