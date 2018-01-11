import java.util.regex.*;
public class MatchTest{
public static void main(String[] args) {

public static replaceTemp(int tmp_id,int assignment,String filePath){

	Pattern p2 = Pattern.compile("(,|\\s|\\(|\\()");
	Pattern p = Pattern.compile("Temp_\\d{1,5}");

	Pattern p = Pattern.compile("Temp_"+tmp_id);
	String test="	lw Temp_30,Temp_30";
	String test="	li Temp_1,100	sw Temp_1, -8($fp)	lw Temp_3, -4($fp)	move Temp_2,Temp_3	sw Temp_2, -12($fp)";
	Matcher m=p.matcher(test);
	String str=m.replaceAll("\\$t"+assignment); 
	String str=m.replaceAll("{\\1}"); 
	System.out.println(str);
	String[] command={"sed", "-i", "'s/Temp_"+tmp_id+"\\$\\$t"+assignment+"/g'", filePath};
	ProcessBuilder pb = new ProcessBuilder(command);
	Process process = pb.start();
	process.waitFor();


}

}	
}
