import java.util.HashMap;

public interface TokenNames {
  /* terminals */
  public static final int MINUS = 3;
  public static final int DIVIDE = 5;
  public static final int NUMBER = 9;
  public static final int EOF = 0;
  public static final int PLUS = 2;
  public static final int ID = 10;
  public static final int error = -1;
  public static final int SEMICOLON = 8;
  public static final int RPAREN = 7;
  public static final int TIMES = 4;
  public static final int LPAREN = 6;
  public static final int ASSIGN = 11;
  public static final int EQ = 12;
  public static final int LBRACK = 13;
  public static final int LT = 14;
  public static final int RBRACK = 15;
  public static final int GT = 16;
  public static final int LBRACE = 17;
  public static final int RBRACE = 18;
  public static final int COMMA = 19;
  public static final int DOT = 20;
  public static final int ARRAY = 21;
  public static final int CLASS = 22;
  public static final int EXTENDS = 23;
  public static final int NIL = 24;
  public static final int WHILE = 25;
  public static final int RETURN = 26;
  public static final int IF = 27;
  public static final int NEW = 28;
  public static final int STRING = 30;
  public static final int COMMENT = 31;

  public static String numToName(int num){
    switch (num){
      case MINUS: return "MINUS";
      case DIVIDE: return "DIVIDE";
      case NUMBER: return "NUMBER";
      case EOF: return "EOF";
      case PLUS: return "PLUS";
      case ID: return "ID";
      case error: return "error";
      case SEMICOLON: return "SEMICOLON";
      case RPAREN: return "RPAREN";
      case TIMES: return "TIMES";
      case LPAREN: return "LPAREN";
      case ASSIGN: return "ASSIGN";
      case EQ: return "EQ";
      case LBRACK: return "LBRACK";
      case LT: return "LT";
      case GT: return "GT";
      case RBRACK: return "RBRACK";
      case LBRACE: return "LBRACE";
      case RBRACE: return "RBRACE";
      case COMMA: return "COMMA";
      case DOT: return "DOT";
      case ARRAY: return "ARRAY";
      case CLASS: return "CLASS";
      case EXTENDS: return "EXTENDS";
      case NIL: return "NIL";
      case WHILE: return "WHILE";
      case RETURN: return "RETURN";
      case IF: return "IF";
      case NEW: return "NEW";
      case STRING: return "STRING";
      case COMMENT: return "COMMENT";
      default: return null;

    }
  }
}
