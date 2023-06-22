import java.util.ArrayList;

public class IntegertoReal extends BuiltinFunctionNode{

    public IntegertoReal(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters,false);
    }

    /** This Method converts an Integer to a Real Number by first checking to see if the argument is an integer
     * takes the first argument and sets the result into the second argument
     * @param InterpreterDataTypes arguments used
     */
    @Override
    public void Execute(ArrayList InterpreterDataTypes) {
        if(InterpreterDataTypes.get(0) instanceof IntDataType){
            int Convert = (((IntDataType) InterpreterDataTypes.get(0)).getValue());
            float FinalConversion = (float) Convert;
            InterpreterDataTypes.set(1, new floatDataType(FinalConversion));
        }
        else
            throw new RuntimeException("Not a Integer Number");
    }
}