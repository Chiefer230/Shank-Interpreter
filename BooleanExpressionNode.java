public class BooleanExpressionNode extends node {
    protected node Left;
    protected node Right;
    protected Token.TokenType Condition;
    protected node boolNode;
    public BooleanExpressionNode(node Left, node Right, Token.TokenType Condition)
    {
        this.Left = Left;
        this.Right = Right;
        this.Condition =Condition;
    }
    public BooleanExpressionNode (node boolNode)
    {
        this.boolNode = boolNode;
    }



    @Override
    public String toString() {
        if(Left == null && Right == null)
        {
             return "BooleanExpressionNode{" +
                "BoolNode=" + boolNode +
                '}';
        }
        return "\nBooleanExpressionNode{" +
                "LeftExpression=" + Left +
                ", RightExpression=" + Right +
                ", Condition=" + Condition +
                '}';
    }
}