/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import java.io.PrintWriter;
import java.util.List;

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
        fileWriter.print("END_OF_PROGRAM:\n");
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

    public void print_string(TEMP t) {
        fileWriter.format("\tmove $a0,%s\n", tempToString(t));
        fileWriter.format("\tli $v0,4\n");
        fileWriter.format("\tsyscall\n");

    }

    public void getStrLen(TEMP dst, TEMP address){
        fileWriter.format("\tmove $a0, %s\n",tempToString(address));
        jal("strlen");
        fileWriter.format("\tmove %s, $v0\n",tempToString(dst));
    }

    public void mallocHeap(TEMP dest, int amount){
        fileWriter.format("\tli $a0 ,%d\n",amount*WORD_SIZE);
        fileWriter.format("\tli $v0,9\n");
        fileWriter.format("\tsyscall\n");
        fileWriter.format("\tmove %s,$v0\n",tempToString(dest));
    }

    public void comment(String comment){
        fileWriter.format("##### %s\n",comment);
    }

    public void storeAddressLocalVar(TEMP src, int serialLocalVarNum) {

        fileWriter.format("\tsw %s, %d($fp)\n", tempToString(src), - serialLocalVarNum * WORD_SIZE);
       // fileWriter.format("\taddi Temp_%d,$fp,%d\n", idx, -serialLocalVarNum * WORD_SIZE);
    }

    public void load(TEMP dst, TEMP src) {

       // fileWriter.format("\tlw Temp_%d,0(Temp_%d)\n", idxdst, idxsrc);
        fileWriter.format("\tlw %s,0(%s)\n", tempToString(dst), tempToString(src));

    }

    public void getFuncFromTable(TEMP dest, TEMP t, String name, int offset){

        fileWriter.format("\tla %s, class_%s\n",tempToString(t),name);
        fileWriter.format("\tlw %s, %d(%s)\n",tempToString(dest),offset * WORD_SIZE,tempToString(t));
    }

    public void loadAddressLocalVar(TEMP dst, int serialLocalVarNum){
        int idx = dst.getSerialNumber();
        fileWriter.format("\tlw %s, %d($fp)\n",tempToString(dst),-serialLocalVarNum*WORD_SIZE);
       // fileWriter.format("\taddi Temp_%d,$fp,%d\n", idx, -serialLocalVarNum * WORD_SIZE);
       //   fileWriter.format("\taddi Temp_%d,$sp,%d\n", idx, -serialLocalVarNum * WORD_SIZE);


    }

    public void move(TEMP dst, TEMP src) {

        //fileWriter.format("\tsw %s,0(%s)\n", tempToString(src), tempToString(dst));
        fileWriter.format("\tmove %s,%s\n", tempToString(dst), tempToString(src));

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

        fileWriter.format("\tblt %s,%s,%s\n", tempToString(oprnd1), tempToString(oprnd2), label);
    }

    public void bge(TEMP oprnd1, TEMP oprnd2, String label) {
        fileWriter.format("\tbge %s,%s,%s\n", tempToString(oprnd1), tempToString(oprnd2), label);

    }

    public void bgt(TEMP oprnd1, TEMP oprnd2, String label) {
        fileWriter.format("\tbgt %s,%s,%s\n", tempToString(oprnd1), tempToString(oprnd2), label);

    }

    public void ble(TEMP oprnd1, TEMP oprnd2, String label) {
        fileWriter.format("\tble %s,%s,%s\n", tempToString(oprnd1), tempToString(oprnd2), label);

    }
    public void beq(TEMP oprnd1, TEMP oprnd2, String label) {
        fileWriter.format("\tbeq %s,%s,%s\n", tempToString(oprnd1), tempToString(oprnd2), label);

    }
    public void bne(TEMP oprnd1, TEMP oprnd2, String label) {
        fileWriter.format("\tbne %s,%s,%s\n", tempToString(oprnd1), tempToString(oprnd2), label);

    }

    public void openNewFrame(int numOfVars){
        //save old FP to the stack
        fileWriter.format("\taddi $sp $sp -4\n");
        fileWriter.format("\tsw  $fp 0($sp)\n");

        //save the FP to the current stack point
        fileWriter.format("\tmove $fp,$sp\n");
        //open room on the stack
        fileWriter.format("\taddi $sp,$sp,%d\n",-numOfVars*WORD_SIZE);

    }

    public void closeFrame(){
        fileWriter.format("\tmove $sp, $fp\n");
    }

    public void setRoomOnStack(int size){
        fileWriter.format("\taddi $sp,$sp,%d\n", -size*WORD_SIZE);
    }

    public void saveRegOnStack(TEMP res, int offset){
        fileWriter.format("\tsw %s, %d($sp)\n", tempToString(res),offset*WORD_SIZE);
    }

    public void saveOnHeap(TEMP src, TEMP address, int offset){
        fileWriter.format("\tsw %s, %d(%s)\n", tempToString(src),offset*WORD_SIZE,tempToString(address));
    }

    public void LoadFromHeap(TEMP dest, TEMP address, int offset){
        fileWriter.format("\tlw %s, %d(%s)\n", tempToString(dest),offset*WORD_SIZE,tempToString(address));
    }

    public void loadRegfromStack(TEMP res, int offset){
        fileWriter.format("\tlw %s, %d($sp)\n", tempToString(res),offset*WORD_SIZE);
    }


    public void saveRaOnStack(){
        fileWriter.format("\tsw $ra,0($sp)\n");
    }

    public void resotreOldFp(int offset){
        fileWriter.format("\tlw $fp, %d($fp)\n",offset*WORD_SIZE);
    }

    public void restoreRaFromStack(int offset){
        fileWriter.format("\tlw $ra, %d($sp)\n", offset*WORD_SIZE);
    }
    public void saveReturnOnStack(int offset, TEMP t){
        fileWriter.format("\tsw %s, %d($sp)\n", tempToString(t), offset*WORD_SIZE);
    }
    public void jal(String target){fileWriter.format("\tjal %s\n",target);}
    public void jr(){fileWriter.format("\tjr $ra\n");}
    public void jalr(TEMP target){fileWriter.format("\tjalr %s\n",tempToString(target));}



    public void printDivByZero(){
        //print the error
        fileWriter.format("\tli $v0, 4\n");
        fileWriter.format("\tla $a0, string_illegal_div_by_0\n");
        fileWriter.format("\tsyscall\n");
        //exit
        fileWriter.print("\tj END_OF_PROGRAM\n");
    }

    public void printIllegalPointer(){
        //print the error
        fileWriter.format("\tli $v0, 4\n");
        fileWriter.format("\tla $a0, string_invalid_ptr_dref\n");
        fileWriter.format("\tsyscall\n");
        //exit
        fileWriter.print("\tj END_OF_PROGRAM\n");
    }

    public void printAccessViolation() {
        //print the error
        fileWriter.format("\tli $v0, 4\n");
        fileWriter.format("\tla $a0, string_access_violation\n");
        fileWriter.format("\tsyscall\n");
        //exit
        fileWriter.print("\tli $v0,10\n");
        fileWriter.print("\tsyscall\n");
    }

    public void classFuncs(String className, List<String> funcs){
        String s = funcs.get(0);
        if (funcs.size() > 1) {
            for (String str : funcs.subList(1,funcs.size())){
                s = s + ", " + str;
            }
        }
        fileWriter.format("class_%s:\t.word\t%s\n",className,s);
    }

//    public void printStrlen(){ //chars
//        fileWriter.print("strlen:\n");
//        fileWriter.print("\tli $s0, 0\n");
//        fileWriter.print("loop_strlen:\n");
//        fileWriter.print("\tlb $s1, 0($a0)\n");
//        fileWriter.print("\tbeqz $s1, exit_strlen\n");
//        fileWriter.print("\taddi $a0, $a0, 1\n");
//        fileWriter.print("\taddi $s0, $s0, 1\n");
//        fileWriter.print("\tj loop_strlen\n");
//        fileWriter.print("exit_strlen:\n");
//        fileWriter.print("\tmove $v0, $s0\n");
//        fileWriter.print("\tjr $ra\n");
//    }

    public void printStrlen(){ //words
        fileWriter.print("strlen:\n");
        fileWriter.print("\tli $s0, 0\n");
        fileWriter.print("loop_strlen:\n");
        fileWriter.print("\tlw $s1, 0($a0)\n");
        fileWriter.print("\tbeqz $s1, exit_strlen\n");
        fileWriter.format("\taddi $a0, $a0, %d\n",WORD_SIZE);
        fileWriter.print("\taddi $s0, $s0, 1\n");
        fileWriter.print("\tj loop_strlen\n");
        fileWriter.print("exit_strlen:\n");
        fileWriter.print("\tmove $v0, $s0\n");
        fileWriter.print("\tjr $ra\n");
    }

    public void printStrcpy(){
        fileWriter.print("strcpy:\n");
        fileWriter.print("\tlb $s0 0($a0)\n");
        fileWriter.print("\tbeqz $s0, exit_strcpy\n");
        fileWriter.print("\tsb $s0 0($a1)\n");
        fileWriter.print("\taddi $a0 $a0 1\n");
        fileWriter.print("\taddi $a1 $a1 1\n");
        fileWriter.print("\tj strcpy\n");
        fileWriter.print("exit_strcpy:\n");
        fileWriter.print("\tmove $v0, $a1\n");
        fileWriter.print("\tjr $ra\n");
    }

    public void printStrCon(){


        fileWriter.format("strcon:\n");
        fileWriter.format("\taddi $sp,$sp,%d\n",-2*WORD_SIZE);
        fileWriter.format("\tsw $ra,0($sp)\n");
        fileWriter.format("\tmove $s0 ,$a2\n"); //save arguments
        fileWriter.format("\tmove $s1 ,$a0\n");
        fileWriter.format("\tmove $s2 ,$a1\n");


        fileWriter.format("\tli $a0, %d\n",WORD_SIZE);
        fileWriter.format("\tmult $a0, $s0\n");
        fileWriter.format("\tmflo $a0\n");

        fileWriter.format("\tli $v0,9\n");
        fileWriter.format("\tsyscall\n");
        fileWriter.format("\tmove $s0,$v0\n"); //save new address t0
        fileWriter.format("\tsw $s0,%d($sp)\n",WORD_SIZE);
        fileWriter.format("\tmove $a1, $s0\n"); //copy first string
        fileWriter.format("\tmove $a0, $s1\n");
        jal("strcpy");
        fileWriter.format("\tmove $a1, $v0\n"); //copy second string
        fileWriter.format("\tmove $a0, $s2\n");
        jal("strcpy");
        fileWriter.format("\tsb $zero, 0($v0)\n"); //print an empty byte


        fileWriter.format("\tlw $s0, %d($sp)\n",WORD_SIZE);
        fileWriter.format("\tmove $v0, $s0\n"); //return the address
        fileWriter.format("\tlw $ra, 0($sp)\n");
        fileWriter.format("\taddi $sp,$sp,%d\n",2*WORD_SIZE);
        fileWriter.format("\tjr $ra\n");

    }

    public void strCon(TEMP dest, TEMP s1, TEMP s2, TEMP size){
        fileWriter.format("\tmove $a0, %s\n",tempToString(s1));
        fileWriter.format("\tmove $a1, %s\n",tempToString(s2));
        fileWriter.format("\tmove $a2, %s\n",tempToString(size));
        jal("strcon");
        fileWriter.format("\tmove %s, $v0\n",tempToString(dest));

    }

    public void printMain(){
        /************************************************/
			/* [4] Print text section with entry point main */
        /************************************************/
        fileWriter.print(".text\n");
        fileWriter.print("### start with main function\n");
        fileWriter.print("j main\n");
        fileWriter.print("############################\n");
        printStrlen();
        fileWriter.print("############################\n");
        printStrcpy();
        fileWriter.print("############################\n");
        printStrCon();
        fileWriter.print("############################\n");
    }

    private String tempToString(TEMP t){
        int i = t.getSerialNumber();

        String s = "Temp_" + i;
        if(t instanceof ZERO_REG)
            s = "$zero";
        if(t instanceof ARGUMENT)
            s = "$a" + ((ARGUMENT) t).getLocal();
        if(t instanceof TEMP_REG)
            s = "$t" +((TEMP_REG)t).getLocal();

        return s;
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

    public int getWORDSIZE(){return WORD_SIZE;}

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

        }
        return instance;
    }
}
