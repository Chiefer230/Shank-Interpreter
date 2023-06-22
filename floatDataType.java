public class floatDataType extends InterpreterDataType{
    protected float Value;
    public floatDataType(String Name,float Value)
    {
        this.Value = Value;
    }
    public floatDataType(float Value)
    {
        this.Value = Value;
    }
    public floatDataType()
    {

    }

    public void setValue(float value) {
        Value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(Value);
    }

    public float getValue()
    {
        return Value;
    }

    @Override
    public void FromString(String Input) {
        Value = Float.parseFloat(Input);
    }
}