package TYPES;

public class TYPE_FUNCTION_LIST extends TYPE
{
	public TYPE_FUNCTION head;
	public TYPE_FUNCTION_LIST tail;

	public TYPE_FUNCTION_LIST(TYPE_FUNCTION head, TYPE_FUNCTION_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	public String getType() {
	return "TYPE_FUNCTION_LIST";
}

}
