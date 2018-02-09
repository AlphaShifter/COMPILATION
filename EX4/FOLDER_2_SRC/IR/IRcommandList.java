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

public class IRcommandList
{
	public IRcommand head;
	public IRcommandList tail;

	IRcommandList(IRcommand head, IRcommandList tail)
	{
		this.head = head;
		this.tail = tail;
	}


	public void MIPSData(){
		if (head != null)
			if(head.isData())
				head.MIPSme();
		if (tail != null) tail.MIPSData();
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		if (head != null)
			if(!head.isData())
				head.MIPSme();
		if (tail != null) tail.MIPSme();
	}
}
