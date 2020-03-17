package Node;

/**
 * Created by jaewonlee on 2020-03-16.
 */
public class AVLNode<T> extends BinaryNode {
    private int height;

    public AVLNode() {
        super();
    }

    public AVLNode(int key, T value) {
        super(key, value);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }
}
