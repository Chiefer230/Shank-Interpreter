import java.util.ArrayList;
class SquareRoot extends BuiltinFunctionNode{

    public SquareRoot(String Name,ArrayList Parameters, boolean isVariadic) {
        super(Name,Parameters, isVariadic);
    }

    /** This Method gets the square root by first checking if the type of DataType it is before proceeding
     * Returns the result in the same DataType initially used
     * If its a String dataType, throw exception
     * @param InterpreterDataTypes The Arguements
     */
    @Override
    public void Execute(ArrayList InterpreterDataTypes) {
        if(InterpreterDataTypes.size() == 2){
        if(InterpreterDataTypes.get(0) instanceof floatDataType)
        {
            InterpreterDataTypes.set(1,new floatDataType((float) Math.sqrt(((floatDataType) InterpreterDataTypes.get(0)).getValue())));
        }
        else if(InterpreterDataTypes.get(0) instanceof IntDataType)
        {
            InterpreterDataTypes.set(1,new IntDataType((int) Math.sqrt(((IntDataType) InterpreterDataTypes.get(0)).getValue())));
        }
        else
            throw new RuntimeException("Wrong Parameter Data Type");
    }
        else
            throw new RuntimeException("Incorrect Amount of Arguments");
        }
}