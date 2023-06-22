import java.util.ArrayList;
import java.util.LinkedList;

public class Parser {
    public Token Next;// Sets a pointer to the Next Token
    public ArrayList<Token> Lexemes;
    public int i = 0;   // Index for the ArrayList of Tokens
    public boolean isConstant;
    private Token Current;
    private FunctionNode Root;      // Root Node
    public Token last;
    public Parser(ArrayList Tokens) {
        Lexemes = Tokens;
        // Removes all Emptylines within  the ArrayList
        for (int i = 0; i < Lexemes.size(); i++) {
            Token CurrentToken = Lexemes.get(i);
            if (CurrentToken.Token == Token.TokenType.TOKEN_EMPTYLINE) {
                Lexemes.remove(i);

            }

        }


    }

    /**
     * The Parse Method
     *
     * @return The Root Node of the AST
     */
    public ArrayList parse() {
        // Call the Expression
        ArrayList Functions = new ArrayList<>();
        Root = FunctionDefinition();
        while (Root != null) {
            Functions.add(Root);
            Root = FunctionDefinition();
            if (Lexemes.isEmpty() || Root == null) {
                Functions.add(Root);
                return Functions;
            }

        }
        return Functions;
    }

    /**
     * Helper Method that helps Scan and point to the Next Token within the list of Lexemes
     * Removes the Current Token from the list
     *
     * @return The Next Token in the List
     */
    public Token MatchAndRemove(Token.TokenType T) {
        if (Lexemes.isEmpty()) {
            return null;
        }
        Next = Lexemes.get(i);
        Current = Lexemes.get(0);
        if (T == Next.Token) {
            Lexemes.remove(i);

            return Next;
        } else
            return null;
    }


    /**
     * The Factor Method used to create a Factor
     *
     * @return A factor Node or return null if a Factor does not exist
     */
    public node Factor() {

        if (MatchAndRemove(Token.TokenType.TOKEN_NUMBER) != null) {
            if (Next.getValue().contains(".")) {
                return new FloatNode(Float.parseFloat(Next.Value));
            } else {
                return new IntegerNode(Integer.parseInt(Next.Value));
            }
        } else if (MatchAndRemove(Token.TokenType.TOKEN_LEFTP) != null) {
            node Node = Expression();
            if (Node == null) {
                return null;
            }
            if (MatchAndRemove(Token.TokenType.TOKEN_RIGHTP) != null) {
                return Node;
            } else
                return null;
        }else if (MatchAndRemove(Token.TokenType.TOKEN_TRUE)!= null)
        {
            boolean BoolValue = true;
            return new BoolNode(BoolValue);
        }
        else if (MatchAndRemove(Token.TokenType.TOKEN_FALSE)!= null)
        {
            boolean BoolValue = false;
            return new BoolNode(BoolValue);
        }
        else if (MatchAndRemove(Token.TokenType.TOKEN_STRINGCONTENTS)!= null)
        {
            String ID = Next.Value;
            return new StringNode(ID);
        }
        else if (MatchAndRemove(Token.TokenType.TOKEN_CHARCONTENTS)!= null)
        {
            String ID = Next.Value;
            return new CharNode(ID);
        }
        else if ((MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null)) {
            String ID = Next.Value;
            return new VariableReference(ID);
        } else if ((MatchAndRemove(Token.TokenType.TOKEN_minus) != null)) {
            String NegativeBind = "";
            NegativeBind += Next.Value;
            if (MatchAndRemove(Token.TokenType.TOKEN_NUMBER) != null) {
                NegativeBind += Next.Value;
                if (Next.getValue().contains(".")) {

                    return new FloatNode(Float.parseFloat(NegativeBind));
                } else {
                    return new IntegerNode(Integer.parseInt(NegativeBind));
                }
            }
            return null;
        }
        return null;
    }

    /**
     * The Term Recursive Method to create Multiplication or Division Expression
     *
     * @return either return a term or returns a Factor if a term does not exist
     */
    public node Term() {
        node Child1 = Factor();
        {// Factor Node
            if (MatchAndRemove(Token.TokenType.TOKEN_multiply) != null) {
                return new MathOpNode(MathOpNode.Operations.Multiply, Child1, Term());
            } else if (MatchAndRemove(Token.TokenType.TOKEN_divide) != null) {
                return new MathOpNode(MathOpNode.Operations.Divide, Child1, Term());
            } else if (MatchAndRemove(Token.TokenType.TOKEN_MOD) != null) {
                return new MathOpNode(MathOpNode.Operations.Modulo, Child1,Term());
            }
            else
                return Child1;
        }
    }


    /**
     * The Expression Recursive Method is used to create Addition or Subtraction Node
     *
     * @return A Expression Node or returns a Term Node if an Expression does not exist
     */
    public node Expression() {
        node Child1 = Term();
         //Term Node
            if (MatchAndRemove(Token.TokenType.TOKEN_plus) != null) {

                return new MathOpNode(MathOpNode.Operations.Add, Child1, Expression());
            } else if (MatchAndRemove(Token.TokenType.TOKEN_minus) != null) {
                return new MathOpNode(MathOpNode.Operations.Subtract, Child1, Expression());
            }
            else
                return Child1;

    }

    /** This Method Serves as the final layer of recursion for the expressions
     *
     * @return the final Expression Node
     */
    public node FinalExpression() {
        node Child1 = Expression();
        if (MatchAndRemove(Token.TokenType.TOKEN_EQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_GREATEREQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_GREATERTHAN) != null || MatchAndRemove(Token.TokenType.TOKEN_LESSTHAN) != null || MatchAndRemove(Token.TokenType.TOKEN_LESSEQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_NOTEQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_EQUAL) != null) {
            Token.TokenType Condition = Next.Token;
            node Right = Expression();
            return new BooleanExpressionNode(Child1, Right, Condition);
        }
        else
        return Child1;
    }


    /**
     * The Boolean Expression Method used to test a Boolean statement
     *
     * @return A Boolean Expression Node with a left/right expression and condition
     */
    public BooleanExpressionNode booleanExpression(node Left) {
        if (MatchAndRemove(Token.TokenType.TOKEN_EQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_GREATEREQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_GREATERTHAN) != null || MatchAndRemove(Token.TokenType.TOKEN_LESSTHAN) != null || MatchAndRemove(Token.TokenType.TOKEN_LESSEQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_NOTEQUAL) != null || MatchAndRemove(Token.TokenType.TOKEN_EQUAL) != null) {
            Token.TokenType Condition = Next.Token;
            node Right = Expression();

            return new BooleanExpressionNode(Left, Right, Condition);

        }
        else if(Lexemes.get(0).Token == Token.TokenType.TOKEN_EOL || Lexemes.get(0).Token == Token.TokenType.TOKEN_THEN)
        {
            return new BooleanExpressionNode(Left);
        }
        return null;
    }


    /**
     * The extra list used to chain together an If statement
     *
     * @param IfList
     * @return
     */
    public LinkedList<node> If(LinkedList<node> IfList) {
        LinkedList If = IfList;
        LinkedList IfChain = new LinkedList<>();
        for (int i = 0; i < If.size(); i++) {
            for (int j = i + 1; j < If.size(); j++) {
                IfChain.add(new IfNode((node) If.get(i), (node) If.get(j)));
                break;
            }
        }
        IfChain.add(new elseNode(null));
        return IfChain;
    }

    /**
     * The Method used create a List of If statements that will be called into IF function to chain the statements together
     *
     * @return A chained List of if- elsif- else Nodes
     */
    public IfNode IfExpression() {
        LinkedList<node> IfStatement = new LinkedList<>();
        IfNode If = null;
        if (MatchAndRemove(Token.TokenType.TOKEN_IF) != null) {
            node Left = Expression();
            BooleanExpressionNode BooleanExpression = booleanExpression(Left);
            if (MatchAndRemove(Token.TokenType.TOKEN_THEN) != null) {
                if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                    ArrayList Statements = Statements();
                    IfNode If1 = new IfNode(BooleanExpression, Statements);
                    IfStatement.add(If1);
                    if (MatchAndRemove(Token.TokenType.TOKEN_ELSE) != null) {
                        if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                            ArrayList Statement = Statements();
                            If(IfStatement);
                            IfStatement.add(new elseNode(Statement));
                            return new IfNode((IfStatement));
                        }
                    } else
                        while (MatchAndRemove(Token.TokenType.TOKEN_ELSIF) != null) {
                            Left = Expression();
                            BooleanExpressionNode ElseBoolean = booleanExpression(Left);
                            if (MatchAndRemove(Token.TokenType.TOKEN_THEN) != null) {
                                if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                                    ArrayList NewStatements = Statements();
                                    If1 = new IfNode(ElseBoolean, NewStatements);
                                    IfStatement.add(If1);
                                    if (MatchAndRemove(Token.TokenType.TOKEN_ELSE) != null)
                                        if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                                            ArrayList Statement = Statements();
                                            If(IfStatement);
                                            IfStatement.add(new elseNode(Statement));
                                            return new IfNode(IfStatement);
                                        }
                                }
                            }

                        }
                    return new IfNode(IfStatement);
                }
                return new IfNode(If(IfStatement));
            }
        }
        return null;
    }

    /**
     * The Method used to Parse a while expression
     *
     * @return A while Node
     */
    public whileNode whileExpression() {
        if (MatchAndRemove(Token.TokenType.TOKEN_WHILE) != null) {
                node Left = Expression();
                BooleanExpressionNode BooleanExpression = booleanExpression(Left);
                if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                    ArrayList Statements = Statements();
                    return new whileNode(BooleanExpression, Statements);
                }
            }
        return null;
    }

    /**
     * The Repeat parse method used to parse a Repeat loop statement
     *
     * @return a Repeat Node
     */
    public RepeatNode repeatExpression() {
        if (MatchAndRemove(Token.TokenType.TOKEN_REPEAT) != null) {
            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                ArrayList Statements = Statements();
                if (MatchAndRemove(Token.TokenType.TOKEN_UNTIL) != null) {
                    node Left = Expression();
                    BooleanExpressionNode BooleanExpression = booleanExpression(Left);
                    if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null)
                        return new RepeatNode(BooleanExpression, Statements);
                }
            } else return null;
        }
        return null;
    }

    /**
     * A For parse Method used to parse a For loop statement
     *
     * @return
     */
    public node ForExpression() {
        if (MatchAndRemove(Token.TokenType.TOKEN_FOR) != null) {
            node IntegerVariable = Expression();
            if (MatchAndRemove(Token.TokenType.TOKEN_FROM) != null) {
                node leftValue = Expression();
                if (MatchAndRemove(Token.TokenType.TOKEN_TO) != null) {
                    node rightValue = Expression();
                    if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                        ArrayList Statements = Statements();
                        return new ForNode(IntegerVariable, leftValue, rightValue, Statements);
                    }
                }
            }
        }
        return null;
    }


    /**
     * The FunctionDefinition constructs a function from the Token list recursively decending through 3 other functions
     *
     * @return Function Node to be used as a root
     */
    public FunctionNode FunctionDefinition() {
        boolean isVar = false;
        if (MatchAndRemove(Token.TokenType.TOKEN_DEFINE) != null) {
            if (MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null) {
                String ID = Next.Value;
                if (MatchAndRemove(Token.TokenType.TOKEN_LEFTP) != null) {
                    ArrayList Parameters = VariableParameter();

                    if (MatchAndRemove(Token.TokenType.TOKEN_RIGHTP) != null) {

                        if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                            ArrayList Constants = Constants();
                            if (Constants == null) {

                            }

                            ArrayList Variables = VariableDeclarations();
                            if (Variables == null) {

                            }
                            ArrayList Statements = Statements();
                            if (Statements == null) {

                            }
                            return new FunctionNode(ID, Parameters, isVar, Constants, Variables, Statements);
                        }
                    } else return null;

                } else
                    return null;
            }
            return null;
        }
        return null;
    }

    /**
     * This Method creates variables within the parameters of the Function header
     * This method is called in Function Definition
     *
     * @return a Variable Node List with DataTypes and Identifiers
     */
    public ArrayList<node> VariableParameter() {
        ArrayList<node> VariableList = new ArrayList<>();
        ArrayList<String> VariableNames = new ArrayList();
        ArrayList<Boolean> isVa = new ArrayList<>();
        boolean isVar = false;
        boolean isConstant = false;
        while (Next.Token != Token.TokenType.TOKEN_RIGHTP) {
            isVar = false;
            if (MatchAndRemove(Token.TokenType.TOKEN_VAR) != null) {
                isVar = true;
                isVa.add(isVar);
            }
            if (MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null) {
                isVar = false;
                isVa.add(isVar);
                String ID = Next.Value;
                VariableNames.add(ID);
                while ((MatchAndRemove(Token.TokenType.TOKEN_COMMA) != null) || MatchAndRemove(Token.TokenType.TOKEN_SEMICOLON) != null) {
                    isVar = false;
                    if (MatchAndRemove(Token.TokenType.TOKEN_VAR) != null) {
                        isVar = true;
                        isVa.add(isVar);

                    }
                    else {
                        isVar = false;
                        isVa.add(isVar);
                    }

                    if (MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null) {
                        ID = Next.Value;
                        VariableNames.add(ID);
                    }

                    if (MatchAndRemove(Token.TokenType.TOKEN_COLON) != null) {
                        if (MatchAndRemove(Token.TokenType.TOKEN_INTEGER) != null) {

                            for (int i = 0; i < VariableNames.size(); i++) {
                                VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.integer, isVa.get(i), isConstant));
                            }
                        } else if (MatchAndRemove(Token.TokenType.TOKEN_REALNUMBER) != null) {
                            for (int i = 0; i < VariableNames.size(); i++) {
                                VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.real, isVa.get(i), isConstant));
                            }


                        }
                        else if (MatchAndRemove(Token.TokenType.TOKEN_STRING) != null) {
                            for (int i = 0; i < VariableNames.size(); i++) {
                                VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.string, isVa.get(i), isConstant));
                            }

                        }else if (MatchAndRemove(Token.TokenType.TOKEN_CHAR) != null) {
                            for (int i = 0; i < VariableNames.size(); i++) {
                                VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.Char, isVa.get(i), isConstant));
                            }

                        }else if (MatchAndRemove(Token.TokenType.TOKEN_BOOLEAN) != null) {
                            for (int i = 0; i < VariableNames.size(); i++) {
                                VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.Boolean, isVa.get(i), isConstant));
                            }

                        }
                    }
                }
                if (MatchAndRemove(Token.TokenType.TOKEN_COLON) != null) {
                    if (isVar == false)
                        isConstant = true;
                    if (MatchAndRemove(Token.TokenType.TOKEN_REALNUMBER) != null) {

                        VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.real, isVa.get(i), isConstant));
                        isVa.remove(0);

                    }

                    else if (MatchAndRemove(Token.TokenType.TOKEN_INTEGER) != null) {
                        VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.integer, isVa.get(i), isConstant));
                        isVa.remove(0);

                    }else if (MatchAndRemove(Token.TokenType.TOKEN_STRING) != null) {
                        {
                            VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.string, isVa.get(i), isConstant));
                            isVa.remove(0);

                        }

                    }else if (MatchAndRemove(Token.TokenType.TOKEN_CHAR) != null) {
                        {
                            VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.Char, isVa.get(i), isConstant));
                            isVa.remove(0);
                        }

                    }else if (MatchAndRemove(Token.TokenType.TOKEN_BOOLEAN) != null) {
                        {
                            VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.Boolean, isVa.get(i), isConstant));
                            isVa.remove(0);
                        }
                    }
                    MatchAndRemove(Token.TokenType.TOKEN_SEMICOLON);
                    VariableNames.clear();

                }

            }
        }
        return VariableList;
    }

    /**
     * The Variable Declarations Method used to declare methods with DataType and Identifiers
     * This Method is recursively called in Function Definition
     *
     * @return Variable Node if it exists
     */
    public ArrayList<node> VariableDeclarations() {
        ArrayList<node> VariableList = new ArrayList<>();
        ArrayList<String> VariableNames = new ArrayList<>();
        boolean isVar = false;
        if (MatchAndRemove(Token.TokenType.TOKEN_VARIABLES) != null) {
            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                while ((MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null)) {

                    String ID = Next.Value;
                    VariableNames.add(ID);
                    if (MatchAndRemove(Token.TokenType.TOKEN_COMMA) != null) {

                    }

                    if (MatchAndRemove(Token.TokenType.TOKEN_COLON) != null) {
                        if (MatchAndRemove(Token.TokenType.TOKEN_INTEGER) != null) {
                            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {

                                for (int i = 0; i < VariableNames.size(); i++) {
                                    VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.integer, isVar, isConstant));
                                }
                                VariableNames.clear();
                            }
                        } else if (MatchAndRemove(Token.TokenType.TOKEN_REALNUMBER) != null) {
                            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                                for (int i = 0; i < VariableNames.size(); i++) {
                                    VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.real, isVar, isConstant));
                                }
                                VariableNames.clear();
                            }
                        } else if (MatchAndRemove(Token.TokenType.TOKEN_CHAR) != null) {
                            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                                for (int i = 0; i < VariableNames.size(); i++) {
                                    VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.Char, isVar, isConstant));
                                }
                                VariableNames.clear();
                            }
                        } else if (MatchAndRemove(Token.TokenType.TOKEN_STRING) != null) {
                            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                                for (int i = 0; i < VariableNames.size(); i++) {
                                    VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.string, isVar, isConstant));
                                }
                                VariableNames.clear();
                            }
                        }else if (MatchAndRemove(Token.TokenType.TOKEN_BOOLEAN) != null) {
                            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                                for (int i = 0; i < VariableNames.size(); i++) {
                                    VariableList.add(new VariableNode(VariableNames.get(i), VariableNode.DataType.Boolean, isVar, isConstant));
                                }
                                VariableNames.clear();
                            }
                        } else
                            throw new RuntimeException("No Data Type Initalized");
                    }
                }
            }
        } else
            throw new RuntimeException("Incorrect Variables Declaration");
        return VariableList;
    }

    /**
     * This Method process constants with an identifier and a Number value
     * This Method is recursively called in Constants
     *
     * @return a List of Variable Nodes as Constants
     */
    public node processConstants() {
        node Constant = null;
        if (MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null) {
            String ID = Next.Value;
            if (MatchAndRemove(Token.TokenType.TOKEN_EQUAL) != null) {
                node Number = Expression();
                if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {


                    if (Number instanceof FloatNode) {
                        isConstant = true;
                        Constant = (new VariableNode(ID, Number, isConstant, VariableNode.DataType.real));
                    }
                    if (Number instanceof StringNode) {
                        isConstant = true;
                        Constant = (new VariableNode(ID, Number, isConstant, VariableNode.DataType.string));
                    }  if (Number instanceof IntegerNode) {
                        isConstant = true;
                        Constant = (new VariableNode(ID, Number, isConstant, VariableNode.DataType.integer));
                    }  if (Number instanceof CharNode) {
                        isConstant = true;
                        Constant = (new VariableNode(ID, Number, isConstant, VariableNode.DataType.Char));
                    }  if (Number instanceof BoolNode) {
                        isConstant = true;
                        Constant = (new VariableNode(ID, Number, isConstant, VariableNode.DataType.Boolean));
                    }


                } else
                    throw new RuntimeException("No Constant was inputted");
            } else
                throw new RuntimeException("Incorrect Syntax for Constant declaration");
        }

        return Constant;
    }

    /**
     * This Method is used to parse Constants
     * This Method is recursively called in function definition
     *
     * @return Variable Node List of Constants
     */
    public ArrayList<node> Constants() {
        ArrayList Constants = new ArrayList();
        if (MatchAndRemove(Token.TokenType.TOKEN_CONSTANTS) != null) {
            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                node Constant = processConstants();
                while (Constant != null) {

                    Constants.add(Constant);
                    Constant = processConstants();
                }
                return Constants;
            }
        } else if (Next.Token == Token.TokenType.TOKEN_VARIABLES) {
            return Constants;
        }


        return Constants;
    }

    /**
     * This Method binds an assignment to with an Identifier and Expression
     * If violated, return an execption
     *
     * @return An Assignment Node with a Variable Reference Node and Expression Node
     */
    public node Assignment() {

        if (Lexemes.get(0).Token == Token.TokenType.TOKEN_Identifier && (Lexemes.get(1).Token == Token.TokenType.TOKEN_ASSIGNMENT)) {
            if (MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null) {
                String ID = Next.Value;
                VariableReference VariableReference = new VariableReference(ID);
                if (MatchAndRemove(Token.TokenType.TOKEN_ASSIGNMENT) != null) {
                    node Expression = FinalExpression();
                    if (Expression == null) {
                        throw new RuntimeException("No Expression");
                    }
                    if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                        return (new AssignmentNode(VariableReference, Expression));
                    }
                }
            }
            return null;

        }

        return null;
    }

    public functionCallNode FunctionCall() {
        ArrayList Parameters = new ArrayList<>();
        boolean isVar = false;
        String ID;
        if (Lexemes.get(0).Token == Token.TokenType.TOKEN_Identifier && (Lexemes.get(1).Token == Token.TokenType.TOKEN_ASSIGNMENT))
            return null;
        if (MatchAndRemove(Token.TokenType.TOKEN_Identifier) != null) {
            ID = Next.Value;
            if (MatchAndRemove(Token.TokenType.TOKEN_VAR) != null) {
                isVar = true;
            }
            node Exp = FinalExpression();
            if (Exp == null) {
                if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null)
                    return new functionCallNode(ID, Parameters);
            }
            Parameters.add(new functionCallParameterNode(Exp, isVar));
            while (MatchAndRemove(Token.TokenType.TOKEN_COMMA) != null) {
                isVar = false;
                if (MatchAndRemove(Token.TokenType.TOKEN_VAR) != null) {
                    isVar = true;
                    node Expression = FinalExpression();
                    if (Expression == null || Expression instanceof FloatNode || Expression instanceof IntegerNode || Expression instanceof StringNode) {
                        throw new RuntimeException("No Parameter");
                    }
                    Parameters.add(new functionCallParameterNode(Expression, isVar));

                } else {
                    node Expression = FinalExpression();
                    if (Expression == null) {
                        throw new RuntimeException("No Parameter");
                    }
                    Parameters.add(new functionCallParameterNode(Expression, isVar));

                }
            }
            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                return new functionCallNode(ID, Parameters);
            }
            return null;
        }
        return null;
    }

    /**
     * The Statement Method to grab Assignments and create a Statement Node
     *
     * @return A statement Node Based on the Assignments
     */
    public node Statement() {
        node Statement = null;
        node Assign;
        node While;
        node ForLoop;
        node RepeatLoop;
        node If;
        functionCallNode functionCall = FunctionCall();
        if (functionCall != null) {
            Statement = functionCall;
            return Statement;
        }
        Assign = Assignment();
        if (Assign != null) {
            Statement = Assign;
            return Statement;
        }
        While = whileExpression();
        if (While != null) {
            Statement = While;
            return Statement;
        }
        ForLoop = ForExpression();
        if (ForLoop != null) {
            Statement = ForLoop;
            return Statement;
        }
        RepeatLoop = repeatExpression();
        if (RepeatLoop != null) {
            Statement = RepeatLoop;
            return Statement;
        }
        If = IfExpression();
        if (If != null) {
            Statement = If;
            return Statement;
        } else return null;
    }

    /**
     * The method used to Create a List of Statement Nodes by calling the Statement method until it fails
     *
     * @return a list of Statement Nodes
     */
    public ArrayList Statements() {
        ArrayList Statements = new ArrayList<>();

        if (MatchAndRemove(Token.TokenType.TOKEN_BEGIN) != null) {
            if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {
                node Statement = Statement();
                while (Statement != null) {
                    Statements.add(Statement);
                    Statement = Statement();
                }
                if (MatchAndRemove(Token.TokenType.TOKEN_END) != null) {
                    if (MatchAndRemove(Token.TokenType.TOKEN_EOL) != null) {

                        return Statements;
                    }
                } else throw new RuntimeException("No End");
            }
        } else
            throw new RuntimeException("No Begin");
        return Statements;
    }

    @Override
    public String toString() {
        return "Parser{" +
                "Root=" + Root +
                '}';
    }
}