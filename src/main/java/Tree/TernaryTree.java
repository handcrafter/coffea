package Tree;

import Base.Tree;
import Node.TernaryNode;

import java.util.LinkedList;
import java.util.Queue;


public class TernaryTree<T> extends Tree {
    TernaryNode<T> root;
    TernaryNode<T> current;

    public TernaryTree() { root = current = null; }

    public TernaryTree(int key, T value) {
        current = new TernaryNode<T>(key,value);
        root = current;
    }

    protected void moveToLeftNode() {
        assert current == null : "current must not be null";
        current = current.getLeftNode();
    }

    protected void moveToRightNode() {
        assert current == null : "current must not be null";
        current = current.getRightNode();
    }

    protected void moveToMiddleNode() {
        assert current == null : "current must not be null";
        current = current.getMiddleNode();
    }

    protected void moveToParentNode() {
        assert current == null : "current must not be null";
        current = current.getParentNode();
    }

    protected void linkNodes(TernaryNode<T> parent, TernaryNode<T> child, char lmr) {
        assert parent == null : "Parent node must not be null";
        assert (lmr != 'R' || lmr != 'L' || lmr != 'M') : "Input must be R or L or M";
        if (lmr == 'L') {
            parent.setLeftNode(child);
            if (child != null) child.setParentNode(parent);
        } else if (lmr == 'R') { // lr =='R'
            parent.setRightNod(child);
            if (child != null) child.setParentNode(parent);
        } else {
            parent.setMiddletNode(child);
            if (child != null) child.setParentNode(parent);
        }
    }

    protected TernaryNode<T> getNode(int key) {
        TernaryNode<T> result = null;
        TernaryNode<T> originalPosition = current;
        current = root;

        Queue<TernaryNode<T>> queue = new LinkedList<TernaryNode<T>>() ;
        queue.add(current);
        while (!queue.isEmpty()) {
            TernaryNode<T> node = queue.remove();
            if (node.getKey() == key) {
                result = node;
                break;
            }
            if (node.getLeftNode() != null) queue.add(node.getLeftNode());
            if (node.getMiddleNode() != null) queue.add(node.getMiddleNode());
            if (node.getRightNode() != null) queue.add(node.getRightNode());
        }

        current = originalPosition;
        return result;
    }

    public boolean contains(int key) {
        TernaryNode<T> node = getNode(key);
        return node != null;
    }

    public void insert(int insertKey, T value) {
        if (root == null) {
            TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
            root = current = node;
        }

        TernaryNode<T> originalPosition = current;
        current = root;
        while (true) {
            TernaryNode<T> left = current.getLeftNode();
            TernaryNode<T> right = current.getRightNode();
            TernaryNode<T> middle = current.getMiddleNode();

            if (!current.isFull()) {
                if (left == null) {
                    TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
                    linkNodes(current, node, 'L');
                    updateSize(node);
                    break;
                } else if (middle == null) {
                    TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
                    linkNodes(current, node, 'M');
                    updateSize(node);
                    break;
                } else if (right == null) {
                    TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
                    linkNodes(current, node, 'R');
                    updateSize(node);
                    break;
                }
            } else {
                if (left.getSize() > middle.getSize()) {
                    moveToMiddleNode();
                } else if (middle.getSize() > right.getSize()) {
                    moveToRightNode();
                } else {
                    moveToLeftNode();
                }
            }

        }
        current = originalPosition;
    }

    private void updateSize(TernaryNode<T> node) {

        TernaryNode<T> originalPosition = node;
        while (node != null) {
            TernaryNode<T> left = node.getLeftNode();
            TernaryNode<T> right = node.getRightNode();
            TernaryNode<T> middle = node.getMiddleNode();
            int size = 0;
            if (node.isFull()) {
                size = left.getSize()+middle.getSize()+right.getSize()+1;
            } else {
                if (left == null) {
                    size = 1;
                } else if (middle == null) {
                    size = left.getSize() + 1;
                } else if (right == null) {
                    size = left.getSize() + middle.getSize() + 1;
                }
            }
            node.setSize(size);
            node = node.getParentNode();
        }
        node = originalPosition;
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

        if (current.getMiddleNode() != null) {
            moveToMiddleNode();
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
        Queue<TernaryNode<T>> queue = new LinkedList<TernaryNode<T>>() ;
        queue.add(current);
        while (!queue.isEmpty()) {
            TernaryNode<T> node = queue.remove();
            node.print();
            if (node.getLeftNode() != null) queue.add(node.getLeftNode());
            if (node.getMiddleNode() != null) queue.add(node.getMiddleNode());
            if (node.getRightNode() != null) queue.add(node.getRightNode());
        }
    }
}
