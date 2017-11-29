package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/*******************************************************************/
	/* Keep the name for debug purposes ... it won't harm anything ... */
	/*******************************************************************/
	public String name;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_CLASS_VAR_DEC_LIST data_members;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,TYPE_CLASS_VAR_DEC_LIST data_members)
	{
		this.father = father;
		this.data_members = data_members;
	}
}
