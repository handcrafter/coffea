package Tree;

import Node.BinaryNode;


public class BinarySearchTree<T> extends BinaryTree<T> {

    public BinarySearchTree() { current = null; }

    public BinarySearchTree(int key, T value) { current = new BinaryNode<T>(key, value); }

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

    public void insert(int insert_key, T value) {
        BinaryNode<T> original_position = current;
        BinaryNode<T> node = new BinaryNode<T>(insert_key, value);

        if (current == null) { current = node; }

        if (current.getKey() < insert_key) {
            if (current.getRightNode() == null) {
                setRightChild(node.getKey(), value);
            } else {
                moveToRightNode();
                insert(insert_key, value);
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                setLeftChild(node.getKey(), value);
            } else {
                moveToLeftNode();
                insert(insert_key, value);
            }
        }
        current = original_position;
    }

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
                if (current.getParentNode().getKey() < current.getLeftNode().getKey()) {
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
}
