import java.util.ArrayList;

public class RightString extends BuiltinFunctionNode{
    public RightString(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters, isVariadic);
    }

    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        if(InterpreterDataTypes.size() == 3)
        {
            String V =((StringDataType)InterpreterDataTypes.get(0)).Value;
            int EndOfString = V.length();
            int Length = ((IntDataType)InterpreterDataTypes.get(1)).getValue();
            String FinalString=V.substring(Length-1,EndOfString);
            ((StringDataType)InterpreterDataTypes.get(2)).setValue(FinalString);
        }
    }
}