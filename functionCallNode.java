import java.util.ArrayList;

public class functionCallNode extends StatementNode{
    protected String FunctionName;
    protected ArrayList<functionCallParameterNode> Parameters;
    public functionCallNode (String FunctionName, ArrayList<functionCallParameterNode> Parameters)
    {
        this.FunctionName = FunctionName;
        this.Parameters = Parameters;
    }

    @Override
    public String toString() {
        return "\nfunctionCallNode{" +
                "FunctionName='" + FunctionName + '\'' +
                ", Parameters=" + Parameters +
                '}';
    }
}
