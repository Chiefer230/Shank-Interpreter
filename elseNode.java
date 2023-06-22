import java.util.ArrayList;

public class elseNode extends IfNode{
    protected ArrayList Statements;
    public elseNode(ArrayList Statements)
    {
        this.Statements = Statements;
    }

    public elseNode() {
    }

    @Override
    public String toString() {
        return "\nelseNode{" +
                 this.BooleanExpression +
                 this.Statements +
                "" +
                this.ifNode +
                '}';
    }
}