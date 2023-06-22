
public class VariableReference extends node {
    protected String Reference;
    public VariableReference (String Reference)
    {
    this.Reference = Reference;
    }

    @Override
    public String toString() {
        return "VariableReference{" +
                "Reference='" + Reference + '\'' +
                '}';
    }
}