package Node;

import Base.Node;


public class TernaryNode<T> extends Node {
    protected T value;
    protected int key;
    protected int size;

    private TernaryNode<T> parent, left, middle, right;

    public TernaryNode(int key, T value) {
        this.key = key;
        this.value = value;
        parent = left = right = middle = null;
        size = 1;
    }

    public TernaryNode() {
        value = null;
        parent = left = right = middle = null;
    }

    public void setKey(int key) { this.key = key; }

    public void setValue(T value) { this.value = value; }

    public void setSize(int size) { this.size = size; }

    public void setLeftNode(TernaryNode<T> node) {
        left = node;
    }

    public void setRightNod(TernaryNode<T> node) {
        right = node;
    }

    public void setMiddletNode(TernaryNode<T> node) {
        middle = node;
    }

    public void setParentNode(TernaryNode<T> node) { parent = node; };

    public boolean isFull() { return (left != null && middle != null && right != null); }

    public int getKey() { return key; }

    public T getValue() { return value; }

    public TernaryNode<T> getLeftNode() { return left; }

    public TernaryNode<T> getRightNode() { return right; }

    public TernaryNode<T> getMiddleNode() { return middle; }

    public TernaryNode<T> getParentNode() { return parent; }

    public int getSize() { return size; }

    @Override
    public void print() { System.out.print("(key: " + key + ", value: " + value + ") "); }
}
