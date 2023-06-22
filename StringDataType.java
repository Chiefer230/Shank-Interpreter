public class StringDataType extends InterpreterDataType {
    protected String Value;

    public StringDataType(String Value) {
        this.Value = Value;
    }

    public StringDataType() {

    }

    public void setValue(String value) {
        Value = value;
    }

    @Override
    public String toString() {
        return Value;
    }

    @Override
    public void FromString(String Input) {
        Value = Input;
    }
}