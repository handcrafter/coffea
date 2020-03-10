package BinaryTree;

import Base.Tree;
import java.util.LinkedList;
import java.util.Queue;


public class AVLtree<T> extends Tree {
    BinaryNode<T> current;

    AVLtree() { current = null; }

    AVLtree(int key, T value) { current = new BinaryNode<T>(key, value); }

    public void moveToLeftNode() { current = current.getLeftNode(); }

    public void moveToRightNode() { current = current.getRightNode(); }

    public void moveToParentNode() { current = current.getParentNode(); }

    public void setLeftChild(int key, T value) {
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        node.setValue(value);
        node.setHeight(0);
        current.setLeftNode(node);
    }

    public void setRightChild(int key, T value) {
        BinaryNode<T> node = current.getRightNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        node.setValue(value);
        node.setHeight(0);
        current.setRightNode(node);
    }

    public void updateHeight(BinaryNode<T> node) {
        if (node.getLeftNode() == null && node.getRightNode() == null) {
            node.setHeight(0);
        } else if (node.getLeftNode() == null && node.getRightNode() != null) {
            node.setHeight(1 + node.getRightNode().getHeight());
        } else if (node.getLeftNode() != null && node.getRightNode() == null) {
            node.setHeight(1 + current.getLeftNode().getHeight());
        } else {
            node.setHeight(1 + Math.max(node.getLeftNode().getHeight(), node.getRightNode().getHeight()));
        }
    }

    public int getDepthDifference(BinaryNode<T> target) {
        if (target.getLeftNode() == null && target.getRightNode() != null) {
            return -target.getRightNode().getHeight() - 1;
        } else if (target.getLeftNode() != null && target.getRightNode() == null) {
            return target.getLeftNode().getHeight() + 1;
        } else {
            return target.getLeftNode().getHeight() - target.getRightNode().getHeight();
        }
    }

    public void bst_insert(int insert_key, T value) {
        BinaryNode<T> node = new BinaryNode<T>(insert_key, value);

        if (current == null) { current = node; }

        if (current.getKey() < insert_key) {
            if (current.getRightNode() == null) {
                setRightChild(node.getKey(), value);
            } else {
                moveToRightNode();
                bst_insert(insert_key, value);
                moveToParentNode();
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                setLeftChild(node.getKey(), value);
            } else {
                moveToLeftNode();
                bst_insert(insert_key, value);
                moveToParentNode();
            }
        }
        updateHeight(current);
    }

    public void insert(int key, T value) {
        // Perform standard bst insertion
        bst_insert(key, value);

        // balance the tree
        int depth_difference = getDepthDifference(current);
        if (depth_difference > 1 && key < current.getLeftNode().getKey()) { // Left Left case
            rightRotation();
        } else if (depth_difference < -1 && key > current.getRightNode().getKey()) { // Right Right case
            leftRotation();
        } else if (depth_difference > 1 && key > current.getLeftNode().getKey()) { //Left Right case
            moveToLeftNode();
            leftRotation();
            moveToParentNode();
            rightRotation();
        } else if (depth_difference < -1 && key < current.getRightNode().getKey()) { // Right Left case
            moveToRightNode();
            rightRotation();
            moveToParentNode();
            leftRotation();
        }
    }

    public void bst_delete(int delete_key) {
        if (current == null) return;

        if (current.getKey() < delete_key) {
            if (current.getRightNode() != null) {
                moveToRightNode();
                bst_delete(delete_key);
                moveToParentNode();
            } else {
                return;
            }
        } else if (current.getKey() > delete_key) {
            moveToLeftNode();
            bst_delete(delete_key);
            moveToParentNode();
        } else if (current.getKey() == delete_key) {
            // Deleting a node with no children
            if (current.getLeftNode() == null && current.getRightNode() == null) {
                if (current.getParentNode().getKey() < current.getKey()) {
                    current.getParentNode().setRightNode(null);
                } else {
                    current.getParentNode().setLeftNode(null);
                }
            }
            // Deleting a node with one child
            else if (current.getLeftNode() == null && current.getRightNode() != null) {
                if (current.getParentNode() == null) { // Removing a root node
                    current.getRightNode().setParentNode(null);
                    current = current.getRightNode();
                } else {
                    if (current.getParentNode().getKey() < current.getRightNode().getKey()) {
                        current.getParentNode().setRightNode(current.getRightNode());
                        current.getRightNode().setParentNode(current.getParentNode());
                    } else {
                        current.getParentNode().setLeftNode(current.getRightNode());
                        current.getRightNode().setParentNode(current.getParentNode());
                    }
                }
            } else if (current.getLeftNode() != null && current.getRightNode() == null) {
                if (current.getParentNode() == null) { // removing a root node
                    current.getLeftNode().setParentNode(null);
                    current = current.getLeftNode();
                } else {
                    if (current.getParentNode().getKey() < current.getRightNode().getKey()) {
                        current.getParentNode().setRightNode(current.getLeftNode());
                        current.getLeftNode().setParentNode(current.getParentNode());
                    } else {
                        current.getParentNode().setLeftNode(current.getLeftNode());
                        current.getLeftNode().setParentNode(current.getParentNode());
                    }
                }
            }
            // Deleting a node with two children
            else {
                BinaryNode<T> successor = find_successor();
                bst_delete(successor.getKey());
                current.setKey(successor.getKey());
            }
        }
        updateHeight(current);
    }

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

    public void delete(int delete_key) {
        // perform standard bst deletion
        bst_delete(delete_key);

        // balance the tree
        int depth_difference = getDepthDifference(current);
        if (depth_difference > 1 && getDepthDifference(current.getLeftNode()) >= 0) { // Left Left case
            rightRotation();
        } else if (depth_difference < -1 && getDepthDifference(current.getRightNode()) <= 0) { // Right Right case
            leftRotation();
        } else if (depth_difference > 1 && getDepthDifference(current.getLeftNode()) < 0) { //Left Right case
            moveToLeftNode();
            leftRotation();
            moveToParentNode();
            rightRotation();
        } else if (depth_difference < -1 && getDepthDifference(current.getRightNode()) > 0) { // Right Left case
            moveToRightNode();
            rightRotation();
            moveToParentNode();
            leftRotation();
        }
    }

    public void rightRotation() {
        BinaryNode<T> newRoot = current.getLeftNode();

        if (newRoot.getRightNode() != null) {
            current.setLeftNode(newRoot.getRightNode());
        } else {
            current.setLeftNode(null);
        }

        newRoot.setRightNode(current);
        newRoot.setParentNode(current.getParentNode());
        current.setParentNode(newRoot);

        if (current.getLeftNode() != null) {
            current.getLeftNode().setParentNode(current);
        }

        updateHeight(current);
        updateHeight(newRoot);
        current = newRoot;
    }

    public void leftRotation() {
        BinaryNode<T> newRoot = current.getRightNode();

        if (newRoot.getLeftNode() != null) {
            current.setRightNode(newRoot.getLeftNode());
        } else {
            current.setRightNode(null);
        }

        newRoot.setLeftNode(current);
        newRoot.setParentNode(current.getParentNode());
        current.setParentNode(newRoot);

        if (current.getRightNode() != null) {
            current.getRightNode().setParentNode(current);
        }

        updateHeight(current);
        updateHeight(newRoot);
        current = newRoot;
    }

    // Contains
    public boolean contains(int key) {
        Boolean result = false;

        if (current == null) return false;

        if (current.getKey() == key) {
            return true;
        } else if (current.getKey() < key) {
            if (current.getRightNode() == null) {
                return false;
            } else {
                moveToRightNode();
                contains(key);
                moveToParentNode();
            }
        } else if (current.getKey() > key) {
            if (current.getLeftNode() == null) {
                return false;
            } else {
                moveToLeftNode();
                contains(key);
                moveToParentNode();
            }
        }

        return result;
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
