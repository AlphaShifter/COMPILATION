package AST;

public class AST_DEC_CLASS extends AST_DEC
{

	String name;
	String ext;
	public AST_CFIELD_LIST cfieldList;
	public AST_FUNC_LIST funcList;
	public AST_VAR_LIST varList;

	/*********************************************************/
	/* The default message for an unknown AST DECLERATION node */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public AST_CFIELD_LIST getCfieldList() {
		return cfieldList;
	}

	public void setCfieldList(AST_CFIELD_LIST cfieldList) {
		this.cfieldList = cfieldList;
	}

	public AST_FUNC_LIST getFuncList() {
		return funcList;
	}

	public void setFuncList(AST_FUNC_LIST funcList) {
		this.funcList = funcList;
	}

	public AST_VAR_LIST getVarList() {
		return varList;
	}

	public void setVarList(AST_VAR_LIST varList) {
		this.varList = varList;
	}

	/*********************************************************/
	public AST_DEC_CLASS(String name, String ext, AST_CFIELD_LIST cfieldList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/

		System.out.format("decClass -> name( %s )\n", name);



		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.cfieldList = cfieldList;
		this.ext = ext;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST ID EXP */
		/*******************************/
		System.out.format("AST NODE decClass ( %s )\n",name);
		if(ext != null) System.out.format("extends %s",ext);
		if (cfieldList != null) cfieldList.PrintMe();


		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		if (ext != null)
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration NAME(%s) EXTENDS", name,ext));
		else
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("Class Declaration NAME(%s)", name));
		if (cfieldList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldList.SerialNumber);

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
