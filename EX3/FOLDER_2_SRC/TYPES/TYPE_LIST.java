package TYPES;

public class TYPE_LIST extends TYPE
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public String getType() {
		return "TYPE_LIST";
	}

	//add TYPE t to the end of the list
	public void add(TYPE t){
		//find the end of the list
		TYPE_LIST curr = this;
		while(curr.tail != null){
			curr = curr.tail;
		}
		//add t as new member of the list
		if(this.head == null)
			this.head = t;
		else
			curr.tail = new TYPE_LIST(t,null);
	}

}
