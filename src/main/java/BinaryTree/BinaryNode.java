package BinaryTree;

import Base.Node;


public class BinaryNode<T> extends Node {
    private T value;
    private int key;
    private BinaryNode<T> parent, left, right;

    BinaryNode(int item, T value)
    {
        key = item;
        this.value = value;
        parent = left = right = null;
    }

    BinaryNode()
    {
        key = 0;
        value = null;
        parent = left = right = null;
    }

    void setKey(int item) {
        key = item;
    }

    void setValue(T value) { this.value = value; }

    void setLeftNode(BinaryNode<T> node) {
        left = node;
    }

    void setRightNode(BinaryNode<T> node) {
        right = node;
    }

    void setParentNode(BinaryNode<T> node) { parent = node; };

    int getKey() { return key; }

    T getValue() { return value; }

    BinaryNode<T> getLeftNode() { return left; }

    BinaryNode<T> getRightNode() { return right; }

    BinaryNode<T> getParentNode() { return parent; }

    @Override
    public void print() {
        System.out.print(key);
    }

}
