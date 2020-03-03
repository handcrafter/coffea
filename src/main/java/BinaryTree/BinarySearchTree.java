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

    public void moveToParentNode() { current = current.getParentNode(); }


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
    public boolean contains(T key) {
        BinaryNode<T> original_position = current;
        Boolean result = false;

        if (current == null) return false;

        while (current != null) {
            if (current.getKey() == key) {
                result = true;
                break;
            } else if ((Integer) current.getKey() < (Integer) key) {
                moveToRightNode();
            } else if ((Integer) current.getKey() > (Integer) key) {
                moveToLeftNode();
            }
        }

        current = original_position;
        return result;
    }

    // Insertion
    public void insert(T newNode) {
        BinaryNode<T> original_position = current;
        BinaryNode<T> node = new BinaryNode<T>(newNode);

        if (current == null) { current = node; }

        if ((Integer) current.getKey() < (Integer) newNode) {
            if (current.getRightNode() == null) {
                setRightChild(node.getKey());
            } else {
                moveToRightNode();
                insert(newNode);
            }
        } else if ((Integer) current.getKey() > (Integer) newNode) {
            if (current.getLeftNode() == null) {
                setLeftChild(node.getKey());
            } else {
                moveToLeftNode();
                insert(newNode);
            }
        }
        current = original_position;
    }

    //
    public void delete(T delete_key) {
        BinaryNode<T> original_position = current;


        if (delete_key == null) return;

        while (current.getLeftNode() != null || current.getRightNode() != null) {
            if ((Integer) current.getKey() < (Integer) delete_key) {
                moveToRightNode();
            } else if ((Integer) current.getKey() > (Integer) delete_key) {
                moveToLeftNode();
            } else if (current.getKey() == delete_key) {
                break;
            }
        }

        if (current.getKey() == delete_key) {

            // Deleting a node with no children
            if (current.getLeftNode() == null && current.getRightNode() == null) {
                if ((Integer) current.getParentNode().getKey() < (Integer) current.getKey()) {
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
            else {
                BinaryNode<T> successor = successor();
                delete(successor.getKey());
                current.setKey(successor.getKey());
            }
        }
        current = original_position;
    }

    // Get min node in subtree
    public BinaryNode<T> successor() {
        moveToRightNode();
        BinaryNode<T> original_position = current;

        while (current.getLeftNode() != null) {
            moveToLeftNode();
        }
        BinaryNode<T> min_Node = current;
        current = original_position;
        moveToParentNode();
        return min_Node;
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
