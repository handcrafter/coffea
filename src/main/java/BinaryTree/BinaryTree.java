package BinaryTree;

import Base.Tree;

public class BinaryTree<T> extends Tree {

    BinaryNode<T> root;
    BinaryNode<T> current;

    BinaryTree() {
        root = null;
        current = null;
    }


    // Constructors
    BinaryTree(T key) {
        root = new BinaryNode<T>(key);
        current = root;
    }

    public void setLeftChild(T key) {
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        root.setLeftNode(node);
    }

    public void setRightChild(T key) {
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        root.setRightNode(node);
    }

    public void moveToLeftNode() {
        current = current.getLeftNode();
    }

    public void moveToRightNode() {
        current = current.getRightNode();
    }


    @Override
    public void printDFS() {
        // TODO
    }

    @Override
    public void printBFS() {
        // TODO
    }
}
