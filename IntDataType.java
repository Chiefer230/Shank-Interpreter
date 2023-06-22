public class IntDataType extends InterpreterDataType{
    protected int Value;

    public void setValue(int value) {
        Value = value;
    }


    public IntDataType()
    {

    }
    public IntDataType(int Value)
    {
        this.Value = Value;
    }

    /** Assessor for the Value of the Data Type
     *
     * @return
     */
    public int getValue()
    {
        return Value;
    }
    @Override
    public String toString() {
        return String.valueOf(Value);
    }

    @Override
    public void FromString(String Input) {
        Value = Integer.parseInt(Input);
    }
}