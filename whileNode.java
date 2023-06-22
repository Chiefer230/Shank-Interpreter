import java.util.ArrayList;

public class whileNode extends node {
    protected BooleanExpressionNode BooleanExpression;
    protected ArrayList<node> Statements;
    public whileNode(BooleanExpressionNode BooleanExpression,ArrayList<node> Statements)
    {
        this.BooleanExpression =BooleanExpression;
        this.Statements = Statements;
    }

    @Override
    public String toString() {
        return "\nwhileNode{" +
                BooleanExpression +
                Statements +
                '}';
    }
}