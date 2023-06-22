public class VariableNode extends node {
    protected String Name;
    protected node Number;
    private Enum DataType;
    protected boolean isConstant;
    protected boolean isVar;
    public VariableNode(String Name,DataType Type, boolean isVar,boolean isConstant)
    {
        this.Name = Name;
        this.DataType = Type;
        this.isVar = isVar;
    }

    public String getName() {
        return Name;
    }

    public node getNumber()
    {
        return Number;
    }
    @Override
    /** ToString method for the Variables,
     * If the variable is a constant, then print with the Number Node
     * If it is not a Constant, then print without the Number Node
     */
    public String toString() {
        if(Number == null)
        {
            return "\nVariableNode{" +
                    "Name='" + Name + '\'' +
                    ", DataType=" + DataType +
                    ", isConstant='" + isConstant + '\'' +
                    ", isVar='" + isVar + '\'' +
                    '}';
        }
        return "\nVariableNode{" +
                "Name='" + Name + '\'' +
                ", Number=" + Number +
                ", DataType=" + DataType +
                ", isConstant='" + isConstant + '\'' +
                '}';
    }

    /** The Variable Node AST
     *
     * @param Name Identifier
     * @param Number Integer or Float Node
     * @param isConstant
     * @param Type Data Type enum
     */
    public VariableNode(String Name, node Number, boolean isConstant, DataType Type)
    {
        this.Name = Name;
        this.Number = Number;
        this.isConstant = isConstant;
        this.DataType = Type;
    }
    public VariableNode(DataType Type)
    {
        this.DataType = Type;
    }

    public Enum getDataType() {
        return DataType;
    }

    /** Used to specifiy the Data type for a Constant
     *
     */
    public enum DataType
    {
        integer,
        string,
        Char,
        Boolean,
        real
    }
}