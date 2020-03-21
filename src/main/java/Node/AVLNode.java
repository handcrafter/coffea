package Node;


public class AVLNode<T> extends BinaryNode<T> {
    private int height;

    public AVLNode() {
        super();
    }

    public AVLNode(int key, T value, int height) {
        super(key, value);
        setHeight(height);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public void print() { System.out.print("(Key: " + key + ", value: " + value + ", height: " + height + ") "); }
}
