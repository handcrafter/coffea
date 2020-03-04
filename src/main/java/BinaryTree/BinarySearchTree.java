package BinaryTree;

import Base.Tree;
import java.util.LinkedList;
import java.util.Queue;


public class BinarySearchTree<T> extends Tree {
    BinaryNode<T> current;

    // Constructors
    BinarySearchTree() { current = null; }
    BinarySearchTree(int key, T value) { current = new BinaryNode<T>(key, value); }

    // Traversing methods
    public void moveToLeftNode() { current = current.getLeftNode(); }

    public void moveToRightNode() { current = current.getRightNode(); }

    public void moveToParentNode() { current = current.getParentNode(); }


    // Setters
    public void setLeftChild(int key) {
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        current.setLeftNode(node);
    }

    public void setRightChild(int key) {
        BinaryNode<T> node = current.getRightNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        current.setRightNode(node);
    }

    // Contains
    public boolean contains(int key) {
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
    public void insert(int insert_key, T value) {
        BinaryNode<T> original_position = current;
        BinaryNode<T> node = new BinaryNode<T>(insert_key, value);

        if (current == null) { current = node; }

        if (current.getKey() < insert_key) {
            if (current.getRightNode() == null) {
                setRightChild(node.getKey());
            } else {
                moveToRightNode();
                insert(insert_key, value);
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                setLeftChild(node.getKey());
            } else {
                moveToLeftNode();
                insert(insert_key, value);
            }
        }
        current = original_position;
    }

    // Deletion
    public void delete(int delete_key) {
        BinaryNode<T> original_position = current;


        if (delete_key == 0) return;

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
                if (current.getParentNode().getKey() < current.getRightNode().getKey()) {
                    current.getParentNode().setRightNode(current.getRightNode());
                    current.getRightNode().setParentNode(current.getParentNode());
                } else {
                    current.getParentNode().setLeftNode(current.getRightNode());
                    current.getRightNode().setParentNode(current.getParentNode());
                }

            } else if (current.getLeftNode() != null && current.getRightNode() == null) {
                if (current.getParentNode().getKey() < current.getRightNode().getKey()) {
                    current.getParentNode().setRightNode(current.getLeftNode());
                    current.getLeftNode().setParentNode(current.getParentNode());
                } else {
                    current.getParentNode().setLeftNode(current.getLeftNode());
                    current.getLeftNode().setParentNode(current.getParentNode());
                }
            }
            // Deleting a node with two children
            else {
                BinaryNode<T> successor = find_successor();
                delete(successor.getKey());
                current.setKey(successor.getKey());
            }
        }
        current = original_position;
    }

    // Get min node in subtree
    public BinaryNode<T> find_successor() {
        BinaryNode<T> original_position = current;
        moveToRightNode();
        while (current.getLeftNode() != null) {
            moveToLeftNode();
        }
        BinaryNode<T> successor = current;
        current = original_position;
        return successor;
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
