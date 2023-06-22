import java.util.ArrayList;

public abstract class CallableNode extends node {
    protected String Name;
    protected ArrayList<VariableNode> Parameters;
    protected boolean isVar;
    public CallableNode (String Name, ArrayList<VariableNode> Parameters, boolean isVar)
    {
        this.Name = Name;
        this.Parameters = Parameters;
        this.isVar = isVar;
    }
    @Override
    public String toString() {
        return "functionCallNode{" +
                "FunctionName='" + Name + '\'' +
                ", Parameters=" + Parameters +
                '}';
    }
}