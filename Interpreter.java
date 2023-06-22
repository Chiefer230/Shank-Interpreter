import java.util.ArrayList;
import java.util.HashMap;

public class Interpreter {
    public static float Value;     // Final Float Result
    public static HashMap<String, CallableNode> FunctionMap = new HashMap(); // Holds the Functions from the Program

    /**
     * Creates the Function Hashmap with custom Params list based on the expected Parameters
     * Calls Start Function
     *
     * @param Functions List of the Function Nodes from the Parser
     */
    public Interpreter(ArrayList<FunctionNode> Functions) {
        Functions.remove(null);
        // Preset Arraylist talored for each Builtin Function
        ArrayList BuiltinParam = new ArrayList<>();
        ArrayList Random = new ArrayList<>();
        Random.add(new VariableNode(" ", VariableNode.DataType.integer, true, false));
        ArrayList IntegertoReal = new ArrayList<>();
        IntegertoReal.add(new VariableNode(" ", VariableNode.DataType.integer, false, true));
        IntegertoReal.add(new VariableNode(" ", VariableNode.DataType.real, true, false));
        ArrayList SquareRoot = new ArrayList<>();
        SquareRoot.add(new VariableNode(" ", VariableNode.DataType.real, false, true));
        SquareRoot.add(new VariableNode(" ", VariableNode.DataType.real, true, false));
        ArrayList RealtoInterger = new ArrayList<>();
        RealtoInterger.add(new VariableNode("", VariableNode.DataType.real, false, true));
        RealtoInterger.add(new VariableNode("", VariableNode.DataType.integer, true, false));
        ArrayList LeftString = new ArrayList<>();
        LeftString.add(new VariableNode("", VariableNode.DataType.string,false,false));
        LeftString.add(new VariableNode("", VariableNode.DataType.integer,false,false));
        LeftString.add(new VariableNode("", VariableNode.DataType.string,true,false));
        ArrayList SubString = new ArrayList();
        SubString.add(new VariableNode("", VariableNode.DataType.string,false,false));
        SubString.add(new VariableNode("", VariableNode.DataType.integer,false,false));
        SubString.add(new VariableNode("", VariableNode.DataType.integer,false,false));
        SubString.add(new VariableNode("", VariableNode.DataType.string,true,false));
        // Initilaizes the Function Hashmap with Built ins and User Defined
        FunctionMap.put("read", new read("read", BuiltinParam, true));
        FunctionMap.put("write", new write("write", BuiltinParam, true));
        FunctionMap.put("GetRandom", new random("GetRandom", Random, false));
        FunctionMap.put("SquareRoot", new SquareRoot("SquareRoot", SquareRoot, false));
        FunctionMap.put("IntegerToReal", new IntegertoReal("IntegerToReal", IntegertoReal, false));
        FunctionMap.put("RealToInteger", new RealToInt("RealToInteger", RealtoInterger, false));
        FunctionMap.put("Left", new LeftString("Left",LeftString,false));
        FunctionMap.put("Right", new RightString("Right",LeftString,false));
        FunctionMap.put("SubString", new SubString("SubString",SubString,false));

        for (int i = 0; i < Functions.size(); i++) {

            FunctionMap.put(Functions.get(i).Name, new FunctionNode(Functions.get(i).Name, Functions.get(i).Parameters, Functions.get(i).isVar, Functions.get(i).Constants, Functions.get(i).Variables, Functions.get(i).Statements));
        }
        // Requires a Start/Main Method inorder to begin process
        if (!FunctionMap.containsKey("start")) {
            throw new RuntimeException("No Start/Main Method");
        }
        interpretFunction(FunctionMap.get("start"), BuiltinParam);
    }

    /**
     * This Function takes a Callable Function and IDTs to interpret the Function in order to create a Data Structure
     * TO hold our Variables
     *
     * @param Function Function from the Function Hashmap
     * @param IDT      IDTs created from the interpret Block Function
     */
    public static void interpretFunction(CallableNode Function, ArrayList<InterpreterDataType> IDT) {
        HashMap<String, InterpreterDataType> FunctionVariables = new HashMap<>(); // Holds the variables
        // Combines all function variables,Constants, and parameters inorder to create a variable Hashmap
        ArrayList<VariableNode> VariableList = new ArrayList<>();
        VariableList.addAll(Function.Parameters);
        VariableList.addAll(((FunctionNode) Function).Constants);
        VariableList.addAll(((FunctionNode) Function).Variables);
        for (int i = 0; i < VariableList.size(); i++) {
            VariableNode Variable = VariableList.get(i);
            if (Variable.getDataType() == VariableNode.DataType.integer && Variable.isConstant == false) {
                FunctionVariables.put(Variable.Name, new IntDataType());
            } else if (Variable.getDataType() == VariableNode.DataType.real && Variable.isConstant == false) {

                FunctionVariables.put(Variable.Name, new floatDataType());

            } else if (Variable.getDataType() == VariableNode.DataType.real && Variable.isConstant == true && Variable.isVar == false) {

                FunctionVariables.put(Variable.Name, new floatDataType(((FloatNode) VariableList.get(i).Number).Value));

            } else if (Variable.getDataType() == VariableNode.DataType.integer && Variable.isConstant == true && Variable.isVar == false) {

                FunctionVariables.put(Variable.Name, new IntDataType(((IntegerNode) VariableList.get(i).Number).Value));

            }else if (Variable.getDataType() == VariableNode.DataType.string && Variable.isConstant == true) {

                FunctionVariables.put(Variable.Name, new StringDataType(((StringNode) VariableList.get(i).Number).Value));

            }else if (Variable.getDataType() == VariableNode.DataType.Char && Variable.isConstant == true) {

                FunctionVariables.put(Variable.Name, new CharacterDataType(((CharNode) VariableList.get(i).Number).Value));

            }else if (Variable.getDataType() == VariableNode.DataType.Boolean && Variable.isConstant == true) {

                FunctionVariables.put(Variable.Name, new BooleanDataType(((BoolNode) VariableList.get(i).Number).Value));

            }
            else if (Variable.getDataType() == VariableNode.DataType.string && Variable.isConstant == false) {

                FunctionVariables.put(Variable.Name, new StringDataType());

            }else if (Variable.getDataType() == VariableNode.DataType.Char && Variable.isConstant == false) {

                FunctionVariables.put(Variable.Name, new CharacterDataType());

            }else if (Variable.getDataType() == VariableNode.DataType.Boolean && Variable.isConstant == false) {

                FunctionVariables.put(Variable.Name, new BooleanDataType());

            }


        }
        for (int j = 0; j < IDT.size(); j++) {
            if (!IDT.isEmpty()) {
                FunctionVariables.replace(Function.Parameters.get(j).Name, IDT.get(j));
            }
        }
        // Interpret the Function Block using the Variables Hashmap and the Function's Statements
        interpretBlock(((FunctionNode) Function).Statements, FunctionVariables);
    }

    /**
     * The Interpret Block Function where the statements are processed
     *
     * @param Statements        Function Statements
     * @param FunctionVariables The Hashmap for the Variables
     */
    public static void interpretBlock(ArrayList Statements, HashMap<String, InterpreterDataType> FunctionVariables) {

        for (int i = 0; i < Statements.size(); i++) {
            ArrayList<InterpreterDataType> IDT = new ArrayList<>();
            boolean BooleanResult = false;
            if (Statements.get(i) instanceof functionCallNode) {
                functionCallNode FunctionCall = (functionCallNode) Statements.get(i);
                if (FunctionMap.containsKey(FunctionCall.FunctionName)) {
                    CallableNode FunctionDefinition = FunctionMap.get(((functionCallNode) Statements.get(i)).FunctionName);
                    int FunctionParametersSize = FunctionDefinition.Parameters.size();
                    int FunctionCallParametersSize = FunctionCall.Parameters.size();
                    if (((FunctionParametersSize == FunctionCallParametersSize || ((FunctionDefinition).isVar) && (FunctionDefinition instanceof BuiltinFunctionNode)))) {
                        for (int j = 0; j < FunctionCall.Parameters.size(); j++) {
                            node FunctionCallParameter = FunctionCall.Parameters.get(j).parameter;
                            if (FunctionCallParameter instanceof FloatNode) {
                                IDT.add(new floatDataType(((FloatNode) FunctionCallParameter).getFValue()));
                            } else if (FunctionCallParameter instanceof IntegerNode) {
                                IDT.add(new IntDataType((FunctionCallParameter.getValue())));
                            } else if (FunctionCallParameter instanceof VariableReference) {
                                InterpreterDataType Name = FunctionVariables.get((((VariableReference) FunctionCallParameter).Reference));
                                IDT.add(Name);
                            }else if (FunctionCallParameter instanceof StringNode) {
                                IDT.add(new StringDataType((((StringNode) FunctionCallParameter).getStringValue())));
                            }else if (FunctionCallParameter instanceof CharNode) {
                                IDT.add(new CharacterDataType((((CharNode) FunctionCallParameter).getCharValue())));
                            }else if (FunctionCallParameter instanceof BoolNode) {
                                IDT.add(new BooleanDataType((((BoolNode) FunctionCallParameter).isValue())));
                            }
                        }
                        if (FunctionDefinition instanceof BuiltinFunctionNode) {
                            ((BuiltinFunctionNode) FunctionDefinition).Execute(IDT);

                        } else if (FunctionDefinition instanceof FunctionNode) {
                            interpretFunction(FunctionMap.get(FunctionDefinition.Name), IDT);

                        }
                        for (int k = 0; k < IDT.size(); k++) {
                                if ((FunctionDefinition.isVar || ((FunctionDefinition.Parameters.get(k).isVar) && FunctionCall.Parameters.get(k).isVar))) {
                                    if (FunctionDefinition.isVar) {
                                        try {
                                            String Name = ((VariableReference) FunctionCall.Parameters.get(k).parameter).Reference;
                                            FunctionVariables.replace(Name, IDT.get(k));
                                        }
                                        catch (Exception e)
                                        {
                                            System.out.println("");
                                        }
                                    }
                                }
                                // Throws an exception if the vars do not match
                                else if (((!FunctionDefinition.Parameters.get(k).isVar) && FunctionCall.Parameters.get(k).isVar) || ((FunctionDefinition.Parameters.get(k).isVar) && !FunctionCall.Parameters.get(k).isVar))
                                    throw new RuntimeException("No Matching Var");
                        }
                    } else throw new RuntimeException("Error");
                }
                // Interprets Assignments by using The Name of the target and setting it to the value  of the expression
            } else if (Statements.get(i) instanceof AssignmentNode) {
                AssignmentNode Assignment = (AssignmentNode) Statements.get(i);
                String Name = Assignment.VariableReferenceNode.Reference;

                InterpreterDataType UpdateVariable = FunctionVariables.get(Name);
                if (UpdateVariable instanceof floatDataType) {
                    float Value = Resolve(Assignment.Expression, FunctionVariables);
                    ((floatDataType) UpdateVariable).setValue(Value);
                } else if (UpdateVariable instanceof IntDataType) {
                    int IntValue = (int) Resolve(Assignment.Expression, FunctionVariables);
                    ((IntDataType) UpdateVariable).setValue(IntValue);
                }else if (UpdateVariable instanceof StringDataType) {
                    String StringValue =  ResolveString(Assignment.Expression, FunctionVariables);
                    ((StringDataType) UpdateVariable).setValue(StringValue);
                }else if (UpdateVariable instanceof CharacterDataType) {
                    String CharValue =  ResolveString(Assignment.Expression, FunctionVariables);
                    ((CharacterDataType) UpdateVariable).setValue(CharValue);
                }else if (UpdateVariable instanceof BooleanDataType) {
                    boolean BooleanValue;
                    if(Assignment.Expression instanceof BoolNode)
                    {
                         BooleanValue = ResolveBoolean(Assignment.Expression,FunctionVariables);
                    }
                    else {
                         BooleanValue = EvaluateBooleanExpression((BooleanExpressionNode) Assignment.Expression, FunctionVariables);
                    }
                    ((BooleanDataType) UpdateVariable).setValue(BooleanValue);
                }




                // Puts the resulting IDTs into the Variable Hashmap to update their values to change the parameter values
            } else if (Statements.get(i) instanceof whileNode) {
                whileNode While = (whileNode) Statements.get(i);
                if(While.BooleanExpression.Left == null || While.BooleanExpression.Right == null)
                {
                    BooleanResult = ResolveBoolean(While.BooleanExpression.boolNode,FunctionVariables);
                }
                else {
                    BooleanResult = EvaluateBooleanExpression(While.BooleanExpression, FunctionVariables);
                }
                while (BooleanResult) {
                    interpretBlock(While.Statements, FunctionVariables);
                    if(While.BooleanExpression.Left == null || While.BooleanExpression.Right == null)
                    {
                        BooleanResult = ResolveBoolean(While.BooleanExpression.boolNode,FunctionVariables);
                    }
                    else {
                        BooleanResult = EvaluateBooleanExpression(While.BooleanExpression, FunctionVariables);
                    }
                }
                // For Loop is variable is binded to the index, It loops and updates the values as it interprets
                // Can be looped decrementing and incrementing
            } else if (Statements.get(i) instanceof ForNode) {
                ForNode For = (ForNode) Statements.get(i);
                String Name = ((VariableReference) For.VariableReference).Reference;
                InterpreterDataType S = FunctionVariables.get(Name);
                if (For.Start instanceof FloatNode || For.End instanceof FloatNode) {
                    throw new RuntimeException("Only Integer Values are allowed");
                }

                int Start = For.Start.getValue();
                S.setValue(Start);
                int end = For.End.getValue();
                if (Start < end) {
                    while (Start != end) {
                        interpretBlock(For.Statements, FunctionVariables);
                        Start++;
                        S.setValue(Start);
                    }
                } else if (Start > end) {
                    while (Start != end) {
                        interpretBlock(For.Statements, FunctionVariables);
                        Start--;
                        S.setValue(Start);
                    }
                }
                // Repeat Node Interpret where it loops until the condition is true
            } else if (Statements.get(i) instanceof RepeatNode) {
                RepeatNode Repeat = (RepeatNode) Statements.get(i);
                if(Repeat.BooleanExpression.Left == null || Repeat.BooleanExpression.Right == null)
                {
                    BooleanResult = ResolveBoolean(Repeat.BooleanExpression.boolNode,FunctionVariables);
                }
                else {
                    BooleanResult = EvaluateBooleanExpression(Repeat.BooleanExpression, FunctionVariables);
                }
                while (!BooleanResult) {
                    interpretBlock(Repeat.Statements, FunctionVariables);
                    if(Repeat.BooleanExpression.Left == null || Repeat.BooleanExpression.Right == null)
                    {
                        BooleanResult = ResolveBoolean(Repeat.BooleanExpression.boolNode,FunctionVariables);
                    }
                    else {
                        BooleanResult = EvaluateBooleanExpression(Repeat.BooleanExpression, FunctionVariables);
                    }
                }
                // If Interpret where it traverses the Chain until the first condition is true  or all are false to inact the else
            } else if (Statements.get(i) instanceof IfNode) {
                IfNode If = (IfNode) Statements.get(i);
                for (int k = 0; k < If.Chain.size(); k++) {
                    if(!(If.Chain.get(k) instanceof elseNode))
                    if(If.Chain.get(k).BooleanExpression.Left == null && If.Chain.get(k).BooleanExpression.Right == null)
                    {
                        BooleanResult = ResolveBoolean(If.Chain.get(k).BooleanExpression.boolNode,FunctionVariables);
                    }
                    else {
                    BooleanResult = EvaluateBooleanExpression(If.Chain.get(k).BooleanExpression, FunctionVariables);}
                    if (BooleanResult) {

                        interpretBlock(If.Chain.get(k).Statements, FunctionVariables);
                        if(If.Chain.get(k).BooleanExpression.Left == null || If.Chain.get(k).BooleanExpression.Right == null)
                        {
                            BooleanResult = ResolveBoolean(If.Chain.get(k).BooleanExpression.boolNode,FunctionVariables);
                        }
                        else {

                            BooleanResult = EvaluateBooleanExpression(If.Chain.get(k).BooleanExpression, FunctionVariables);}
                        break;
                    }
                    if (!BooleanResult) {
                        if (If.Chain.get(k) instanceof elseNode &&  If.Chain.get(k).BooleanExpression == null) {
                            elseNode Else = (elseNode) If.Chain.get(k);
                            interpretBlock(Else.Statements, FunctionVariables);

                        }
                        else continue;
                    }
                }

            }
        }

    }
    public static boolean ResolveBoolean(node Input, HashMap Variables)
    {
        boolean Result = false;
        if(Input instanceof BoolNode) {
            Result = ((BoolNode) Input).isValue();
        }
        else if (Input instanceof VariableReference) {
            InterpreterDataType IDT = (InterpreterDataType) Variables.get(((VariableReference) Input).Reference);
            if (IDT instanceof BooleanDataType) {
                Result = ((BooleanDataType) IDT).Value;
            }
        }
        return Result;
    }
    /**
     * This is the Boolean Expression Where the conditionals re determined if they are true or they are false
     *
     * @param Expression The expression being tested
     * @param Variables  the variables within the block
     * @return a boolean of the expression
     */
    public static boolean EvaluateBooleanExpression(BooleanExpressionNode Expression, HashMap Variables) {
        boolean Result = false;
        if (Expression == null) {
            return Result;
        }
        if(Expression.Right == null && Expression.Left == null )
        {
            Result = ResolveBoolean(Expression.boolNode,Variables);
        }
        else {
            if(Expression.Right instanceof StringNode || Expression.Left instanceof StringNode)
            {
                String left = ResolveString(Expression.Left,Variables);
                String right = ResolveString(Expression.Right,Variables);
                if (Expression.Condition.equals(Token.TokenType.TOKEN_EQUAL)) {
                    if (left.equals(right)) {
                        Result = true;
                    } else Result = false;
                }
                else if (Expression.Condition.equals(Token.TokenType.TOKEN_NOTEQUAL)) {
                    if (!left.equals(right)) {
                        Result = true;
                    } else Result = false;
                }
                else
                    throw new RuntimeException("Can only do = and <> for Boolean String Operations");

            }
            else if (Expression.Right instanceof CharNode && Expression.Left instanceof CharNode)
            {
                String left = ResolveString(Expression.Left,Variables);
                String right = ResolveString(Expression.Right,Variables);
                if (Expression.Condition.equals(Token.TokenType.TOKEN_EQUAL)) {
                    if (left.equals(right)) {
                        Result = true;
                    } else Result = false;
                }
                else if (Expression.Condition.equals(Token.TokenType.TOKEN_NOTEQUAL)) {
                    if (!left.equals(right)) {
                        Result = true;
                    } else Result = false;
                }
                else
                    throw new RuntimeException("Can Only do = and <> for Boolean Char Operations");
            }
            else if ((Expression.Left instanceof BoolNode && Expression.Right instanceof VariableReference) || Expression.Right instanceof BoolNode && Expression.Left instanceof VariableReference)
            {
                boolean left = ResolveBoolean(Expression.Left,Variables);
                boolean right = ResolveBoolean(Expression.Right,Variables);
                if (Expression.Condition.equals(Token.TokenType.TOKEN_EQUAL)) {
                    if (left ==(right)) {
                        Result = true;
                    } else Result = false;
                }
                else if (Expression.Condition.equals(Token.TokenType.TOKEN_NOTEQUAL)) {
                    if (left !=(right)) {
                        Result = true;
                    } else Result = false;
                }
            }
            else {
                float left = Resolve(Expression.Left, Variables);
                float right = Resolve(Expression.Right, Variables);
                if (Expression.Condition.equals(Token.TokenType.TOKEN_EQUAL)) {
                    if (left == right) {
                        Result = true;
                    } else Result = false;
                } else if (Expression.Condition.equals(Token.TokenType.TOKEN_GREATERTHAN)) {
                    if (left > right) {
                        Result = true;
                    } else Result = false;
                } else if (Expression.Condition.equals(Token.TokenType.TOKEN_GREATEREQUAL)) {
                    if (left >= right) {
                        Result = true;
                    } else Result = false;
                } else if (Expression.Condition.equals(Token.TokenType.TOKEN_LESSEQUAL)) {
                    if (left <= right) {
                        Result = true;
                    } else Result = false;
                } else if (Expression.Condition.equals(Token.TokenType.TOKEN_LESSTHAN)) {
                    if (left < right) {
                        Result = true;
                    } else Result = false;
                } else if (Expression.Condition.equals(Token.TokenType.TOKEN_NOTEQUAL)) {
                    if (left != right) {
                        Result = true;
                    } else Result = false;
                }
            }
        }
        return Result;
    }

public static String ResolveString(node Input, HashMap FunctionVariables)
{
    String StringValue = "";
    if(Input instanceof StringNode)
    {
        return ((StringNode) Input).getStringValue();
    }
    if(Input instanceof CharNode)
    {
        return ((CharNode) Input).getCharValue();
    }
    else if(Input instanceof MathOpNode)
    {
        String Left = ResolveString(((MathOpNode) Input).getLeftNode(), FunctionVariables);
        String Right = ResolveString(((MathOpNode) Input).getRightNode(), FunctionVariables);
        if (((MathOpNode) Input).Type == MathOpNode.Operations.Add) {
            StringValue += Left + Right;
        }
        else
            throw new RuntimeException("Can't do other operations on String DataTypes");

    }
    else if (Input instanceof VariableReference) {
        InterpreterDataType IDT = (InterpreterDataType) FunctionVariables.get(((VariableReference) Input).Reference);
        if (IDT instanceof StringDataType) {
            StringValue = ((StringDataType) IDT).Value;
        } else if (IDT instanceof CharacterDataType) {
            StringValue = ((CharacterDataType) IDT).Value;
        }
        else
            throw new RuntimeException("Wrong Input");

    }return StringValue;}
    /**
     * Resolve Method to do the Math
     * Tests to see if the Node is a float or integer, then perform the math using those nodes
     *
     * @param Input The Node to test
     * @return The Value of the Node
     */
    public static float Resolve(node Input, HashMap FunctionVariables) {
        // Switch Statements to perform operations with the left and Right of the AST
        if (Input instanceof FloatNode) {
            Value = ((FloatNode) Input).getFValue();
        } else if (Input instanceof IntegerNode) {
            Value = (float) ((IntegerNode) Input).getValue();
        } else if (Input instanceof VariableReference) {
            InterpreterDataType IDT = (InterpreterDataType) FunctionVariables.get(((VariableReference) Input).Reference);
            if (IDT instanceof IntDataType) {
                Value = ((IntDataType) IDT).Value;
            } else if (IDT instanceof floatDataType) {
                Value = ((floatDataType) IDT).Value;
            }
        } else if (Input instanceof MathOpNode) {
            // Recursively call the left and right Children of the Math Nodes to traverse the Math Tree
            // Based on the Order of Operations
            float Left = Resolve(((MathOpNode) Input).getLeftNode(), FunctionVariables);
            float Right = Resolve(((MathOpNode) Input).getRightNode(), FunctionVariables);
            if (((MathOpNode) Input).Type == MathOpNode.Operations.Add) {
                Value = Left + Right;
            } else if (((MathOpNode) Input).Type == MathOpNode.Operations.Subtract) {
                Value = Left - Right;
            } else if (((MathOpNode) Input).Type == MathOpNode.Operations.Multiply) {
                Value = Left * Right;
            } else if (((MathOpNode) Input).Type == MathOpNode.Operations.Divide) {
                Value = Left / Right;
            } else if (((MathOpNode) Input).Type == MathOpNode.Operations.Modulo) {
                Value = Left % Right;
            }
        }
        return Value;
    }
}