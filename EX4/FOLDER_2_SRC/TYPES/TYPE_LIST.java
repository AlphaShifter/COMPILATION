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

	public void addRewrite(TYPE newT, TYPE oldT){
		TYPE_LIST curr = this;
		while(curr.head != oldT){
			curr = curr.tail;
		}
		curr.head = newT;
	}
	public void addAll(TYPE_LIST other){
		addAll(other, false);
	}

	public void addAll(TYPE_LIST other, boolean rewrite){
		if(rewrite){
			TYPE_LIST copier = other;
			while (copier != null) {
				TYPE oldT = this.findInList(copier.head.getName());
				if(oldT != null){
					this.addRewrite(copier.head,oldT);
				} else {
					this.add(copier.head);
				}
				copier = copier.tail;
			}
		} else {
			if (other == null)
				return;
			//find the end of the list
			TYPE_LIST curr = this;
			while (curr.tail != null) {
				curr = curr.tail;
			}
			//add t as new member of the list
			TYPE_LIST copier = other;
			while (copier != null) {
				//add head from other
				if (this.head == null)
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


	/*
	finds the entry that corresponds to the name 'query'
	if does not exist in list, returns null
	 */
	public TYPE findInList(String query) {
		TYPE_LIST currTail = this;
		if(this.head == null) //empty list
			return null;
		while(currTail != null) {
			TYPE_FUNCTION currHead = (TYPE_FUNCTION)currTail.head;
			if(currHead.name.equals(query)) {
				return currHead;
			}
			// update the pointers
			currTail = currTail.tail;
		}
		return null;
	}


	// checks that the arguments passed to two different functions are the same
	public boolean compareFuncArgsByType(TYPE_LIST compareTo) {

		TYPE_LIST currTail1 = this;
		TYPE_LIST currTail2 = compareTo;
		while((currTail1 != null) && (currTail2 != null)){ // go on until reached end of either list
			TYPE currHead1 = currTail1.head;
			TYPE currHead2 = currTail2.head;
			// if the types of the arguments are not equal
			if(currHead1 != currHead2) {
				return false;
			}
			currTail1 = currTail1.tail;
			currTail2 = currTail2.tail;
		}
		if((currTail1 != null) || (currTail2 != null)) {
			return false;
		}
		return true;
	}

	public int getSize(){
		int res = 0;
		for(TYPE_LIST runner = this; runner != null; runner = runner.tail){
			res++;
		}
		if(this.head == null)
			return 0;
		else
			return res;
	}


}
