import java.util.ArrayList;
import java.util.HashMap;

public class Lexer {
    public static HashMap<String, Token.TokenType> Characters = new HashMap<>();
    public ArrayList<Token> Tokens = new ArrayList<>();

    /** Populates A Hashmap with Keywords from Language Definition
     *
     */
    public static void PopulateHashmap() {
        // HashMap Functions
        Characters.put("begin", Token.TokenType.TOKEN_BEGIN);
        Characters.put("define", Token.TokenType.TOKEN_DEFINE);
        Characters.put("integer", Token.TokenType.TOKEN_INTEGER);
        Characters.put("variables", Token.TokenType.TOKEN_VARIABLES);
        Characters.put("constants", Token.TokenType.TOKEN_CONSTANTS);
        Characters.put(";", Token.TokenType.TOKEN_SEMICOLON);
        Characters.put("real", Token.TokenType.TOKEN_REALNUMBER);
        Characters.put("end", Token.TokenType.TOKEN_END);
        Characters.put("if", Token.TokenType.TOKEN_IF);
        Characters.put("then", Token.TokenType.TOKEN_THEN);
        Characters.put("else", Token.TokenType.TOKEN_ELSE);
        Characters.put("elsif", Token.TokenType.TOKEN_ELSIF);
        Characters.put("for", Token.TokenType.TOKEN_FOR);
        Characters.put("from", Token.TokenType.TOKEN_FROM);
        Characters.put("to", Token.TokenType.TOKEN_TO);
        Characters.put("while", Token.TokenType.TOKEN_WHILE);
        Characters.put("repeat", Token.TokenType.TOKEN_REPEAT);
        Characters.put("until", Token.TokenType.TOKEN_UNTIL);
        Characters.put("mod", Token.TokenType.TOKEN_MOD);
        Characters.put("var", Token.TokenType.TOKEN_VAR);
        Characters.put("true", Token.TokenType.TOKEN_TRUE);
        Characters.put("false", Token.TokenType.TOKEN_FALSE);
        Characters.put("string",Token.TokenType.TOKEN_STRING);
        Characters.put("character", Token.TokenType.TOKEN_CHAR);
        Characters.put("boolean", Token.TokenType.TOKEN_BOOLEAN);
    }

    /**
     * The Lex Method with a State Machine
     *
     * @param input String Code input
     * @return List of Initialized Tokens
     */
    public ArrayList Lex(String input) {
        PopulateHashmap();
        int State = 1;  // Secondary State for State Machine
        int comment = 0;    // Comment Character initalizer
        String PrimaryState = null;
        // Empty String to build a Number Value
        String Number = "";
        String Word = "";
        String StringValue = "";
        // String Builder to trim Spaces and adds a Newline Char to the end of the String
        // Trims Spaces to push characters together to ignore the spaces within String
        StringBuilder NewInput = new StringBuilder(input.trim());
        NewInput.append('\n');

        // Loop to Error check if there are balancing comments
        // If not return an Exception
        for (int i = 0; i < NewInput.length(); i++) {
            if (NewInput.charAt(i) == '(') {
                if (NewInput.charAt(i + 1) == '*') comment++;
            } else if (NewInput.charAt(i) == '*') {

                if (NewInput.charAt(i + 1) == ')') {
                    comment--;
                }
            } else continue;

        }
        // Conditional to check if comments are balanced
        if (comment != 0) {
            throw new RuntimeException("comment");
        }


        // Checks the first Character in the input and changes the state based on it
        if (Character.isLetter(NewInput.charAt(0))) {
            PrimaryState = "word";

        } else if (NewInput.charAt(0) == '(' && NewInput.charAt(1) == '*') {
            PrimaryState = "comment";
        } else if (NewInput.charAt(0) == ' ') {
            PrimaryState = "word";
        } else if (NewInput.charAt(0) == '\n') {
            PrimaryState = "\n";
        } else if (NewInput.charAt(0) == '\'') {
            PrimaryState = "Char";
        } else if (NewInput.charAt(0) == '"') {
            PrimaryState = "String";
        } else if (Character.isDigit(NewInput.charAt(0))) {
            PrimaryState = "number";
        } else {
            throw new RuntimeException("wrong");
        }
        // State Machine iterating String Characters
        // Creates a Token based on State and Case conditions
        // Specific rules that are violated throw an exception which the Shank Class catches
        for (int i = 0; i < NewInput.length(); i++) {

            switch (PrimaryState) {
                /** Character State used to construct a char
                 *
                 */
                case "Char":
                    String CharValue = "";
                    switch (State) {
                        case 1:
                            while (NewInput.charAt(i) != '\'') {
                                CharValue += NewInput.charAt(i);
                                State = 1;
                                i++;
                            }
                            if (CharValue.length() == 0 || CharValue.length() > 1) {
                                throw new RuntimeException("Incorrect Amount of Literals");
                            }
                            i--;
                        {
                            Tokens.add(new Token(Token.TokenType.TOKEN_CHARCONTENTS, CharValue));
                            State = 2;
                        }
                        break;
                        case 2:
                            if (Character.isLetter(NewInput.charAt(i))) {

                                PrimaryState = "word";
                                i--;
                                State = 1;
                            } else if (Character.isDigit(NewInput.charAt(i))) {
                                PrimaryState = "number";
                                i--;
                                State = 1;
                            }
                            else
                                State = 3;
                            break;
                        case 3:
                            switch (NewInput.charAt(i)) {
                                case '+', '-', '*', '/', '<', '>', '=', ' ':
                                    switch (NewInput.charAt(i)) {
                                        case ' ':
                                            break;
                                        case '=':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_EQUAL, "="));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '>':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATEREQUAL, ">="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATERTHAN, ">"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '<':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSEQUAL, "<="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else if (NewInput.charAt(i + 1) == '>') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_NOTEQUAL, "<>"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSTHAN, "<"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '+':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_plus, "+"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '*':
                                            if (NewInput.charAt(i - 1) == '(' && NewInput.charAt(i) == '*') {
                                                State = 1;
                                                break;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_multiply, "*"));
                                                PrimaryState = "operations";


                                                State = 1;
                                            }
                                            break;
                                        case '-': {
                                            if (NewInput.charAt(i + 1) == '-') {
                                                i++;
                                                Number += NewInput.charAt(i);
                                            }
                                            Tokens.add(new Token(Token.TokenType.TOKEN_minus, "-"));
                                            PrimaryState = "operations";
                                            State = 1;
                                        }
                                        break;
                                        case '/':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_divide, "/"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                    }
                            }
                    }
                    break;
                /** String State used to construct a String
                 *
                 */
                case "String":
                    StringValue = "";
                    switch (State) {
                        case 1:
                            char h = NewInput.charAt(i);
                            if(NewInput.charAt(i) =='\n')
                            {
                                NewInput.deleteCharAt(i);
                                PrimaryState = "word";
                                State = 1;
                                break;
                            }
                            if(h == '"')
                            {
                                i++;
                            }
                            while (NewInput.charAt(i) != '"') {

                                StringValue += NewInput.charAt(i);
                                State = 1;
                                i++;
                            }
                            i--;
                        {
                            Tokens.add(new Token(Token.TokenType.TOKEN_STRINGCONTENTS, StringValue));
                            State = 2;
                        }
                        break;
                        case 2:

                            if (Character.isLetter(NewInput.charAt(i))) {

                                PrimaryState = "word";
                                i--;
                                State = 1;
                            } else if (Character.isDigit(NewInput.charAt(i))) {
                                PrimaryState = "number";
                                i--;
                                State = 1;
                            }
                            else if (Character.isWhitespace(NewInput.charAt(i))) {
                                State = 2;
                            }
                            else
                                State = 3;
                            break;
                        case 3:
                            switch (NewInput.charAt(i)) {
                                case '+', '-', '*', '/', '<', '>', '=', ' ':
                                    switch (NewInput.charAt(i)) {
                                        case ' ':
                                            if(Character.isLetter(NewInput.charAt(i + 1)) && NewInput.charAt(i) != '"') {
                                                PrimaryState = "word";
                                                State = 1;
                                            }
                                            else if(Character.isDigit(NewInput.charAt(i+1))) {
                                                PrimaryState = "number";
                                                State = 1;
                                            }
                                            else {
                                                if(Character.isLetter(NewInput.charAt(i))) {
                                                    PrimaryState = "word";
                                                    State = 1;}
                                                else {
                                                    PrimaryState = "String";
                                                    State = 3;
                                                }
                                            }
                                            break;
                                        case '=':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_EQUAL, "="));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '>':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATEREQUAL, ">="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATERTHAN, ">"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '<':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSEQUAL, "<="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else if (NewInput.charAt(i + 1) == '>') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_NOTEQUAL, "<>"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSTHAN, "<"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '+':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_plus, "+"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '*':
                                            if (NewInput.charAt(i - 1) == '(' && NewInput.charAt(i) == '*') {
                                                State = 1;
                                                break;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_multiply, "*"));
                                                PrimaryState = "operations";


                                                State = 1;
                                            }
                                            break;
                                        case '-': {
                                            if (NewInput.charAt(i + 1) == '-') {
                                                i++;
                                                Number += NewInput.charAt(i);
                                            }
                                            Tokens.add(new Token(Token.TokenType.TOKEN_minus, "-"));
                                            PrimaryState = "operations";
                                            State = 1;
                                        }
                                        break;
                                        case '/':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_divide, "/"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                    }
                            }
                            break;
                    }
                    break;
                /** Operations State used as a transition between Operations whether it is math or boolean Operations
                 *
                 */
                case "operations":
                    switch (State) {
                        case 1:
                            Word = "";
                            if (Character.isLetter(NewInput.charAt(i + 1))) {
                                if(NewInput.charAt(i) == '"')
                                {

                                    PrimaryState = "String";
                                    State = 1;
                                }
                                else if (NewInput.charAt(i) == '\'')
                                {
                                    PrimaryState = "Char";
                                    State = 1;
                                }
                                else {
                                    Word += NewInput.charAt(i + 1);
                                    Word = "";
                                    PrimaryState = "word";
                                }

                            } else if (Character.isWhitespace(NewInput.charAt(i + 1))) {
                                i--;
                                if (Character.isLetter(NewInput.charAt(i + 2)) && NewInput.charAt(i+1) != '"') {
                                    PrimaryState = "word";
                                    State = 1;
                                }
                                if(NewInput.charAt(i+1) == '"')
                                {

                                    PrimaryState = "String";

                                    State = 1;
                                }
                                else if (NewInput.charAt(i+1) == '\'')
                                {
                                    PrimaryState = "Char";
                                    i++;
                                    State = 1;
                                }
                                else {
                                    PrimaryState = "number";
                                    State = 1;
                                }

                            }
                            if (Character.isDigit(NewInput.charAt(i + 1))) {
                            if(NewInput.charAt(i) == '"')
                            {

                                PrimaryState = "String";
                                State = 1;
                            }
                            else if (NewInput.charAt(i) == '\'')
                            {
                                PrimaryState = "Char";
                                State = 1;
                            }
                            else {
                                Word += NewInput.charAt(i + 1);
                                Word = "";
                                PrimaryState = "number";
                            }

                        }
                            break;
                    }
                    /**Assignment State used to transition an assignment from previous word State
                     *
                     */
                case "assignment":
                    switch (State) {
                        case 1:
                            if ((Character.isLetter(NewInput.charAt(i + 1))))
                            {

                                if(NewInput.charAt(i) == '"')
                                {
                                    PrimaryState = "String";
                                    State = 1;
                                }
                                else if (NewInput.charAt(i) == '\'')
                                {
                                    PrimaryState = "Char";
                                    State = 1;
                                }
                                else if (Character.isLetter(NewInput.charAt(i))) {
                                    if(NewInput.charAt(i - 2) == '"')
                                    {
                                        i--;
                                        PrimaryState = "String";
                                        State = 1;
                                    }
                                    else {
                                        Word += NewInput.charAt(i);
                                        PrimaryState = "word";
                                        State = 1;
                                    }
                                }
                                else {
                                    Word += NewInput.charAt(i);
                                    PrimaryState = "word";
                                    State = 1;
                                }
                            } else if (Character.isWhitespace(NewInput.charAt(i + 1))) {
                                PrimaryState = "assignment";
                                State = 1;
                            } else if (Character.isDigit(NewInput.charAt(i+1)) || NewInput.charAt(i) == '.') {
                                PrimaryState = "number";
                                State = 1;
                            }
                            else if(NewInput.charAt(i) == '"')
                            {
                                PrimaryState = "String";
                                State = 1;
                            }
                            else if (NewInput.charAt(i) == '\'') {
                                PrimaryState = "Char";
                                State = 1;
                            }
                            break;
                    }

                    // Comment State to allow and ignore Comments
                case "comment":
                    switch (State) {
                        case 1:
                            switch (NewInput.charAt(i)) {
                                case '*': {
                                    switch (NewInput.charAt(i + 1)) {
                                        case ')':

                                            State = 2;
                                            break;
                                    }
                                    break;
                                }
                                default:
                                    State = 1;
                                    break;
                            }
                            break;
                        case 2:
                            if (Character.isLetter(i)) {
                                Word += NewInput.charAt(i);
                                PrimaryState = "word";
                                State = 1;
                            } else if (Character.isWhitespace(NewInput.charAt(i))) {

                                PrimaryState = "word";
                            } else if (NewInput.charAt(i) == ':' || NewInput.charAt(i) == ',' || NewInput.charAt(i) == ')') {
                                State = 1;
                                PrimaryState = "word";
                            } else if (Character.isDigit(NewInput.charAt(i))) PrimaryState = "number";
                            else PrimaryState = "word";

                    }

                    break;
                // EmptyLine State to create emptyline token
                case "\n":
                    Tokens.add(new Token(Token.TokenType.TOKEN_EMPTYLINE, "EMPTYLINE"));
                    PrimaryState = null;
                    break;
                /** Word State used to construct KeyWords or Identifier Tokens
                 *
                 */
                case "word":
                    switch (State) {
                        // Used to seperate the unreserved words and reserved from each other
                        case 1:
                            while (Character.isLetterOrDigit(NewInput.charAt(i))) {
                                Word += NewInput.charAt(i);
                                i++;
                            }
                            i--;
                            Word = Word.trim();
                            if (Characters.containsKey(Word)) {
                                Tokens.add(new Token(Characters.get(Word), Word));
                                Word = "";
                                Number = "";
                                i++;
                                if (NewInput.charAt(i) == '\n') {
                                    Tokens.add(new Token(Token.TokenType.TOKEN_EOL, "EndOfLine"));

                                } else if (NewInput.charAt(i) == ')') {
                                    Tokens.add(new Token(Token.TokenType.TOKEN_RIGHTP, ")"));
                                } else if (NewInput.charAt(i) == ';') {
                                    Tokens.add(new Token(Token.TokenType.TOKEN_SEMICOLON, ";"));
                                } else if (Character.isDigit(NewInput.charAt(i + 1))) {

                                    State = 1;
                                    PrimaryState = "number";
                                }

                            } else if ((NewInput.charAt(i) == ',' || NewInput.charAt(i) == '(' || NewInput.charAt(i) == ')' || NewInput.charAt(i) == ' ' || NewInput.charAt(i) == ':' || NewInput.charAt(i) == ';' || NewInput.charAt(i) == '\n')) {
                                State = 2;
                            } else if (Character.isDigit(NewInput.charAt(i) )&& !Character.isLetter(NewInput.charAt(i-1))) {

                                Number = Word.substring(0, Word.length() - 1);
                                PrimaryState = "number";
                                i--;
                                break;
                            } else if (NewInput.charAt(i) == '"') {
                                PrimaryState = "String";
                            } else if (NewInput.charAt(i) == '\'') {
                                PrimaryState = "Char";
                            } else {
                                i--;
                                Tokens.add(new Token(Token.TokenType.TOKEN_Identifier, Word));
                                Word = "";
                                State = 2;
                            }
                            break;
                        case 2:
                            switch (NewInput.charAt(i)) {
                                case '+', '-', '*', '/', '<', '>', '=','\'','"':
                                    switch (NewInput.charAt(i)) {
                                        case '=':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_EQUAL, "="));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '>':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATEREQUAL, ">="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATERTHAN, ">"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '\'':
                                            PrimaryState = "Char";
                                            State = 1;
                                            break;
                                        case '"':
                                            PrimaryState = "String";
                                            State = 1;
                                            break;
                                        case '<':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSEQUAL, "<="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else if (NewInput.charAt(i + 1) == '>') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_NOTEQUAL, "<>"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSTHAN, "<"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '+':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_plus, "+"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '*':
                                            if (NewInput.charAt(i - 1) == '(' && NewInput.charAt(i) == '*') {
                                                State = 1;
                                                break;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_multiply, "*"));
                                                PrimaryState = "operations";


                                                State = 1;
                                            }
                                            break;
                                        case '-': {
                                            if (NewInput.charAt(i + 1) == '-') {
                                                i++;
                                                Number += NewInput.charAt(i);
                                            }
                                            Tokens.add(new Token(Token.TokenType.TOKEN_minus, "-"));
                                            PrimaryState = "operations";
                                            State = 1;
                                        }
                                        break;
                                        case '/':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_divide, "/"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                    }

                                case ' ', '\t':
                                    State = 1;
                                    break;
                                case '(':
                                    State = 3;
                                    break;
                                case ';':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_SEMICOLON, ";"));
                                    State = 1;
                                    break;
                                case ',':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_COMMA, ","));
                                    State = 1;
                                    PrimaryState = "operations";
                                    break;
                                case ':':
                                    if (NewInput.charAt(i) == ':' && NewInput.charAt(i + 1) == '=') {
                                        Tokens.add(new Token(Token.TokenType.TOKEN_ASSIGNMENT, "Assignment"));
                                        PrimaryState = "assignment";
                                        State = 1;
                                    } else {
                                        Tokens.add(new Token(Token.TokenType.TOKEN_COLON, ":"));
                                        State = 1;
                                        break;
                                    }
                                    break;
                                case ')':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_RIGHTP, ")"));
                                    State = 1;
                                    break;
                                case '\n':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_EOL, "EndOfLine"));
                                    State = 1;
                                    break;
                            }
                            break;
                        case 3:
                            if (NewInput.charAt(i - 1) == '(' && NewInput.charAt(i) == '*') {
                                PrimaryState = "comment";
                                State = 1;
                            } else
                            { i--;
                            Tokens.add(new Token(Token.TokenType.TOKEN_LEFTP, "("));
                            State = 1;}
                            break;

                    }
                    break;
                /** Number State used to construct Number Tokens
                 *
                 */
                case "number":
                    switch (State) {
                        case 1:
                            int Decimal = 0;
                            if(Character.isDigit(NewInput.charAt(i))) {
                                while ((Character.isDigit(NewInput.charAt(i))) || NewInput.charAt(i) == '.' || NewInput.charAt(i) == '+' || NewInput.charAt(i) == '-') {

                                    if (NewInput.charAt(i) == '.') {
                                        Decimal++;
                                        Number += NewInput.charAt(i);
                                        i++;
                                    } else {
                                        Number += NewInput.charAt(i);
                                        i++;
                                    }
                                }
                                if (Number.contains("-") || Number.contains("+")) {
                                    if (Number.charAt(0) != '-' || Number.charAt(0) != '+') {
                                        throw new RuntimeException("WRONGNNG");
                                    }
                                }
                                if (Decimal > 1) {
                                    throw new RuntimeException("TOO MANY DECIMALS");
                                }
                                i--;
                                Tokens.add(new Token(Token.TokenType.TOKEN_NUMBER, Number));
                                Number = "";
                                State = 2;
                            }
                            else State = 2;
                            break;
                        case 2:
                            if (Character.isLetter(i)) {
                                PrimaryState = "word";
                                State = 1;
                                break;
                            } else PrimaryState = "word";
                            switch (NewInput.charAt(i)) {
                                case '+', '-', '*', '/', '<', '>', '=':
                                    switch (NewInput.charAt(i)) {
                                        case '=':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_EQUAL, "="));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '>':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATEREQUAL, ">="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_GREATERTHAN, ">"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '<':
                                            if (NewInput.charAt(i + 1) == '=') {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSEQUAL, "<="));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else if (NewInput.charAt(i + 1) == '>') {
                                                i++;
                                                Tokens.add(new Token(Token.TokenType.TOKEN_NOTEQUAL, "<>"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_LESSTHAN, "<"));
                                                PrimaryState = "operations";
                                                State = 1;
                                            }
                                            break;
                                        case '+':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_plus, "+"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '*':
                                            if (NewInput.charAt(i - 1) == '(' && NewInput.charAt(i) == '*') {
                                                State = 1;
                                                break;
                                            } else {
                                                Tokens.add(new Token(Token.TokenType.TOKEN_multiply, "*"));
                                                PrimaryState = "operations";

                                                State = 1;
                                            }
                                            break;
                                        case '-':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_minus, "-"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                        case '/':
                                            Tokens.add(new Token(Token.TokenType.TOKEN_divide, "/"));
                                            PrimaryState = "operations";
                                            State = 1;
                                            break;
                                    }
                                case ' ', '\t':
                                    State = 1;
                                    break;
                                case '(':
                                    State = 3;
                                    break;
                                case ';':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_SEMICOLON, ";"));
                                    State = 1;
                                    break;
                                case ',':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_COMMA, ","));
                                    State = 1;
                                    PrimaryState = "operations";
                                    break;
                                case ':':
                                    if (NewInput.charAt(i) == ':' && NewInput.charAt(i + 1) == '=') {
                                        Tokens.add(new Token(Token.TokenType.TOKEN_ASSIGNMENT, "Assignment"));
                                        PrimaryState = "assignment";
                                        State = 1;
                                    } else {
                                        Tokens.add(new Token(Token.TokenType.TOKEN_COLON, ":"));
                                        State = 1;
                                        break;
                                    }
                                case ')':
                                    Tokens.add(new Token(Token.TokenType.TOKEN_RIGHTP, ")"));
                                    State = 1;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        Tokens.add(new Token(Token.TokenType.TOKEN_EOL, "EOL"));
        /**
         *  This loop is used to remove extra End of Line Tokens and EmptyLines the file may have
         */
        for (int i = 0; i < Tokens.size(); i++) {
            if (Tokens.get(i).Token == Token.TokenType.TOKEN_EOL) {
                i--;
                if (Tokens.get(i++).Token == Token.TokenType.TOKEN_EOL) Tokens.remove(i);

            } else if (Tokens.get(i).Token == Token.TokenType.TOKEN_EMPTYLINE) {
                Tokens.remove(i);
            } else continue;
        }
        return Tokens;
    }
}