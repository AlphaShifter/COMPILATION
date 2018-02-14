/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public abstract class IRcommand
{
	/*****************/
	/* Label Factory */
	/*****************/
	protected static int label_counter=0;
	protected String getFreshLabel(String msg)
	{
		return String.format("Label_%d_%s",label_counter++,msg);
	}

	public static String getFreshIfLabel(){ return String.format("Label_%d_%s",label_counter++,"End_if_block");}
	public static String getFreshWhileStartLabel(){return String.format("Label_%d_%s",label_counter++,"While_Start_block");}
	public static String getFreshWhileCondLabel(){return String.format("Label_%d_%s",label_counter++,"While_Block_Cond");}
	public static String getFreshFuncLabel(String name){return String.format("Label_%d_Func_%s",label_counter++,name);}
	public static String getFreshLegal(){return String.format("Label_%d_Legal",label_counter++);}
	public static String getFreshGlobal(int place) {return String.format("Label_%d_Global_Init_%d",label_counter++,place);}

	public boolean isData() {return false;}

	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();
}
