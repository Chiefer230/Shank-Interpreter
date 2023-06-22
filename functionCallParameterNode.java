public class functionCallParameterNode extends node {
    protected node parameter;
    protected boolean isVar;
    public functionCallParameterNode(node parameter, boolean isVar)
    {
        this.parameter = parameter;
        this.isVar = isVar;
    }

    @Override
    public String toString() {
        return "functionCallParameterNode{" +
                "parameter=" + parameter +
                ", isVar=" + isVar +
                '}';
    }
}