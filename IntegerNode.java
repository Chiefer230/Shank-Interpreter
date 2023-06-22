public class IntegerNode extends node {
    protected int Value;
    /** Integer Node doesn't produce Children and Only Stores the Integer value
     *
     */
    public IntegerNode(int Value) {
       this.Value = Value;
    }

    /** Read Only Accessor
     *
     * @return Value
     */
    public int getValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "IntegerNode{" +
                "value=" + this.Value +
                '}';
    }
}