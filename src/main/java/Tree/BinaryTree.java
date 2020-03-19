package Tree;

import Base.Tree;
import Node.BinaryNode;

import java.util.LinkedList;
import java.util.Queue;


public class BinaryTree<T> extends Tree {

    BinaryNode<T> current;

    public BinaryTree() {
        current = null;
    }

    // Constructors
    public BinaryTree(int key, T value) { current = new BinaryNode<T>(key, value); }

    public void setLeftChild(int key, T value) {
        if (current == null) return;
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setValue(value);
        node.setParentNode(current);
        current.setLeftNode(node);
    }

    public void setRightChild(int key, T value) {
        assert current == null : "current node must not be null";
        BinaryNode<T> node = current.getRightNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setValue(value);
        node.setParentNode(current);
        current.setRightNode(node);
    }

    public void moveToLeftNode() {
        assert current == null : "current must not be null";
        current = current.getLeftNode();
    }

    public void moveToRightNode() {
        assert current == null : "current must not be null";
        current = current.getRightNode();
    }

    public void moveToParentNode() {
        assert current == null : "current must not be null";
        current = current.getParentNode();
    }

    @Override
    public void printDFS() {
        assert current == null : "current must not be null";
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
    }

    @Override
    public void printBFS() {
        assert current == null : "current must not be null";
        Queue<BinaryNode<T>> queue = new LinkedList<BinaryNode<T>>() ;
        queue.add(current);
        while (!queue.isEmpty()) {
            BinaryNode<T> node = queue.remove();
            node.print();
            if (node.getLeftNode() != null) queue.add(node.getLeftNode());
            if (node.getRightNode() != null) queue.add(node.getRightNode());
        }
    }
}


