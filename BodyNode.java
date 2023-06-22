import java.util.ArrayList;

public class BodyNode extends node {
    private ArrayList Variables;
    private ArrayList Constants;
    public BodyNode(ArrayList Variables)
    {

        this.Variables = Variables;
    }

    @Override
    public String toString() {
        return "BodyNode{" +
                "Variables=" + Variables.toString();
    }
}