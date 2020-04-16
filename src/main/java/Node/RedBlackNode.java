package Node;


public class RedBlackNode<T> extends BinaryNode<T> {
    boolean isBlack;
    boolean isDoubleBlack;

    public RedBlackNode() { super(); }

    public RedBlackNode(int key, T value, boolean black) {
        super(key, value);
        setColour(black);
        setDoubleBlack(false);
    }

    public void setDoubleBlack(boolean doubleBlack) { isDoubleBlack = doubleBlack; }

    public void setColour(boolean isBlack) {
        this.isBlack = isBlack;
    }

    public boolean isBlack() { return isBlack; }

    public boolean isDoubleBlack() { return isDoubleBlack; }

    @Override
    public void print() { System.out.print("(Key: " + key + ", value: " + value + ", Black: " + isBlack + ") "); }
}
