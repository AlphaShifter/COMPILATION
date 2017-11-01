
":=" 					{ return symbol(TokenNames.ASSIGN);}
"=" 					{ return symbol(TokenNames.EQ);}
"["  					{ return symbol(TokenNames.LBRACK);}
"<" 					{ return symbol(TokenNames.LT);}
"]"  					{ return symbol(TokenNames.RBRACK );}
">" 					{ return symbol(TokenNames.GT);}
"{" 					{ return symbol(TokenNames.LBRACE);}
"}" 					{ return symbol(TokenNames.RBRACE);}
"+"  					{ return symbol(TokenNames.PLUS);}
"-"  					{ return symbol(TokenNames.MINUS);}
"∗"  					{ return symbol(TokenNames.TIMES);}
"/" 					{ return symbol(TokenNames.DIVIDE);}
"," 					{ return symbol(TokenNames.COMMA);}
"."  					{ return symbol(TokenNames.DOT);}
";" 					{ return symbol(TokenNames.SEMICOLON);}
"array"					{ return symbol(TokenNames.ARRAY);}
"class"					{ return symbol(TokenNames.CLASS);}
"extends"				{ return symbol(TokenNames.EXTENDS);}
"nil"					{ return symbol(TokenNames.NIL);}
"while"					{ return symbol(TokenNames.WHILE);}
"return"				{ return symbol(TokenNames.RETURN);}
"if"					{ return symbol(TokenNames.IF);}
"new"					{ return symbol(TokenNames.NEW);}

{ return symbol(TokenNames.INT(value) value is an integer
{ return symbol(TokenNames.STRING(value) value is a string
{ return symbol(TokenNames.ID(value) value is an identifier	
