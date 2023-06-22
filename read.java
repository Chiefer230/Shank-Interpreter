import java.util.ArrayList;
import java.util.Scanner;

public class read extends BuiltinFunctionNode {

    public read(String Name, ArrayList Parameters, boolean isVariadic) {
        super(Name, Parameters,true);
    }

    /**This Method uses Java's Scanner to take in the User Input
     *
     * @param InterpreterDataTypes arguments
     */
    @Override
    public void Execute(ArrayList<InterpreterDataType> InterpreterDataTypes) {
        Scanner Input = new Scanner(System.in);
        for (int i = 0; i < InterpreterDataTypes.size();i++)
        {
            InterpreterDataType IDT =InterpreterDataTypes.get(i);
                if( IDT instanceof IntDataType)
                {
                    IDT.FromString(Input.next());
                }
                else if (IDT instanceof floatDataType)
                {
                    IDT.FromString(Input.next());
                }
                else if (IDT instanceof StringDataType)
                {
                    IDT.FromString(Input.nextLine());
                } else if (IDT instanceof CharacterDataType)
                {
                    IDT.FromString(Input.next());
                } else if (IDT instanceof BooleanDataType)
                {
                    IDT.FromString(Input.next());
                }

        }

    }
}