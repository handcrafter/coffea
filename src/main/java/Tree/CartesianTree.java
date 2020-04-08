package Tree;

import Node.BinaryNode;


public class CartesianTree<T> extends BinaryTree<T> {
    int[] sequence;
    boolean maxHeap;

    public CartesianTree() { current = null; }

    public CartesianTree(int[] sequence, boolean maxHeap) {
        this.sequence = sequence;
        this.maxHeap = maxHeap;
        current = constructTree(0, sequence.length-1);
    }

    public BinaryNode<T> constructTree(int begin, int end) {
        if (begin > end) return null;
        BinaryNode<T> node = null;
        int index = 0;

        if (maxHeap) {
            index = maxIndex(begin, end);
            node = new BinaryNode<T>(sequence[index], null);
        } else {
            index = minIndex(begin, end);
            node = new BinaryNode<T>(sequence[index], null);
        }

        if (begin == end) return node;

        linkNodes(node, constructTree(begin, index-1), 'L');

        linkNodes(node, constructTree(index+1, end), 'R');

        return node;
    }

    private int maxIndex(int begin, int end) {
        int maxPoint = sequence[begin];
        int index = begin;
        for (int i = begin; i <= end; i++) {
            if (maxPoint < sequence[i]) {
                maxPoint = sequence[i];
                index = i;
            }
        }
        return index;
    }

    private int minIndex(int begin, int end) {
        int minPoint = sequence[begin];
        int index = begin;
        for (int i = begin; i <= end; i++) {
            if (minPoint > sequence[i]) {
                minPoint = sequence[i];
                index = i;
            }
        }
        return index;
    }

    public void printInOrder() {
        if (current == null) return;
        if (current.getLeftNode() != null) {
            moveToLeftNode();
            printInOrder();
            moveToParentNode();
        }
        current.print();
        if (current.getRightNode() != null) {
            moveToRightNode();
            printInOrder();
            moveToParentNode();
        }
    }
}