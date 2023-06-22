public class StringNode extends node {
    protected String Value;
    public StringNode(String Value)
    {
        this.Value = Value;
    }


    public String getStringValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "StringNode{" +
                "Value='" + Value + '\'' +
                '}';
    }
}