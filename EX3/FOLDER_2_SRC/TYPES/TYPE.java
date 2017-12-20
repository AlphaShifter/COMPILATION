package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;

	public abstract String getType();


	public boolean isArray(){ return false;}
	public boolean isClass(){ return false;}

	public static boolean eqByType(TYPE t1, TYPE t2){
		return t1.getType().equals(t2.getType());
	}
	public static boolean eqByString(TYPE t1, String s){
		return t1.getType().equals(s);
	}
}
