package BinaryTree;

import Base.Tree;
import java.util.LinkedList;
import java.util.Queue;


public class BinaryTree<T> extends Tree {

    BinaryNode<T> current;

    BinaryTree() {
        current = null;
    }

    // Constructors
    BinaryTree(T key) {
        current = new BinaryNode<T>(key);
    }

    public void setLeftChild(T key) {
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        current.setLeftNode(node);
    }

    public void setRightChild(T key) {
        BinaryNode<T> node = current.getRightNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        current.setRightNode(node);
    }

    public void moveToLeftNode() {
        current = current.getLeftNode();
    }

    public void moveToRightNode() {
        current = current.getRightNode();
    }

    public void moveToParentNode() {
        current = current.getParentNode();
    }

    @Override
    public void printDFS() {
        BinaryNode<T> original_position = current;
        if (current == null) return;

        current.print();

        if (current.getLeftNode() != null) {
            moveToLeftNode();
            printDFS();
            moveToParentNode();
        }

        if (current.getRightNode() != null) {
            moveToRightNode();
            printDFS();
            moveToParentNode();
        }

        current = original_position;
    }

    @Override
    public void printBFS() {
        Queue<BinaryNode<T>> queue = new LinkedList<BinaryNode<T>>() ;
        if (current == null) return;

        queue.add(current);
        while (!queue.isEmpty()) {
            BinaryNode<T> node = queue.remove();
            node.print();
            if (node.getLeftNode() != null) queue.add(node.getLeftNode());
            if (node.getRightNode() != null) queue.add(node.getRightNode());
        }
    }
}


