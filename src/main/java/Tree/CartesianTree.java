package Tree;

import Node.BinaryNode;


public class CartesianTree<T> extends BinaryTree<T> {
    int[] sequence;
    int length;

    public CartesianTree() { current = null; }

    public CartesianTree(int[] sequence, int length) {
        current = null;
        this.sequence = sequence;
        this.length = length;
    }

    public void heapTree(Boolean maxHeap) { current = buildHeapTree(this.sequence, 0, length-1, maxHeap); }

    protected BinaryNode<T> buildHeapTree(int[] seq, int begin, int end, Boolean isMaxHeap) {
        if (begin > end) return null;
        BinaryNode<T> node = null;
        int index = 0;
        Boolean isMax = false;

        if (isMaxHeap) {
            index = maxOrMin(seq, begin, end, true);
            node = new BinaryNode<T>(seq[index], null);
            isMax = true;
        } else {
            index = maxOrMin(seq, begin, end, false);
            node = new BinaryNode<T>(seq[index], null);
        }

        if (begin == end) return node;

        linkNodes(node, buildHeapTree(seq, begin, index-1, isMax), 'L');
        if (node.getLeftNode() != null) { node.getLeftNode().setParentNode(node); }

        linkNodes(node, buildHeapTree(seq, index+1, end, isMax), 'R');
        if (node.getRightNode() != null) { node.getRightNode().setParentNode(node); }

        return node;
    }

    protected int maxOrMin(int[] seq, int begin, int end, boolean max) {
        int peekPoint = seq[begin];
        int index = begin;
        for (int i = begin; i <= end; i++) {
            if (max) {
                if (peekPoint < seq[i]) {
                    peekPoint = seq[i];
                    index = i;
                }
            } else {
                if (peekPoint > seq[i]) {
                    peekPoint = seq[i];
                    index = i;
                }
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