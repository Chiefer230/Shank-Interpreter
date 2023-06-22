public class AssignmentNode extends StatementNode{
    protected VariableReference VariableReferenceNode;
    protected node Expression;

    /** The Assignment Node is used to Assign a variable to a variable
     *
     * @param VariableReferenceNode The Value of the Variable being referenced
     * @param Expression The Expression assigned to the variable
     */
    public AssignmentNode(VariableReference VariableReferenceNode, node Expression)
    {
        this.VariableReferenceNode = VariableReferenceNode;
        this.Expression = Expression;
    }

    public node getExpression() {
        return Expression;
    }


    @Override
    public String toString() {
        return "\nAssignmentNode{" +
                  VariableReferenceNode +
                Expression +
                '}';
    }
}