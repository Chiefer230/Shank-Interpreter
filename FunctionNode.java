import java.util.ArrayList;

public class FunctionNode extends CallableNode{
    protected ArrayList<VariableNode> Constants;
    protected ArrayList<VariableNode> Variables;
    protected ArrayList Statements;

    /** The Function Node AST
     *
     * @param Name Identifier of the Function
     * @param Parameters List of Variables within the parameters
     * @param Constants list of Constant
     * @param Variables List of Variables
     * @param Statements Node for the Body
     */
    public FunctionNode(String Name, ArrayList<VariableNode> Parameters,boolean isVar, ArrayList<VariableNode> Constants, ArrayList<VariableNode> Variables, ArrayList Statements)
    {
        super(Name,Parameters,isVar);
        this.Constants = Constants;
        this.Variables = Variables;
        this.Statements = Statements;
    }

    @Override
    public String toString() {
        return "\n\nFUNCTION{" +
                "Value='" + Name + '\'' +
                ",\nParameters=" + Parameters +
                ", \n\nConstants=" + Constants +
                ", \n\nVariables=" + Variables +
                ", \n\n" + Statements +
                '}';
    }
}