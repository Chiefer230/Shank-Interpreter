import java.util.ArrayList;
import java.util.HashMap;

public class SemanticAnaylsis {
   public static  ArrayList ExpressionDataTypes = new ArrayList<>(); // List of Current Variable Data Types

    /**
     * Used to Correct Assignments given a Function Node
     * Uses a Variable HashMap constructed from a Function Node to hold all Variables and their Data Types
     * @param Functions
     */
    public void CheckAssignments(ArrayList<FunctionNode> Functions)
    {
        HashMap<String, Enum> VariableCheck = new HashMap();
        Functions.remove(null);
        for(int i = 0; i < Functions.size(); i ++)
        {
            FunctionNode Function = Functions.get(i);
             VariableCheck = PopulateVariableMap(Function);
             ArrayList Statements = Function.Statements;
            CorrectStatements(Statements,VariableCheck);
        }
    }

    /**
     *  Corrects Assignments within the Blocks of If,While,Repeat recursively
     * @param Statements Statements of the Function Node
     * @param VariableCheck VariableMap from the Function Node with Name and DataType
     */
    public void CorrectStatements(ArrayList Statements,HashMap <String,Enum> VariableCheck)
    {
        for (int j = 0; j < Statements.size(); j++)
        {
            if(Statements.get(j) instanceof AssignmentNode)
            {
                AssignmentNode Assignment =(AssignmentNode)Statements.get(j);
                String Name = Assignment.VariableReferenceNode.Reference;
                Enum Target = VariableCheck.get(Name);
                if(Assignment.Expression instanceof BooleanExpressionNode)
                {
                    BoolCheck(BooleanCheck((BooleanExpressionNode) Assignment.Expression,VariableCheck),Target,Name);
                }
                else{
                DataCheck(Traverse(Assignment.Expression,VariableCheck),Target,Name);}
                ExpressionDataTypes.clear();

            }
            else if (Statements.get(j) instanceof functionCallNode)
            {
                continue;
            }
            else if (Statements.get(j) instanceof whileNode)
            {
                whileNode While = (whileNode) Statements.get(j);
                BooleanExpressionCheck(BooleanCheck((BooleanExpressionNode) While.BooleanExpression,VariableCheck));
                CorrectStatements(While.Statements,VariableCheck);
            }
            else if (Statements.get(j) instanceof ForNode)
            {
                ForNode For = (ForNode) Statements.get(j);
                CorrectStatements(For.Statements,VariableCheck);
            }
            else if (Statements.get(j) instanceof RepeatNode)
            {
                RepeatNode Repeat = (RepeatNode) Statements.get(j);
                BooleanExpressionCheck(BooleanCheck((BooleanExpressionNode)Repeat.BooleanExpression,VariableCheck));
                CorrectStatements(Repeat.Statements,VariableCheck);
            }
            else if (Statements.get(j) instanceof IfNode)
            {
                IfNode If = (IfNode) Statements.get(j);
                for(int k = 0; k < If.Chain.size();k++)
                {
                    if(If.Chain.get(k) instanceof IfNode) {
                        if (If.Chain.get(k) instanceof elseNode)
                        {
                            elseNode Else = (elseNode) If.Chain.get(k);
                            CorrectStatements(Else.Statements,VariableCheck);
                        }
                        else {
                            CorrectStatements(If.Chain.get(k).Statements, VariableCheck);
                        }

                    }
                }
            }
        }
    }
    public void BooleanExpressionCheck(ArrayList BooleanTypes)
    {
        for( int i = 0; i < BooleanTypes.size()-1; i++)
        {
            for(int j = i + 1; j < BooleanTypes.size(); j++)
            {
                if(ExpressionDataTypes.get(i) != ExpressionDataTypes.get(j))
                {
                    throw new RuntimeException("\n" + ExpressionDataTypes.get(j) + " Does not match the required Data Type to compare: " + ExpressionDataTypes.get(i));
                }
            }
        }
    }
    /** BoolCheck is used to Check a Boolean Expression Node to see if it is correct.
     * All Nodes should have the same DataType on both sides of the Boolean Expression
     * If both sides consist of the same DataType, then Check to see if Target is a Boolean Type
     * If Target is Not a Boolean Type, throw an execption
     * @param BooleanTypes
     * @param Target
     * @param Name
     */
    public void BoolCheck(ArrayList BooleanTypes,Enum Target,String Name)
    {
        for( int i = 0; i < BooleanTypes.size()-1; i++)
        {
            for(int j = i + 1; j < BooleanTypes.size(); j++)
            {
                if(ExpressionDataTypes.get(i) != ExpressionDataTypes.get(j))
                {
                    throw new RuntimeException("\n" + ExpressionDataTypes.get(j) + " Does not match the required Data Type to compare: " + ExpressionDataTypes.get(i));
                }
            }
        }
        if(Target != VariableNode.DataType.Boolean)
        {
            throw new RuntimeException("\nVariable Name:" + Name + "\n" + Target + " is required to be a Boolean");
        }

    }

    /**
     * Adds all Variable DataTypes from both sides to Error Check
     * @param Node Boolean ExpressionNode if an assignment has a BooleanExpression
     * @param FunctionVariables Variable HashMap with the Name and DataTypes
     * @return ArrayList of the Boolean Expression's DataType
     */
    public ArrayList BooleanCheck(BooleanExpressionNode Node, HashMap<String,Enum> FunctionVariables)
    {
        ExpressionDataTypes.addAll(Traverse(Node.Left,FunctionVariables));
        ExpressionDataTypes.addAll(Traverse(Node.Right,FunctionVariables));
        return ExpressionDataTypes;
    }

    /**
     *  DataCheck Takes the RightHand side of the Assignment DataTypes and checks to see if it matches the Target DataType
     *  If one out of the DataTypes do not match to the Target Type, then throw an exception
     * @param ExpressionDataTypes Lists of DataTypes from the Assignment's righthand Side
     * @param Target Target's DataType
     * @param Name Target's Name
     * @return Boolean value if they all Match
     */
    public boolean DataCheck(ArrayList ExpressionDataTypes,Enum Target,String Name)
    {
        boolean IdenticalDataTypes = false;
        for(int i = 0; i < ExpressionDataTypes.size(); i++)
        {
            if(Target == ExpressionDataTypes.get(i))
            {
                IdenticalDataTypes = true;
            }
            else if (Target == VariableNode.DataType.string)
            {
                if(ExpressionDataTypes.get(i) == VariableNode.DataType.string || ExpressionDataTypes.get(i) == VariableNode.DataType.Char)
                {
                    IdenticalDataTypes = true;
                }
                else
                {
                throw new RuntimeException("\nTarget Name: " + Name + "\nTarget DataType: " + Target + "\nWrong Match at Value position " + i + "\n"+ExpressionDataTypes.get(i) + " : does not match the Target Data Type: " + Target);
                }

            }
            else
                throw new RuntimeException("\nTarget Name: " + Name + "\nTarget DataType: " + Target + "\nWrong Match at Value position " + i + "\n"+ExpressionDataTypes.get(i) + " : does not match the Target Data Type: "  + Target);
        }
        return IdenticalDataTypes;
    }

    /**
     *  Traverses the Assignment Expression to add each DataType to a list to compare
     * @param Input Node from expression
     * @param FunctionVariables Variable HashMap with Name and DataType of the Target
     * @return DataType ArrayList for the RightHandSide of the Assignment
     */
    public ArrayList Traverse(node Input,HashMap<String,Enum> FunctionVariables)
    {
        if (Input instanceof FloatNode) {

            ExpressionDataTypes.add((VariableNode.DataType.real));
        } else if (Input instanceof BoolNode) {
            ExpressionDataTypes.add((VariableNode.DataType.Boolean));

        } else if (Input instanceof StringNode) {
            ExpressionDataTypes.add((VariableNode.DataType.string));

        }else if (Input instanceof CharNode) {
            ExpressionDataTypes.add((VariableNode.DataType.Char));

        }else if (Input instanceof IntegerNode) {
            ExpressionDataTypes.add((VariableNode.DataType.integer));

        }
        else if (Input instanceof VariableReference) {
           Enum J= FunctionVariables.get(((VariableReference) Input).Reference);
            ExpressionDataTypes.add(J);
        } else if (Input instanceof MathOpNode) {
            // Recursively call the left and right Children of the Math Nodes to traverse the Math Tree
            // Based on the Order of Operations
            Traverse(((MathOpNode) Input).getLeftNode(),FunctionVariables);
            Traverse(((MathOpNode) Input).getRightNode(),FunctionVariables);
        }

        return ExpressionDataTypes;
    }

    /**
     * Populates Hashmap with all variables,constants, and Parameters\
     * Takes the Name and DataType
     * @param Function Function Node
     * @return HashMap of Name,DataType of Function Variables
     */
    public HashMap<String, Enum> PopulateVariableMap(FunctionNode Function)
    {
        HashMap<String, Enum> Variables = new HashMap<>();
        ArrayList<VariableNode> Temp = new ArrayList<>();
        Temp.addAll(Function.Parameters);
        Temp.addAll(Function.Variables);
        Temp.addAll(Function.Constants);
        for( int i = 0; i < Temp.size(); i ++)
        {
            Variables.put(Temp.get(i).Name,Temp.get(i).getDataType());

        }
        return Variables;
    }

}