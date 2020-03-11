package BinaryTree;


import Base.Tree;
import java.util.LinkedList;
import java.util.Queue;


public class RedBlackTree<T extends String> extends Tree {
    BinaryNode<T> current;

    // Constructors
    RedBlackTree() { current = null;}

    RedBlackTree(int key, T value) { current = new BinaryNode<T>(key, value); }

    // Traversing methods
    public void moveToLeftNode() { current = current.getLeftNode(); }

    public void moveToRightNode() { current = current.getRightNode(); }

    public void moveToParentNode() { current = current.getParentNode(); }

    public BinaryNode<T> getGrandParent(BinaryNode<T> node) {
        if (node == null || node.getParentNode() == null || node.getParentNode().getParentNode() == null) return null;
        return node.getParentNode().getParentNode();
    }

    public BinaryNode<T> getUncle(BinaryNode<T> node) {
        BinaryNode gp = getGrandParent(node);

        if (gp == null) return null;

        if (node.getKey() < gp.getKey()) {
            return gp.getRightNode();
        } else {
            return gp.getLeftNode();
        }
    }

    public BinaryNode<T> getSibling(BinaryNode<T> node) {
        if (node == null || node.getParentNode() == null) return null;

        if (node == node.getParentNode().getLeftNode()) {
            return node.getParentNode().getParentNode();
        } else {
            return node.getParentNode().getLeftNode();
        }
    }

    public BinaryNode<T> getRoot(BinaryNode<T> node) {
        if (node == null) return null;
        while (node.getParentNode() != null) {
            node = node.getParentNode();
        }
        return node;
    }

    // Setters
    public void setLeftChild(int key, T value) {
        BinaryNode<T> node = current.getLeftNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setValue(value);
        node.setParentNode(current);
        current.setLeftNode(node);
    }

    public void setRightChild(int key, T value) {
        BinaryNode<T> node = current.getRightNode();
        if (node == null) {
            node = new BinaryNode<T>();
        }
        node.setKey(key);
        node.setValue(value);
        node.setParentNode(current);
        current.setRightNode(node);
    }

    public void setColour(BinaryNode<T> node, T value) {
        node.setValue(value);
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

    // Insertion
    public void insert(int insert_key, T value) {
        BinaryNode<T> node = new BinaryNode<T>(insert_key, value);

        if (current == null) {
            current = node;
            adjustAfterInsert(current);
            return;
        }

        if (current.getKey() < insert_key) {
            if (current.getRightNode() == null) {
                setRightChild(node.getKey(), value);
                adjustAfterInsert(current.getRightNode());
            } else {
                moveToRightNode();
                insert(insert_key, value);
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                setLeftChild(node.getKey(), value);
                adjustAfterInsert(current.getLeftNode());
            } else {
                moveToLeftNode();
                insert(insert_key, value);
            }
        }
        current = getRoot(current);
    }

    public void adjustAfterInsert(BinaryNode<T> node) {
        // colour the node red
        setColour(node, (T)"Red");

        if (node != null && node.getParentNode() != null && node.getParentNode().getValue().equals("Red")) {
            BinaryNode gp = getGrandParent(node);

            if (getUncle(node) != null && getUncle(node).getValue().equals("Red")) {
                setColour(node.getParentNode(), (T) "Black");
                setColour(getUncle(node), (T) "Black");
                setColour(getGrandParent(node), (T) "Red");
                adjustAfterInsert(getGrandParent(node));
            }
            else if (node.getParentNode() == gp.getLeftNode()) {
                if (node == node.getParentNode().getRightNode()) {
                    leftRotation(node.getParentNode());
                }
                setColour(node.getParentNode(), (T) "Black");
                setColour(getGrandParent(node), (T) "Red");
                rightRotation(getGrandParent(node));
            }
            else if (node.getParentNode() == gp.getRightNode()) {
                if (node == node.getParentNode().getLeftNode()) {
                    rightRotation(node.getParentNode());
                }
                setColour(node.getParentNode(), (T) "Black");
                setColour(getGrandParent(node), (T) "Red");
                leftRotation(getGrandParent(node));
            }
        }

        setColour(getRoot(node), (T) "Black");
    }

    // Deletion
    public void delete(int delete_key) {
        if (current == null) return;

        if (current.getKey() < delete_key) {
            if (current.getRightNode() == null) return;
                moveToRightNode();
                delete(delete_key);
        } else if (current.getKey() > delete_key) {
            moveToLeftNode();
            delete(delete_key);
        } else if (current.getKey() == delete_key) {

            // Deleting a node with no children
            if (current.getLeftNode() == null && current.getRightNode() == null) {
                if (current.getParentNode().getKey() < current.getKey()) {
                    current.getParentNode().setRightNode(null);
                } else {
                    current.getParentNode().setLeftNode(null);
                }
                adjustAfterDelete(current);
            }
            // Deleting a node with one child
            else if (current.getLeftNode() == null && current.getRightNode() != null) {
                if (current.getParentNode() == null) { // Removing a root node
                    current.getRightNode().setParentNode(null);
                    current = current.getRightNode();
                    adjustAfterDelete(current);
                } else {
                    if (current.getParentNode().getKey() < current.getRightNode().getKey()) {
                        current.getParentNode().setRightNode(current.getRightNode());
                        current.getRightNode().setParentNode(current.getParentNode());
                        adjustAfterDelete(current.getParentNode().getRightNode());
                    } else {
                        current.getParentNode().setLeftNode(current.getRightNode());
                        current.getRightNode().setParentNode(current.getParentNode());
                        adjustAfterDelete(current.getParentNode().getLeftNode());
                    }
                }
            } else if (current.getLeftNode() != null && current.getRightNode() == null) {
                if (current.getParentNode() == null) { // removing a root node
                    current.getLeftNode().setParentNode(null);
                    current = current.getLeftNode();
                    adjustAfterDelete(current);
                } else {
                    if (current.getParentNode().getKey() < current.getLeftNode().getKey()) {
                        current.getParentNode().setRightNode(current.getLeftNode());
                        current.getLeftNode().setParentNode(current.getParentNode());
                        adjustAfterDelete( current.getParentNode().getRightNode());
                    } else {
                        current.getParentNode().setLeftNode(current.getLeftNode());
                        current.getLeftNode().setParentNode(current.getParentNode());
                        adjustAfterDelete(current.getParentNode().getLeftNode());
                    }
                }
            }
            // Deleting a node with two children
            else {
                BinaryNode<T> successor = find_successor();
                delete(successor.getKey());
                current.setKey(successor.getKey());
                adjustAfterDelete(current);
            }
        }
        current = getRoot(current);
    }

    public void adjustAfterDelete(BinaryNode<T> node) {
        if (node == null) return;
        while (node.getParentNode() != null && node.getValue().equals("Black")) {
            if (node == node.getParentNode().getLeftNode()) {
                BinaryNode<T> sibling = getSibling(node);
                if (sibling != null && sibling.getValue().equals("Red")) {
                    setColour(sibling, (T) "Black");
                    setColour(node.getParentNode(), (T) "Red");
                    leftRotation(node.getParentNode());
                    sibling = node.getParentNode().getRightNode();
                }
                if (sibling != null && sibling.getLeftNode() != null && sibling.getRightNode() != null &&
                        sibling.getLeftNode().getValue().equals("Black") && sibling.getRightNode().getValue().equals("Black")) {
                    setColour(sibling, (T) "Red");
                    node = node.getParentNode();
                } else if (sibling.getRightNode() != null && sibling.getRightNode().getValue().equals("Black")) {
                    if (sibling.getLeftNode() != null) {
                        setColour(sibling.getLeftNode(), (T) "Black");
                    }
                    setColour(sibling, (T) "Red");
                    rightRotation(sibling);
                    sibling = node.getParentNode().getRightNode();
                }
                setColour(sibling, node.getParentNode().getValue());
                setColour(node.getParentNode(), (T) "Black");
                setColour(sibling.getRightNode(), (T) "Black");
                leftRotation(node.getParentNode());
            } else {
                BinaryNode<T> sibling = getSibling(node);
                if (sibling != null && sibling.getValue().equals("Red")) {
                    setColour(sibling, (T) "Black");
                    setColour(node.getParentNode(), (T) "Red");
                    rightRotation(node.getParentNode());
                    sibling = node.getParentNode().getLeftNode();
                }
                if (sibling != null && sibling.getLeftNode() != null && sibling.getRightNode() != null &&
                        sibling.getLeftNode().getValue().equals("Black") && sibling.getRightNode().getValue().equals("Black")) {
                    setColour(sibling, (T) "Red");
                    node = node.getParentNode();
                } else if (sibling.getLeftNode() != null && sibling.getLeftNode().getValue().equals("Black")) {
                    if (sibling.getRightNode() != null) {
                        setColour(sibling.getRightNode(), (T) "Black");
                    }
                    setColour(sibling, (T) "Red");
                    leftRotation(sibling);
                    sibling = node.getParentNode().getLeftNode();
                }
                setColour(sibling, node.getParentNode().getValue());
                setColour(node.getParentNode(), (T) "Black");
                setColour(sibling.getLeftNode(), (T) "Black");
                rightRotation(node.getParentNode());
            }
        }
        setColour(node, (T) "Black");
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

    public void rightRotation(BinaryNode<T> node) {
        BinaryNode<T> newRoot = node.getLeftNode();

        if (newRoot.getRightNode() != null) {
            node.setLeftNode(newRoot.getRightNode());
        } else {
            node.setLeftNode(null);
        }

        if (node.getParentNode() != null) {
            if (node == node.getParentNode().getLeftNode()) {
                node.getParentNode().setLeftNode(newRoot);
            } else {
                node.getParentNode().setRightNode(newRoot);
            }
        }
        newRoot.setRightNode(node);
        newRoot.setParentNode(node.getParentNode());
        node.setParentNode(newRoot);

        if (node.getLeftNode() != null) {
            node.getLeftNode().setParentNode(node);
        }

        node = newRoot;
    }

    public void leftRotation(BinaryNode<T> node) {
        BinaryNode<T> newRoot = node.getRightNode();

        if (newRoot.getLeftNode() != null) {
            node.setRightNode(newRoot.getLeftNode());
        } else {
            node.setRightNode(null);
        }

        if (node.getParentNode() != null) {
            if (node == node.getParentNode().getLeftNode()) {
                node.getParentNode().setLeftNode(newRoot);
            } else {
                node.getParentNode().setRightNode(newRoot);
            }
        }
        newRoot.setLeftNode(node);
        newRoot.setParentNode(node.getParentNode());
        node.setParentNode(newRoot);

        if (node.getRightNode() != null) {
            node.getRightNode().setParentNode(node);
        }

        node = newRoot;
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