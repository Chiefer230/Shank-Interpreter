public abstract class InterpreterDataType {
protected String Name;
protected int Value;
public InterpreterDataType(int Value)
{
    this.Value = Value;
    this.Name = Name;
}
public InterpreterDataType()
{

}


    public void setValue(int value) {
        Value = value;
    }

    public abstract String toString();
    public abstract void FromString(String Input);
}