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
        current = current.getRightNode();
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

        while (true) {
            if (current.getKey() == key) {
                result = current;
                break;
            } else if (current.getKey() < key) {
                if (current.getRightNode() != null) {
                    moveToRightNode();
                } else {
                    break;
                }
            } else if (current.getKey() > key) {
                if (current.getLeftNode() != null) {
                    moveToLeftNode();
                } else {
                    break;
                }
            }
        }

        current = originalPosition;
        return result;
    }

    public boolean contains(int key) {
        TernaryNode<T> node = getNode(key);
        return node != null;
    }

    public void insert(int insertKey, T value) {
        if (current == null) {
            TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
            current = node;
        }
        TernaryNode<T> originalPosition = current;

        while (true) {
            TernaryNode<T> left = current.getLeftNode();
            TernaryNode<T> right = current.getRightNode();
            TernaryNode<T> middle = current.getMiddleNode();

            if (left == null) {
                TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
                linkNodes(current, node, 'L');
                break;
            } else if (middle == null) {
                TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
                linkNodes(current, node, 'M');
                break;
            } else if (right == null) {
                TernaryNode<T> node = new TernaryNode<T>(insertKey, value);
                linkNodes(current, node, 'R');
                break;
            } else if (left != null) {
                current = current.getLeftNode();
            } else if (middle != null) {
                current = current.getMiddleNode();
            } else if (right != null) {
                current = current.getRightNode();
            }
        }
        current = originalPosition;
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
