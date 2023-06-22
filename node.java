abstract class node {
   protected String Value;
   protected node Left;
   protected node Right;

    /** Abstract Node Constructor
     *
     * @param Value the data of the Node
     */
   public node(String Value)
   {
       this.Value = Value;
       this.Left = null;
       this.Right = null;
   }
   public node()
   {}

    public int getValue() {
        return Integer.parseInt(Value);
    }
    public float getFloatValue()
        {
       return Float.parseFloat(Value);
    }

    @Override
    public String toString() {
        return "Node{" +
                "Value='" + this.Value + '\n' +
                ", Left=" + this.Left +
                ", Right=" + this.Right +
                '}';
    }
}