import java.util.ArrayList;
import java.util.Random;

public class random extends BuiltinFunctionNode{

    public random(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters, isVariadic);
    }

    /** Creates a random Integer Number and returns it to the argument, Checks if the size is 1
     *
     * @param InterpreterDataTypes
     */
    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        if(InterpreterDataTypes.size() == 1) {

            Random rn = new Random();
            int Result = rn.nextInt();
             String Name =InterpreterDataTypes.get(0).Name;
            InterpreterDataTypes.set(0, new IntDataType(Result));
        }
        else throw new RuntimeException("More Than One arguement");
    }
}