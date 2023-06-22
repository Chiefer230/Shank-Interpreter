import java.util.ArrayList;

public abstract class BuiltinFunctionNode extends CallableNode{
    protected boolean isVariadic;


    public BuiltinFunctionNode(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name,Parameters,isVariadic);

    }

    public abstract void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes);

}
