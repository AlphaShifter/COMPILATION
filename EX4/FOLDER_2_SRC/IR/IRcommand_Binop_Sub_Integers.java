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

import MIPS.sir_MIPS_a_lot;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class IRcommand_Binop_Sub_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_Sub_Integers(TEMP dst, TEMP t1, TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/******************************************************/
		/* [0] Allocate a fresh temporary t4 for the addition */
		/******************************************************/
		TEMP t1_minus_t2 = TEMP_FACTORY.getInstance().getFreshTEMP();

		/******************************************/
		/* [1] Allocate a fresh temporary INT_MAX */
		/******************************************/
		TEMP intMax = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP intMin = TEMP_FACTORY.getInstance().getFreshTEMP();


		/********************************/
		/* [2] intMax := 32767 (= 2^15) */
		/********************************/
		sir_MIPS_a_lot.getInstance().li(intMax,32767);
		sir_MIPS_a_lot.getInstance().li(intMin,-32767);

		/****************************************************/
		/* [3] Allocate a fresh label for possible overflow */
		/****************************************************/
		String label_end         = getFreshLabel("end");
		String label_overflow    = getFreshLabel("overflow");
		String label_overflow__min    = getFreshLabel("overflow_min");
		String label_no_overflow = getFreshLabel("no_overflow");

		/*********************/
		/* [4] t4 := t1 + t2 */
		/*********************/
		sir_MIPS_a_lot.getInstance().sub(t1_minus_t2,t1,t2);
		
		/********************************************************/
		/* [5] if (32767 <  t1_plus_t2) goto label_overflow;
		    * if (-32767 > t1_plus_t2) goto label "overflow_min */
		/*   (else)  if (32767 >= t1_plus_t2) goto label_no_overflow; */
		/********************************************************/
		sir_MIPS_a_lot.getInstance().blt(intMax,t1_minus_t2,label_overflow);
		sir_MIPS_a_lot.getInstance().bgt(intMin,t1_minus_t2,label_overflow);
		sir_MIPS_a_lot.getInstance().bge(intMax,t1_minus_t2,label_no_overflow);

		/***********************/
		/* [3] label_overflow: */
		/*                     */
		/*         t3 := 32767 */
		/*         goto end;   */
		/*                     */
		/***********************/
		sir_MIPS_a_lot.getInstance().label(label_overflow);
		sir_MIPS_a_lot.getInstance().li(dst,32767);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/*label_overflow_min
		t3 := -32767
		goto end
		 */
		sir_MIPS_a_lot.getInstance().label(label_overflow__min);
		sir_MIPS_a_lot.getInstance().li(dst,-32767);
		sir_MIPS_a_lot.getInstance().jump(label_end);


		/**************************/
		/* [4] label_no_overflow: */
		/*                        */
		/*         t3 := t1+t2    */
		/*         goto end;      */
		/*                        */
		/**************************/
		sir_MIPS_a_lot.getInstance().label(label_no_overflow);
		sir_MIPS_a_lot.getInstance().load(dst,t1_minus_t2);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [5] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);		
	}
}
