/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import java.io.PrintWriter;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;

public class sir_MIPS_a_lot {
    private int WORD_SIZE = 4;
    /***********************/
    /* The file writer ... */
    /***********************/
    private PrintWriter fileWriter;

    /***********************/
	/* The file writer ... */

    /***********************/
    public void finalizeFile() {
        fileWriter.print("\tli $v0,10\n");
        fileWriter.print("\tsyscall\n");
        fileWriter.close();
    }

    public void print_int(TEMP t) {
        int idx = t.getSerialNumber();
        // fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
        fileWriter.format("\tmove $a0,Temp_%d\n", idx);
        fileWriter.format("\tli $v0,1\n");
        fileWriter.format("\tsyscall\n");
    }

    public TEMP addressLocalVar(int serialLocalVarNum) {
        TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
        int idx = t.getSerialNumber();

        fileWriter.format("\taddi Temp_%d,$fp,%d\n", idx, -serialLocalVarNum * WORD_SIZE);

        return t;
    }

    public void load(TEMP dst, TEMP src) {
        int idxdst = dst.getSerialNumber();
        int idxsrc = src.getSerialNumber();
        fileWriter.format("\tlw Temp_%d,0(Temp_%d)\n", idxdst, idxsrc);
    }

    public void store(TEMP dst, TEMP src) {
        int idxdst = dst.getSerialNumber();
        int idxsrc = src.getSerialNumber();
        fileWriter.format("\tsw Temp_%d,0(Temp_%d)\n", idxsrc, idxdst);
    }

    public void li(TEMP t, int value) {
        int idx = t.getSerialNumber();
        fileWriter.format("\tli Temp_%d,%d\n", idx, value);
    }

    public void add(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();
        int dstidx = dst.getSerialNumber();

        fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n", dstidx, i1, i2);
    }

    public void sub(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();
        int dstidx = dst.getSerialNumber();

        fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n", dstidx, i1, i2);
    }

    public void mult(TEMP t1, TEMP t2) {
        int i1 = t1.getSerialNumber();
        int i2 = t2.getSerialNumber();
        fileWriter.format("\tmult Temp_%d,Temp_%d\n", i1, i2);
    }
    public void div(TEMP t1, TEMP t2){
        int i1 = t1.getSerialNumber();
        int i2 = t2.getSerialNumber();
        fileWriter.format("\tdiv Temp_%d,Temp_%d\n", i1, i2);
    }
    public void getHi(TEMP dest){
        int t = dest.getSerialNumber();

        fileWriter.format("\tmfhi Temp_%d\n",t);
    }
    public void getLo(TEMP dest){
        int t = dest.getSerialNumber();

        fileWriter.format("\tmflo Temp_%d\n",t);
    }

    public void label(String inlabel) {
        fileWriter.format("%s:\n", inlabel);
    }

    public void jump(String inlabel) {
        fileWriter.format("\tj %s\n", inlabel);
    }

    public void blt(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n", i1, i2, label);
    }

    public void bge(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n", i1, i2, label);
    }

    public void bgt(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbgt Temp_%d,Temp_%d,%s\n", i1, i2, label);
    }

    public void ble(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tble Temp_%d,Temp_%d,%s\n", i1, i2, label);
    }
    public void beq(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n", i1, i2, label);
    }
    public void bne(TEMP oprnd1, TEMP oprnd2, String label) {
        int i1 = oprnd1.getSerialNumber();
        int i2 = oprnd2.getSerialNumber();

        fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n", i1, i2, label);
    }

    public void printDivByZero(){
        //print the error
        fileWriter.format("\tli $v0, 4\n");
        fileWriter.format("\tla $a0, string_illegal_div_by_0\n");
        fileWriter.format("\tsyscall\n");
        //exit
        fileWriter.print("\tli $v0,10\n");
        fileWriter.print("\tsyscall\n");

    }

    /**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
    /**************************************/
    private static sir_MIPS_a_lot instance = null;

    /*****************************/
	/* PREVENT INSTANTIATION ... */

    /*****************************/
    protected sir_MIPS_a_lot() {
    }

    /******************************/
	/* GET SINGLETON INSTANCE ... */

    /******************************/
    public static sir_MIPS_a_lot getInstance() {
        if (instance == null) {
            /*******************************/
			/* [0] The instance itself ... */
            /*******************************/
            instance = new sir_MIPS_a_lot();

            try {
                /*********************************************************************************/
				/* [1] Open the MIPS text file and write data section with error message strings */
                /*********************************************************************************/
                String dirname = "./FOLDER_5_OUTPUT/";
                String filename = String.format("MIPS.txt");

                /***************************************/
				/* [2] Open MIPS text file for writing */
                /***************************************/
                instance.fileWriter = new PrintWriter(dirname + filename);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

            /*****************************************************/
			/* [3] Print data section with error message strings */
            /*****************************************************/
            instance.fileWriter.print(".data\n");
            System.out.print("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

            instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
            instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
            instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");

            /************************************************/
			/* [4] Print text section with entry point main */
            /************************************************/
            instance.fileWriter.print(".text\n");
            instance.fileWriter.print("main:\n");

            /******************************************/
			/* [5] Will work with <= 10 variables ... */
            /******************************************/
            instance.fileWriter.print("\taddi $fp,$sp,40\n");
        }
        return instance;
    }
}
