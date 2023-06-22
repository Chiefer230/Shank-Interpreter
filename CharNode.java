public class CharNode extends node {
    protected String Value;
    public CharNode(String Value)
    {
        this.Value = Value;
    }


    public String getCharValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "CharNode{" +
                "Value='" + Value + '\'' +
                '}';
    }
}