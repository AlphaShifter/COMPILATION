package AST.EXP;

import AST.AST_GRAPHVIZ;
import AST.AST_Node_Serial_Number;
import TEMP.*;
import IR.*;
import MIPS.sir_MIPS_a_lot;
import TYPES.TYPE;
import TYPES.TYPE_STRING;

public class AST_EXP_STRING extends AST_EXP
{
	public String value;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String str)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format(" exp -> String( %s )\n", str);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.value = str.substring(1,str.length()-1);
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE STRING( %s )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",value));
	}

	@Override
	public TYPE SemantMe() {
		return TYPE_STRING.getInstance();
	}

	@Override
    public TEMP IRme() {
        TEMP address = TEMP_FACTORY.getInstance().getFreshTEMP();
        IR.getInstance().Add_IRcommand(
                new IRcommand_mallocHeap(address, this.value.length() + 1)
        );
        int res = 0;
        int count = 0;
        int off = 0;

        for (int i = 0; i < value.length(); i++) {

            //compress string chars into words
            res = res + (value.charAt(i) * (int) Math.pow(2, 8 * count));
            count++;
            if (count == sir_MIPS_a_lot.getInstance().getWORDSIZE()) {

                TEMP c = TEMP_FACTORY.getInstance().getFreshTEMP();
                //get the char from string
                IR.getInstance().Add_IRcommand(
                        new IRcommandConstInt(c, res)
                );
                //save on heap
                IR.getInstance().Add_IRcommand(
                        new IRcommand_SaveOnHeap(c, address, off)
                );
                res = 0;
                count = 0;
                off++;
            }

        }
        //save leftovers
        if (count != 0) {
            TEMP c = TEMP_FACTORY.getInstance().getFreshTEMP();
            //get the char from string
            IR.getInstance().Add_IRcommand(
                    new IRcommandConstInt(c, res)
            );
            //save on heap
            IR.getInstance().Add_IRcommand(
                    new IRcommand_SaveOnHeap(c, address, off)
            );
            off++;
        }
        //add zero
        IR.getInstance().Add_IRcommand(
                new IRcommand_SaveOnHeap(ZERO_REG.getInstance(), address, off)
        );

        return address;
    }

}
