package SYMBOL_TABLE;

import TYPES.*;

public class SYMBOL_TABLE_ENTRY
{
	/******************************************/
	/* prev and next symbol table entries ... */
	/******************************************/
	public SYMBOL_TABLE_ENTRY prev;
	public SYMBOL_TABLE_ENTRY next;
	public SYMBOL_TABLE_ENTRY top;

	public String name;
	/******************/
	/* TYPE type ... */
	/******************/
	public TYPE type;


	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public SYMBOL_TABLE_ENTRY(SYMBOL_TABLE_ENTRY prev, SYMBOL_TABLE_ENTRY next, TYPE type, SYMBOL_TABLE_ENTRY top)
	{
		this.prev = prev;
		this.next = next;
		this.type = type;
		this.top = top;
	}
	public SYMBOL_TABLE_ENTRY(String name, TYPE t, SYMBOL_TABLE_ENTRY next, SYMBOL_TABLE_ENTRY prevtop){
		this.name =name;
		this.top = prevtop;
		this.prev = prevtop;
		this.type = t;
		this.next = next;

	}
}
