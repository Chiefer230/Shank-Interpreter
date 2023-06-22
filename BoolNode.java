public class BoolNode extends node {
    protected boolean Value;
    public BoolNode(boolean Value)
    {
        this.Value =Value;
    }

    public boolean isValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "BoolNode{" +
                "Value=" + Value +
                '}';
    }
}