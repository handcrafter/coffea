package BinaryTree;

import Base.Tree;
import java.util.LinkedList;
import java.util.Queue;


public class BinarySearchTree<T> extends Tree {
    BinaryNode<T> current;

    // Constructors
    BinarySearchTree() { current = null; }
    BinarySearchTree(T key) { current = new BinaryNode<T>(key); }

    // Traversing methods
    public void moveToLeftNode() { current = current.getLeftNode(); }

    public void moveToRightNode() { current = current.getRightNode(); }

    public void moveToParentNode() {
        current = current.getParentNode();
    }


    // Setters
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

    // Search
    public boolean searchKey(T key) {
        BinaryNode<T> original_position = current;
        Boolean result = false;

        if (current == null) return false;

        while (current != null) {
            if (current.getKey() == key) {
                result = true;
                break;
            } else if ((Integer)current.getKey() < (Integer)key) {
                moveToRightNode();
            } else if ((Integer)current.getKey() > (Integer)key) {
                moveToLeftNode();
            }
        }

        current = original_position;
        return result;
    }

    // Insertion
    public void insertNode(BinaryNode<T> n) {
        BinaryNode<T> original_position = current;
        if (current == null) { current = n; }

        if ((Integer)current.getKey() < (Integer)n.getKey()) {
            if (current.getRightNode() == null) {
                setRightChild(n.getKey());
            } else {
                moveToRightNode();
                insertNode(n);
            }
        } else if ((Integer)current.getKey() > (Integer)n.getKey()) {
            if (current.getLeftNode() == null) {
                setLeftChild(n.getKey());
            } else {
                moveToLeftNode();
                insertNode(n);
            }
        }
        current = original_position;
    }

    //
    public void deleteNode(BinaryNode<T> dNode) {
        BinaryNode<T> original_position = current;

        while (current.getKey() != dNode.getKey()) {
            if ((Integer)current.getKey() < (Integer)dNode.getKey()) {
                moveToRightNode();
            } else if ((Integer)current.getKey() > (Integer)dNode.getKey()) {
                moveToLeftNode();
            }
        }

        if (current.getKey() == dNode.getKey()) {

            // Deleting a node with no children
            if (current.getLeftNode() == null && current.getRightNode() == null) {
                if ((Integer)current.getParentNode().getKey() < (Integer)current.getKey()) {
                    current.getParentNode().setRightNode(null);
                } else {
                    current.getParentNode().setLeftNode(null);
                }

            }
            // Deleting a node with one child
            else if (current.getLeftNode() == null && current.getRightNode() != null) {
                current.getParentNode().setRightNode(current.getRightNode());
                current.getRightNode().setParentNode(current.getParentNode());

            } else if (current.getLeftNode() != null && current.getRightNode() == null) {
                current.getParentNode().setLeftNode(current.getLeftNode());
                current.getLeftNode().setParentNode(current.getParentNode());

            }
            // Deleting a node with two children
            else if (current.getLeftNode() != null && current.getRightNode() != null) {
                moveToRightNode();
                BinaryNode<T> successor = find_successor();
                deleteNode(successor);
                moveToParentNode();
                successor.setParentNode(current.getParentNode());
                successor.setLeftNode(current.getLeftNode());
                successor.setRightNode(current.getRightNode());
            }
        }
        current = original_position;
    }

    // Get min node in subtree
    public BinaryNode<T> find_successor() {
        BinaryNode<T> original_position = current;

        while (current.getLeftNode() != null) {
            moveToLeftNode();
        }
        BinaryNode<T> min_Node = current;
        current = original_position;
        return min_Node;
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
