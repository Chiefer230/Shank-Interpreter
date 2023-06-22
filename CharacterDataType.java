public class CharacterDataType extends InterpreterDataType{
    protected String Value;
    public CharacterDataType(String Value)
    {
        this.Value = Value;
    }

    public CharacterDataType() {

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