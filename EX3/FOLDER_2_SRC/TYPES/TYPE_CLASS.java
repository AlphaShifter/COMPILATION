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
	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_CLASS_VAR_DEC_LIST data_members;
	public TYPE_FUNCTION_LIST function_list;
	private TYPE_CLASS_VAR_DEC_LIST lastDataMember;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(String name, TYPE_CLASS father,TYPE_CLASS_VAR_DEC_LIST data_members, TYPE_FUNCTION_LIST function_list)
	{
		this.father = father;
		this.data_members = data_members;
		this.function_list = function_list;
		this.name = name;
	}

	public void addDataMember(TYPE_CLASS_VAR_DEC dec){
		if(data_members == null){
			data_members = new TYPE_CLASS_VAR_DEC_LIST(dec, null);
			lastDataMember = data_members;
		}
		else{
			lastDataMember.tail = new TYPE_CLASS_VAR_DEC_LIST(dec, null);
			lastDataMember = lastDataMember.tail;
		}
	}
}
