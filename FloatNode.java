public class FloatNode extends node {
    protected float Value;

    /** The Float Node doesn't produce Children and only stores a float value
     *
     * @param Value
     */
    public FloatNode(float Value) {
        this.Value = Value;
    }

    /** Read only Accessor
     *
     * @return Float Value
     */
    public float getFValue() {
        return Value;
    }

    @Override
    public String toString() {
        return "FloatNode{" +
                "value=" + this.Value +
                '}';
    }
}