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

	public void addAll(TYPE_LIST other){
		if(other == null)
			return;
		//find the end of the list
		TYPE_LIST curr = this;
		while(curr.tail != null){
			curr = curr.tail;
		}
		//add t as new member of the list
		TYPE_LIST copier = other;
		while(copier != null){
			//add head from other
			if(this.head == null)
				this.head = copier.head;
			else {
				curr.tail = new TYPE_LIST(copier.head, null);
				curr = curr.tail; //curr is moved only here because in the edge case of an empty list in shouldn't
			}
			//move copy
			copier = copier.tail;
		}
	}

}
