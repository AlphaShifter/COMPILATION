/***********/
/* PACKAGE */
/***********/
package AST_PRINT;

/************************/
/* AST PRINTING VISITOR */
/************************/
public class AST_Printing_Visitor
{
	/****************/
	/* DATE MEMBERS */
	/****************/
	public FilePrinter fl;
	public static int serial_number = 0;
	
	/***************************************/
	/* AST_Printing_Visit ... AST_DEC_LIST */
	/***************************************/
	public Visit(AST_DEC_LIST AST)
	{
		/****************************************/
		/* [1] Print The AST node serial number */
		/****************************************/
		fl.print(String.format("v%d",serial_number));

		/*******************************/
		/* [2] Print The AST node name */
		/*******************************/
		fl.print(" [label = \"DEC\nLIST\"];\n");

		/************************************************/
		/* [3] Print (this,left) and (this,right) edges */
		/************************************************/
		fl.print(String.format("v%d -> v%d",serial_number,serial_number+1));
		fl.print(String.format("v%d -> v%d",serial_number,serial_number+2));

		/***************************/
		/* [4] Print Head and Tail */
		/***************************/
		serial_number++; Visit(AST.head);
		serial_number++; Visit(AST.tail);
	}

	/****************************************/
	/* AST_Printing_Visit ... AST_STMT_LIST */
	/****************************************/
	public Visit(AST_STMT_LIST AST)
	{
		/****************************************/
		/* [1] Print The AST node serial number */
		/****************************************/
		fl.print(String.format("v%d",serial_number));

		/*******************************/
		/* [2] Print The AST node name */
		/*******************************/
		fl.print(" [label = \"STMT\nLIST\"];\n");

		/************************************************/
		/* [3] Print (this,left) and (this,right) edges */
		/************************************************/
		fl.print(String.format("v%d -> v%d",serial_number,serial_number+1));
		fl.print(String.format("v%d -> v%d",serial_number,serial_number+2));

		/***************************/
		/* [4] Print Head and Tail */
		/***************************/
		serial_number++; Visit(AST.head);
		serial_number++; Visit(AST.tail);
	}

	/***************************************/
	/* AST_Printing_Visit ... AST_EXP_LIST */
	/***************************************/
	public Visit(AST_EXP_LIST AST)
	{
		/****************************************/
		/* [1] Print The AST node serial number */
		/****************************************/
		fl.print(String.format("v%d",serial_number));

		/*******************************/
		/* [2] Print The AST node name */
		/*******************************/
		fl.print(" [label = \"EXP\nLIST\"];\n");

		/************************************************/
		/* [3] Print (this,left) and (this,right) edges */
		/************************************************/
		fl.print(String.format("v%d -> v%d",serial_number,serial_number+1));
		fl.print(String.format("v%d -> v%d",serial_number,serial_number+2));

		/***************************/
		/* [4] Print Head and Tail */
		/***************************/
		serial_number++; Visit(AST.head);
		serial_number++; Visit(AST.tail);
	}

	/**************************************/
	/* AST_Printing_Visit ... AST_STMT_IF */
	/**************************************/
	public Visit(AST_STMT_IF AST)
	{
		/****************************************/
		/* [1] Print The AST node serial number */
		/****************************************/
		fl.print(String.format("v%d",serial_number));

		/*******************************/
		/* [2] Print The AST node name */
		/*******************************/
		fl.print(" [label = \"IF (cond)\nTHEN { body }\"];\n");

		/************************************************/
		/* [3] Print (this,left) and (this,right) edges */
		/************************************************/
		fl.print(String.format("v%d -> v%d [label = \" cond\" ];\n",serial_number,serial_number+1));
		fl.print(String.format("v%d -> v%d [label = \" body\" ];\n",serial_number,serial_number+2));

		/***************************/
		/* [4] Print cond and body */
		/***************************/
		serial_number++; Visit(AST.cond);
		serial_number++; Visit(AST.body);
	}

	/*****************************************/
	/* AST_Printing_Visit ... AST_STMT_WHILE */
	/*****************************************/
	public Visit(AST_STMT_WHILE AST)
	{
		/****************************************/
		/* [1] Print The AST node serial number */
		/****************************************/
		fl.print(String.format("v%d",serial_number));

		/*******************************/
		/* [2] Print The AST node name */
		/*******************************/
		fl.print(" [label = \"WHILE (cond)\nDO { body }\"];\n");

		/************************************************/
		/* [3] Print (this,left) and (this,right) edges */
		/************************************************/
		fl.print(String.format("v%d -> v%d [label = \" cond\" ];\n",serial_number,serial_number+1));
		fl.print(String.format("v%d -> v%d [label = \" body\" ];\n",serial_number,serial_number+2));

		/***************************/
		/* [4] Print cond and body */
		/***************************/
		serial_number++; Visit(AST.cond);
		serial_number++; Visit(AST.body);
	}

}
