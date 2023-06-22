import java.util.ArrayList;

public class RealToInt extends BuiltinFunctionNode{


    public RealToInt(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters,false);
    }

    /** This method converts A real Number to an Integer, checking to see if the parameter is a float before converting
     *
     * @param InterpreterDataTypes Arguements within Builtin function
     */
    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        if(InterpreterDataTypes.get(0) instanceof floatDataType){
            float Convert = (((floatDataType) InterpreterDataTypes.get(0)).getValue());
            int FinalConversion = (int) Convert;
            InterpreterDataTypes.set(1,new IntDataType(FinalConversion));
        }
        else
            throw new RuntimeException("Not a Real Number");
    }
}
