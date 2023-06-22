
class Token
{
    //Instances
    public TokenType Token;
    public String Value;

    /** Token Constructor
     *
     * @param Token The TokenType
     * @param Value The String Value of the Token
     */
    public Token(TokenType Token,String Value)
{
    this.Token = Token;
    this.Value = Value;
}
    // Token Type Enum
    public enum TokenType {

        TOKEN_NUMBER(""),
        TOKEN_plus("PLUS"),
        TOKEN_minus("MINUS"),
        TOKEN_multiply("MULTIPLY"),
        TOKEN_divide("DIVIDE"),
        TOKEN_EOL("EndOfLine"),
        TOKEN_LEFTP("("),
        TOKEN_RIGHTP(")"),
        TOKEN_Identifier("ID"),
        TOKEN_DEFINE("Define"),
        TOKEN_INTEGER("Integer"),
        TOKEN_REALNUMBER("Real Number"),
        TOKEN_BEGIN("Begin"),
        TOKEN_END("End"),
        TOKEN_SEMICOLON("'"),
        TOKEN_COLON("Colon"),
        TOKEN_EQUAL("="),
        TOKEN_COMMA(","),
        TOKEN_VARIABLES("Variable"),
        TOKEN_CONSTANTS("Constant"),
        TOKEN_EMPTYLINE("EmptyLine"),
        TOKEN_ASSIGNMENT("Assignment"),
        TOKEN_IF("If"),
        TOKEN_THEN("then"),
        TOKEN_ELSE("else"),
        TOKEN_ELSIF("elsif"),
        TOKEN_FOR("for"),
        TOKEN_FROM("from"),
        TOKEN_TO("To"),
        TOKEN_WHILE("while"),
        TOKEN_REPEAT("repeat"),
        TOKEN_UNTIL("until"),
        TOKEN_MOD("mod"),
        TOKEN_GREATERTHAN(">"),
        TOKEN_LESSTHAN("<"),
        TOKEN_GREATEREQUAL(">="),
        TOKEN_LESSEQUAL("<="),
        TOKEN_NOTEQUAL("<>"),
        TOKEN_VAR("var"),
        TOKEN_TRUE("true"),
        TOKEN_FALSE("false"),
        TOKEN_STRING("string"),
        TOKEN_CHAR("char"),
        TOKEN_BOOLEAN("Boolean"),
        TOKEN_STRINGCONTENTS(""),
        TOKEN_CHARCONTENTS("");



        // Variable
        private final String Value;

        //Constructor for the Tokens
         TokenType(String Value) {
            this.Value = Value;
        }
    }

    /** Token Accessor
     *
     * @return Token
     */
    public TokenType getToken() {
        return Token;
    }

    /** Value Accessor
     *
     * @return String Value
     */
    public String getValue() {
        return Value;
    }

    /** To String Method
     *
     * @return String of the Token Type and the Token's Value
     */
    @Override
    public String toString() {
        return "(" + getToken() + ")" +
                "(Value= " + getValue() +")";
    }
}