package BinaryTree;

import Base.Node;


public class BinaryNode<T> extends Node {
    private T key;
    private BinaryNode<T> parent, left, right;

    BinaryNode(T item)
    {
        key = item;
        parent = left = right = null;
    }

    BinaryNode()
    {
        key = null;
        parent = left = right = null;
    }

    void setKey(T item) {
        key = item;
    }

    void setLeftNode(BinaryNode<T> node) {
        left = node;
    }

    void setRightNode(BinaryNode<T> node) {
        right = node;
    }

    void setParentNode(BinaryNode<T> node) { parent = node; };

    T getKey() { return key; }

    BinaryNode<T> getLeftNode() { return left; }

    BinaryNode<T> getRightNode() { return right; }

    BinaryNode<T> getParentNode() { return parent; }

    @Override
    public void print() {
        System.out.print(key);
    }

}
