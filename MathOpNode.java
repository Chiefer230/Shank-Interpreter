public class MathOpNode extends node {
    private node Left;
    private node Right;
    public Operations Type;
    /** The MathNode Produces Children based on the Operation being done
     *
     *
     * @param Left  Left Child
     * @param Right Right Child
     */
    public MathOpNode(MathOpNode.Operations Type, node Left, node Right) {
        this.Left = Left;
        this.Right = Right;
        this.Type = Type;



    }

    /** The Enum to create an Operation Type for the MathNode
     *
     */
    public enum Operations {
        Divide("/"),
        Multiply("*"),
        Add("+"),
        Subtract("-"),
        Modulo("mod");

        private final String Value;
        Operations(String Value)
        {
            this.Value = Value;
        }
    }
    public Operations getOperations()
    {
        return Type;
    }
    public node getRightNode()
    {
        return Right;
    }
    public node getLeftNode()
    {
        return Left;
    }
    public float getLeft() {
        return Left.getValue();
    }
    public float getRight() {
        return Right.getValue();
    }

    @Override
    public String toString() {
        return "MathOpNode{" + "\nValue=" + getOperations() +
                "\nLeft=" + this.Left +
                ", Right=" + this.Right +
                "\n";
    }
}