/* Generated By:JavaCC: Do not edit this line. PythonParserConstants.java */
package net.sourceforge.pmd.lang.python.ast;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface PythonParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SPACE = 1;
  /** RegularExpression Id. */
  int CONTINUATION = 4;
  /** RegularExpression Id. */
  int NEWLINE = 5;
  /** RegularExpression Id. */
  int TRAILING_COMMENT = 6;
  /** RegularExpression Id. */
  int LPAREN = 7;
  /** RegularExpression Id. */
  int RPAREN = 8;
  /** RegularExpression Id. */
  int LBRACE = 9;
  /** RegularExpression Id. */
  int RBRACE = 10;
  /** RegularExpression Id. */
  int LBRACKET = 11;
  /** RegularExpression Id. */
  int RBRACKET = 12;
  /** RegularExpression Id. */
  int SEMICOLON = 13;
  /** RegularExpression Id. */
  int COMMA = 14;
  /** RegularExpression Id. */
  int DOT = 15;
  /** RegularExpression Id. */
  int COLON = 16;
  /** RegularExpression Id. */
  int PLUS = 17;
  /** RegularExpression Id. */
  int MINUS = 18;
  /** RegularExpression Id. */
  int MULTIPLY = 19;
  /** RegularExpression Id. */
  int DIVIDE = 20;
  /** RegularExpression Id. */
  int FLOORDIVIDE = 21;
  /** RegularExpression Id. */
  int POWER = 22;
  /** RegularExpression Id. */
  int LSHIFT = 23;
  /** RegularExpression Id. */
  int RSHIFT = 24;
  /** RegularExpression Id. */
  int MODULO = 25;
  /** RegularExpression Id. */
  int NOT = 26;
  /** RegularExpression Id. */
  int XOR = 27;
  /** RegularExpression Id. */
  int OR = 28;
  /** RegularExpression Id. */
  int AND = 29;
  /** RegularExpression Id. */
  int EQUAL = 30;
  /** RegularExpression Id. */
  int GREATER = 31;
  /** RegularExpression Id. */
  int LESS = 32;
  /** RegularExpression Id. */
  int EQEQUAL = 33;
  /** RegularExpression Id. */
  int EQLESS = 34;
  /** RegularExpression Id. */
  int EQGREATER = 35;
  /** RegularExpression Id. */
  int LESSGREATER = 36;
  /** RegularExpression Id. */
  int NOTEQUAL = 37;
  /** RegularExpression Id. */
  int PLUSEQ = 38;
  /** RegularExpression Id. */
  int MINUSEQ = 39;
  /** RegularExpression Id. */
  int MULTIPLYEQ = 40;
  /** RegularExpression Id. */
  int DIVIDEEQ = 41;
  /** RegularExpression Id. */
  int FLOORDIVIDEEQ = 42;
  /** RegularExpression Id. */
  int MODULOEQ = 43;
  /** RegularExpression Id. */
  int ANDEQ = 44;
  /** RegularExpression Id. */
  int OREQ = 45;
  /** RegularExpression Id. */
  int XOREQ = 46;
  /** RegularExpression Id. */
  int LSHIFTEQ = 47;
  /** RegularExpression Id. */
  int RSHIFTEQ = 48;
  /** RegularExpression Id. */
  int POWEREQ = 49;
  /** RegularExpression Id. */
  int OR_BOOL = 50;
  /** RegularExpression Id. */
  int AND_BOOL = 51;
  /** RegularExpression Id. */
  int NOT_BOOL = 52;
  /** RegularExpression Id. */
  int IS = 53;
  /** RegularExpression Id. */
  int IN = 54;
  /** RegularExpression Id. */
  int LAMBDA = 55;
  /** RegularExpression Id. */
  int IF = 56;
  /** RegularExpression Id. */
  int ELSE = 57;
  /** RegularExpression Id. */
  int ELIF = 58;
  /** RegularExpression Id. */
  int WHILE = 59;
  /** RegularExpression Id. */
  int FOR = 60;
  /** RegularExpression Id. */
  int TRY = 61;
  /** RegularExpression Id. */
  int EXCEPT = 62;
  /** RegularExpression Id. */
  int DEF = 63;
  /** RegularExpression Id. */
  int CLASS = 64;
  /** RegularExpression Id. */
  int FINALLY = 65;
  /** RegularExpression Id. */
  int PRINT = 66;
  /** RegularExpression Id. */
  int PASS = 67;
  /** RegularExpression Id. */
  int BREAK = 68;
  /** RegularExpression Id. */
  int CONTINUE = 69;
  /** RegularExpression Id. */
  int RETURN = 70;
  /** RegularExpression Id. */
  int YIELD = 71;
  /** RegularExpression Id. */
  int IMPORT = 72;
  /** RegularExpression Id. */
  int FROM = 73;
  /** RegularExpression Id. */
  int DEL = 74;
  /** RegularExpression Id. */
  int RAISE = 75;
  /** RegularExpression Id. */
  int GLOBAL = 76;
  /** RegularExpression Id. */
  int EXEC = 77;
  /** RegularExpression Id. */
  int ASSERT = 78;
  /** RegularExpression Id. */
  int AS = 79;
  /** RegularExpression Id. */
  int WITH = 80;
  /** RegularExpression Id. */
  int AT = 81;
  /** RegularExpression Id. */
  int NAME = 82;
  /** RegularExpression Id. */
  int LETTER = 83;
  /** RegularExpression Id. */
  int DECNUMBER = 84;
  /** RegularExpression Id. */
  int HEXNUMBER = 85;
  /** RegularExpression Id. */
  int OCTNUMBER = 86;
  /** RegularExpression Id. */
  int BINNUMBER = 87;
  /** RegularExpression Id. */
  int FLOAT = 88;
  /** RegularExpression Id. */
  int COMPLEX = 89;
  /** RegularExpression Id. */
  int EXPONENT = 90;
  /** RegularExpression Id. */
  int DIGIT = 91;
  /** RegularExpression Id. */
  int SINGLE_STRING = 104;
  /** RegularExpression Id. */
  int SINGLE_STRING2 = 105;
  /** RegularExpression Id. */
  int TRIPLE_STRING = 106;
  /** RegularExpression Id. */
  int TRIPLE_STRING2 = 107;
  /** RegularExpression Id. */
  int SINGLE_BSTRING = 108;
  /** RegularExpression Id. */
  int SINGLE_BSTRING2 = 109;
  /** RegularExpression Id. */
  int TRIPLE_BSTRING = 110;
  /** RegularExpression Id. */
  int TRIPLE_BSTRING2 = 111;
  /** RegularExpression Id. */
  int SINGLE_USTRING = 112;
  /** RegularExpression Id. */
  int SINGLE_USTRING2 = 113;
  /** RegularExpression Id. */
  int TRIPLE_USTRING = 114;
  /** RegularExpression Id. */
  int TRIPLE_USTRING2 = 115;
  /** RegularExpression Id. */
  int DEFINE_FUNCTION = 199;
  
  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_STRING11 = 1;
  /** Lexical state. */
  int IN_STRING21 = 2;
  /** Lexical state. */
  int IN_STRING13 = 3;
  /** Lexical state. */
  int IN_STRING23 = 4;
  /** Lexical state. */
  int IN_BSTRING11 = 5;
  /** Lexical state. */
  int IN_BSTRING21 = 6;
  /** Lexical state. */
  int IN_BSTRING13 = 7;
  /** Lexical state. */
  int IN_BSTRING23 = 8;
  /** Lexical state. */
  int IN_USTRING11 = 9;
  /** Lexical state. */
  int IN_USTRING21 = 10;
  /** Lexical state. */
  int IN_USTRING13 = 11;
  /** Lexical state. */
  int IN_USTRING23 = 12;
  /** Lexical state. */
  int IN_STRING1NLC = 13;
  /** Lexical state. */
  int IN_STRING2NLC = 14;
  /** Lexical state. */
  int IN_USTRING1NLC = 15;
  /** Lexical state. */
  int IN_USTRING2NLC = 16;
  /** Lexical state. */
  int IN_BSTRING1NLC = 17;
  /** Lexical state. */
  int IN_BSTRING2NLC = 18;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\f\"",
    "<CONTINUATION>",
    "<NEWLINE>",
    "<TRAILING_COMMENT>",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\":\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"//\"",
    "\"**\"",
    "\"<<\"",
    "\">>\"",
    "\"%\"",
    "\"~\"",
    "\"^\"",
    "\"|\"",
    "\"&\"",
    "\"=\"",
    "\">\"",
    "\"<\"",
    "\"==\"",
    "\"<=\"",
    "\">=\"",
    "\"<>\"",
    "\"!=\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"//=\"",
    "\"%=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"<<=\"",
    "\">>=\"",
    "\"**=\"",
    "\"or\"",
    "\"and\"",
    "\"not\"",
    "\"is\"",
    "\"in\"",
    "\"lambda\"",
    "\"if\"",
    "\"else\"",
    "\"elif\"",
    "\"while\"",
    "\"for\"",
    "\"try\"",
    "\"except\"",
    "\"def\"",
    "\"class\"",
    "\"finally\"",
    "\"print\"",
    "\"pass\"",
    "\"break\"",
    "\"continue\"",
    "\"return\"",
    "\"yield\"",
    "\"import\"",
    "\"from\"",
    "\"del\"",
    "\"raise\"",
    "\"global\"",
    "\"exec\"",
    "\"assert\"",
    "\"as\"",
    "\"with\"",
    "\"@\"",
    "<NAME>",
    "<LETTER>",
    "<DECNUMBER>",
    "<HEXNUMBER>",
    "<OCTNUMBER>",
    "<BINNUMBER>",
    "<FLOAT>",
    "<COMPLEX>",
    "<EXPONENT>",
    "<DIGIT>",
    "<token of kind 92>",
    "<token of kind 93>",
    "<token of kind 94>",
    "<token of kind 95>",
    "<token of kind 96>",
    "<token of kind 97>",
    "<token of kind 98>",
    "<token of kind 99>",
    "<token of kind 100>",
    "<token of kind 101>",
    "<token of kind 102>",
    "<token of kind 103>",
    "\"\\\'\"",
    "\"\\\"\"",
    "\"\\\'\\\'\\\'\"",
    "\"\\\"\\\"\\\"\"",
    "\"\\\'\"",
    "\"\\\"\"",
    "\"\\\'\\\'\\\'\"",
    "\"\\\"\\\"\\\"\"",
    "\"\\\'\"",
    "\"\\\"\"",
    "\"\\\'\\\'\\\'\"",
    "\"\\\"\\\"\\\"\"",
    "\"\\\\\\r\\n\"",
    "<token of kind 117>",
    "\"\\\\\\r\\n\"",
    "<token of kind 119>",
    "\"\\\\\\r\\n\"",
    "<token of kind 121>",
    "\"\\\\\\r\\n\"",
    "<token of kind 123>",
    "\"\\\\\\r\\n\"",
    "<token of kind 125>",
    "\"\\\\\\r\\n\"",
    "<token of kind 127>",
    "\"\"",
    "\"\"",
    "\"\"",
    "\"\"",
    "\"\"",
    "\"\"",
    "<token of kind 134>",
    "<token of kind 135>",
    "\"\\r\\n\"",
    "\"\\n\"",
    "\"\\r\"",
    "<token of kind 139>",
    "<token of kind 140>",
  };

}
