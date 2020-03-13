package BinaryTree;

import Base.Tree;
import java.util.LinkedList;
import java.util.Queue;


public class CartesianTree<T> extends Tree {
    BinaryNode<T> current;
    int[] sequence;
    int length;

    CartesianTree() { current = null; }

    CartesianTree(int[] sequence, int length) {
        current = null;
        this.sequence = sequence;
        this.length = length;
    }

    public void moveToLeftNode() { current = current.getLeftNode(); }

    public void moveToRightNode() { current = current.getRightNode(); }

    public void moveToParentNode() { current = current.getParentNode(); }

    public void MaxHeapTree() { current = buildMaxHeapTree(this.sequence, 0, length-1); }

    public void MinHeapTree() { current = buildMinHeapTree(this.sequence, 0, length-1); }

    public BinaryNode<T> buildMaxHeapTree(int[] seq, int begin, int end) {
        if (begin > end) return null;

        int max = maxIndex(seq, begin, end);

        BinaryNode<T> node = new BinaryNode<T>(seq[max], null);

        if (begin == end) return node;

        node.setLeftNode(buildMaxHeapTree(seq, begin, max-1));
        if (node.getLeftNode() != null) { node.getLeftNode().setParentNode(node); }

        node.setRightNode(buildMaxHeapTree(seq, max+1, end));
        if (node.getRightNode() != null) { node.getRightNode().setParentNode(node); }

        return node;
    }

    public BinaryNode<T> buildMinHeapTree(int[] seq, int begin, int end) {
        if (begin > end) return null;

        int min = minIndex(seq, begin, end);

        BinaryNode<T> node = new BinaryNode<T>(seq[min], null);

        if (begin == end) return node;

        node.setLeftNode(buildMinHeapTree(seq, begin, min-1));
        if (node.getLeftNode() != null) { node.getLeftNode().setParentNode(node); }

        node.setRightNode(buildMinHeapTree(seq, min+1, end));
        if (node.getRightNode() != null) { node.getRightNode().setParentNode(node); }

        return node;
    }

    public int maxIndex(int[] seq, int begin, int end) {
        int max = seq[begin];
        int index = begin;
        for (int i = begin; i <= end; i++) {
            if (max < seq[i]) {
                max = seq[i];
                index = i;
            }
        }
        return index;
    }

    public int minIndex(int[] seq, int begin, int end) {
        int min = seq[begin];
        int index = begin;
        for (int i = begin; i <= end; i++) {
            if (min > seq[i]) {
                min = seq[i];
                index = i;
            }
        }
        return index;
    }

    public void printInorder() {
        if (current == null) return;

        /* first recur on left child */
        if (current.getLeftNode() != null) {
            moveToLeftNode();
            printInorder();
            moveToParentNode();
        }

        /* then print the data of node */
        current.print();

        /* now recur on right child */
        if (current.getRightNode() != null) {
            moveToRightNode();
            printInorder();
            moveToParentNode();
        }
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
