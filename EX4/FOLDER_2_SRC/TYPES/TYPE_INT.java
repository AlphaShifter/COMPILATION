package TYPES;

public class TYPE_INT extends TYPE
{
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static TYPE_INT instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected TYPE_INT() {this.name = "int";}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static TYPE_INT getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_INT();
		}
		return instance;
	}
	public String getType() {
		return "TYPE_INT";
	}
}
