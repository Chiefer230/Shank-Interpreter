import java.util.ArrayList;

public class SubString extends BuiltinFunctionNode{

    public SubString(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters, isVariadic);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        if(InterpreterDataTypes.size() == 4)
        {
            String V =((StringDataType)InterpreterDataTypes.get(0)).Value;
            int index = ((IntDataType)InterpreterDataTypes.get(1)).getValue();
            int Length = ((IntDataType)InterpreterDataTypes.get(2)).getValue();
            String FinalString=V.substring(index,Length);
            ((StringDataType)InterpreterDataTypes.get(3)).setValue(FinalString);
        }
    }
}