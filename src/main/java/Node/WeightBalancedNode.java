package Node;


public class WeightBalancedNode<T> extends BinaryNode<T> {
    private int weight;

    public WeightBalancedNode() { super(); }

    public WeightBalancedNode(int key, T value, int weight) {
        super(key, value);
        setWeight(weight);
    }

    public void setWeight(int height) { this.weight = height; }

    public int getWeight() { return weight; }

    @Override
    public void print() { System.out.print("(Key: " + key + ", value: " + value + ", weight: " + weight + ") "); }
}
