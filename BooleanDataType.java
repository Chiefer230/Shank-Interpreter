public class BooleanDataType extends InterpreterDataType{
    protected boolean Value;
    public BooleanDataType(boolean Value)
    {
        this.Value = Value;
    }

    public BooleanDataType() {

    }

    public void setValue(boolean value) {
        Value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(Value);
    }

    @Override
    public void FromString(String Input) {
        Value = Boolean.parseBoolean(Input);
    }
}