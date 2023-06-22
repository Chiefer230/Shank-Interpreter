import java.util.ArrayList;
import java.util.LinkedList;

public class IfNode extends node {
    protected BooleanExpressionNode BooleanExpression;
    protected ArrayList Statements;
    protected node ifNode;
    protected node IfChain;
    protected LinkedList<IfNode> Chain;

    /** First Constructor used to create An IfNode
     *
     * @param BooleanExpression
     * @param Statements
     */
    public IfNode(BooleanExpressionNode BooleanExpression,ArrayList Statements)
    {
        this.BooleanExpression = BooleanExpression;
        this.Statements = Statements;
        this.ifNode = ifNode;
    }
    public IfNode(LinkedList Chain)
    {
        this.Chain = Chain;
    }

    public IfNode() {
    }

    /**Second Constructor used to chain the if Nodes together
     *
     * @param ifNode
     * @param IfChain
     */
    public IfNode(node ifNode, node IfChain)
    {
        this.IfChain = IfChain;
        this.ifNode = ifNode;
    }

    @Override
    public String toString() {
        return "IfNode{" +
                "BooleanExpression=" + BooleanExpression +
                ", Statements=" + Statements +
                ", IfNode=" + ifNode +
                ", IfChain=" + IfChain +
                ", Chain=" + Chain +
                '}';
    }
}