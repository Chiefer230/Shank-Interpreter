import java.util.ArrayList;

public class write extends BuiltinFunctionNode{

    public write(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters,true);
    }

    /** This Method outputs the parameter using an Abstract toString Method
     *
     * @param InterpreterDataTypes The amount of arguements within the Method
     */
    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        for (int i = 0; i < InterpreterDataTypes.size(); i++) {
            System.out.print(InterpreterDataTypes.get(i).toString() +" ");
        }
        System.out.println("");
    }
}