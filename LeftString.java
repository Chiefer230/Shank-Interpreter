import java.util.ArrayList;

public class LeftString extends BuiltinFunctionNode{
    public LeftString(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters, isVariadic);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        if(InterpreterDataTypes.size() == 3)
        {
             String V =((StringDataType)InterpreterDataTypes.get(0)).Value;
             int Length = ((IntDataType)InterpreterDataTypes.get(1)).getValue();
             String FinalString=V.substring(0,Length);
            ((StringDataType)InterpreterDataTypes.get(2)).setValue(FinalString);
        }
    }
}