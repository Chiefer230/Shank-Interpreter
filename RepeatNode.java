import java.util.ArrayList;

public class RepeatNode extends node {
    protected BooleanExpressionNode BooleanExpression;
    protected ArrayList<node> Statements;
    public RepeatNode(BooleanExpressionNode BooleanExpression,ArrayList<node> Statements)
    {
        this.BooleanExpression = BooleanExpression;
        this.Statements = Statements;
    }

    @Override
    public String toString() {
        return "RepeatNode{" +
                "BooleanExpression=" + BooleanExpression +
                ", Statements=" + Statements +
                '}';
    }
}