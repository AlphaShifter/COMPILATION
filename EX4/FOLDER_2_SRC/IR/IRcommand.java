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

	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();
}
