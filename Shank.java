import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Shank {
    /** The Main Method
     *
     * @param args Used to Input the Filename
     */
    public static void main(String [] args) {
        // Try-Catch to see if the Command Line argument violates having no argument
        try {
            if (args.length > 1) {
                System.out.println("ERROR: More than one Argument");
                System.exit(1);
            }
            try {
                Path path = Paths.get(args [0]);
                List<String> Input = Files.readAllLines(path);
                Lexer Main = new Lexer();
                ArrayList Tokens = new ArrayList<>();
                //Loop Used to create a single line Token Array combining all the lines in the file
                for (String line : Input) {

                    Tokens = (Main.Lex(line));
                }
                // Print Lexer Tokens
                System.out.println(Tokens);
                // Parse the Lexer's Tokens List
                Parser Parser = new Parser(Tokens);
                ArrayList<FunctionNode> ListOfFunctions = Parser.parse();
                // Print AST
               String AST= ListOfFunctions.toString();
                SemanticAnaylsis S = new SemanticAnaylsis();
                S.CheckAssignments(ListOfFunctions);
               System.out.println(AST + "\n\n============OUTPUT===============\n\n");

               // Assignment 7 call to Main
               // Interpreter constructor creates the HashMap of Functions, Adds Builtin and User defined Functions
                // The Interpreter Constructor finds the Start Method, If it can not find it then throw an error
                Interpreter MainI = new Interpreter(ListOfFunctions);


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    catch (ArrayIndexOutOfBoundsException j)
    {
        System.out.println("NO ARGUMENTS");
    }
}}