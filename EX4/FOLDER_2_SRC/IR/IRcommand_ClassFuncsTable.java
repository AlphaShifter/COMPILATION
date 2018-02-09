/***********/
/* PACKAGE */
/***********/
package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;

import java.util.List;

/*******************/
/* GENERAL IMPORTS */
/*******************/
/*******************/
/* PROJECT IMPORTS */

/*******************/

public class IRcommand_ClassFuncsTable extends IRcommand
{

	List funcs;
	String className;
	public IRcommand_ClassFuncsTable(List<String> funcLabels, String className){

		this.funcs = funcLabels;
		this.className = className;
	}

	public void MIPSme()
	{

		sir_MIPS_a_lot.getInstance().classFuncs(className,funcs);
	}

	@Override
	public boolean isData() {
		return true;
	}
}
