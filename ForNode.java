import java.util.ArrayList;

public class ForNode extends node {
    protected node VariableReference;
    protected node Start;
    protected node End;
    protected ArrayList<node> Statements;
    public ForNode(node VariableReference, node Start, node End, ArrayList<node> Statements)
    {
        this.VariableReference = VariableReference;
        this.Start = Start;
        this.End = End;
        this.Statements = Statements;
    }

    @Override
    public String toString() {
        return "ForNode{" +
                "VariableReference=" + VariableReference +
                ", Start=" + Start +
                ", End=" + End +
                ", Statements=" + Statements +
                '}';
    }
}