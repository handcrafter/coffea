package BinaryTree;

import Base.Tree;

import java.util.LinkedList;
import java.util.Queue;

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
        BinaryNode<T> node = current.getRightNode();
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
        if (current == null) return;

        current.print();

        BinaryNode hold = current;
        if (current.getLeftNode() != null) {
            moveToLeftNode();
            printDFS();
            current = hold;
        }

        hold = current;
        if (current.getRightNode() != null) {
            moveToRightNode();
            printDFS();
            current = hold;
        }

    }

    @Override
    public void printBFS() {
        Queue<BinaryNode<T>> queue = new LinkedList<BinaryNode<T>>() ;
        if (root == null)
            return;
        queue.clear();
        queue.add(root);
        while(!queue.isEmpty()){
            BinaryNode<T> node = queue.remove();
            node.print();
            if(node.getLeftNode() != null) queue.add(node.getLeftNode());
            if(node.getRightNode() != null) queue.add(node.getRightNode());
        }
    }
}


