package BinaryTree;

import Base.Node;

public class BinaryNode<T> extends Node {
    private T key;
    private BinaryNode<T> left, right;

    BinaryNode(T item)
    {
        key = item;
        left = right = null;
    }

    BinaryNode()
    {
        key = null;
        left = right = null;
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

    BinaryNode<T> getLeftNode() {
        return left;
    }

    BinaryNode<T> getRightNode() {
        return right;
    }

    @Override
    public void print() {
        System.out.print(key);
    }

}
