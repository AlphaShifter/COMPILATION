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

public class IRcommand_Binop_Div_Integers extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;

	public IRcommand_Binop_Div_Integers(TEMP dst, TEMP t1, TEMP t2)
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
		TEMP t1_div_t2 = TEMP_FACTORY.getInstance().getFreshTEMP();

		/******************************************/
		/* [1] Allocate a fresh temporary INT_MAX */
		/******************************************/
		TEMP intMax = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP intMin = TEMP_FACTORY.getInstance().getFreshTEMP();


		/********************************/
		/* [2] intMax := 32767 (= 2^15) */
		/********************************/
		sir_MIPS_a_lot.getInstance().li(intMax,32767);
		sir_MIPS_a_lot.getInstance().li(intMin,-32768);


		/****************************************************/
		/* [3] Allocate a fresh label for possible overflow */
		/****************************************************/
		String label_end         = getFreshLabel("end");
		String label_dev_by_zero    = getFreshLabel("div_by_zero");
		String label_overflow    = getFreshLabel("overflow");
		String label_overflow__min    = getFreshLabel("overflow_min");
		String label_no_overflow = getFreshLabel("no_overflow");


		/*
		check if t2 is 0 if it is, die
		 */
		TEMP zero = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().li(zero,0);
		sir_MIPS_a_lot.getInstance().beq(t2,zero,label_dev_by_zero);


		/*********************/
		/* [4] calc t1 *t2 */
		/*********************/
		sir_MIPS_a_lot.getInstance().div(t1,t2);

		/*
		save result to t1_div_t2
		 */
		sir_MIPS_a_lot.getInstance().getLo(t1_div_t2); // store the contents of the register Lo in t1_div_t2

		sir_MIPS_a_lot.getInstance().blt(intMax,t1_div_t2,label_overflow); // overflow when intMax < t1_plus_t2
		sir_MIPS_a_lot.getInstance().bgt(intMin,t1_div_t2,label_overflow__min); // overflow when inMin > t1_plus_t2
		sir_MIPS_a_lot.getInstance().bge(intMax,t1_div_t2,label_no_overflow); // no overflow when intMax >= t1_plus_t2

		sir_MIPS_a_lot.getInstance().label(label_no_overflow);
		sir_MIPS_a_lot.getInstance().move(dst,t1_div_t2);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		//if div by zero - die
		sir_MIPS_a_lot.getInstance().label(label_dev_by_zero);
		sir_MIPS_a_lot.getInstance().printDivByZero();
		sir_MIPS_a_lot.getInstance().jump(label_end);

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
		sir_MIPS_a_lot.getInstance().li(dst,-32768);
		sir_MIPS_a_lot.getInstance().jump(label_end);


		/******************/
		/* [5] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);		
	}
}
