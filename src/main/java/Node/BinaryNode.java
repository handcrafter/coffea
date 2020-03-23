package Node;

import Base.Node;


public class BinaryNode<T> extends Node {
    protected T value;
    protected int key;
    private BinaryNode<T> parent, left, right;

    public BinaryNode(int key, T value)
    {
        this.key = key;
        this.value = value;
        parent = left = right = null;
    }

    public BinaryNode()
    {
        value = null;
        parent = left = right = null;
    }

    public void setKey(int key) { this.key = key; }

    public void setValue(T value) { this.value = value; }

    public void setLeftNode(BinaryNode<T> node) {
        left = node;
    }

    public void setRightNode(BinaryNode<T> node) {
        right = node;
    }

    public void setParentNode(BinaryNode<T> node) { parent = node; };

    public int getKey() { return key; }

    public T getValue() { return value; }

    public BinaryNode<T> getLeftNode() { return left; }

    public BinaryNode<T> getRightNode() { return right; }

    public BinaryNode<T> getParentNode() { return parent; }

    @Override
    public void print() {
        System.out.print("key: " + key + ", value: " + value);
    }

}
