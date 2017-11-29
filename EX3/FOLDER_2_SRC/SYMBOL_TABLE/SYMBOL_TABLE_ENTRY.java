package TYPES;

public class SYMBOL_TABLE_ENTRY
{
	/******************************************/
	/* prev and next symbol table entries ... */
	/******************************************/
	public SYMBOL_TABLE_ENTRY prevtop;
	public SYMBOL_TABLE_ENTRY next;
	
	/******************/
	/* TYPE value ... */
	/******************/
	public TYPE value;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public SYMBOL_TABLE_ENTRY(SYMBOL_TABLE_ENTRY prev,SYMBOL_TABLE_ENTRY next,TYPE value)
	{
		this.prev = prev;
		this.next = next;
		this.value = value;
	}
}
