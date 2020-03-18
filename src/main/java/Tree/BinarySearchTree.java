package Tree;

import Node.BinaryNode;


public class BinarySearchTree<T> extends BinaryTree<T> {
    BinaryNode<T> root;

    public BinarySearchTree() { current = root = null; }

    public BinarySearchTree(int key, T value) {
        current = new BinaryNode<T>(key, value);
        root = current;
    }

    private void setLeft(BinaryNode<T> leftChild, BinaryNode<T> parent) {
        assert parent == null : "Parent node must not be null";
        parent.setLeftNode(leftChild);
        leftChild.setParentNode(parent);
    }

    private void setRight(BinaryNode<T> rightChild, BinaryNode<T> parent) {
        assert parent == null : "Parent node must not be null";
        parent.setRightNode(rightChild);
        rightChild.setParentNode(parent);
    }

    public boolean contains(int key) {
        BinaryNode<T> node = getNode(key);
        if (node == null) {
            return false;
        } else {
            return true;
        }
    }

    public T getValue(int key) {
        BinaryNode<T> node = getNode(key);
        assert node == null : "key does not exist";
        return node.getValue();
    }

    protected BinaryNode<T> getNode (int key) {
        BinaryNode<T> result = null;
        BinaryNode<T> originalPosition = current;
        current = root;

        while (current != null) {
            if (current.getKey() == key) {
                result = current;
                break;
            } else if (current.getKey() < key) {
                moveToRightNode();
            } else if (current.getKey() > key) {
                moveToLeftNode();
            }
        }

        current = originalPosition;
        return result;
    }

    protected BinaryNode<T> getSuccessor (BinaryNode<T> node) {
        assert node == null : "Input node must not be null";

        node = node.getRightNode();
        while (node.getLeftNode() != null) {
            node = node.getLeftNode();
        }
        return node;
    }

    protected BinaryNode<T> getPredecessor (BinaryNode<T> node) {
        assert node == null : "Input node must not be null";

        node = node.getLeftNode();
        while (node.getRightNode() != null) {
            node = node.getRightNode();
        }

        return node;
    }

    private void swapWithSuccessor(BinaryNode<T> deleteNode, BinaryNode<T> successor) {
        deleteNode.setKey(successor.getKey());
        deleteNode.setValue(successor.getValue());

        int successorKey = successor.getKey();
        int parentKey = successor.getParentNode().getKey();
        if (successor.getRightNode() != null) {
            if (successorKey < parentKey) {
                successor.getParentNode().setLeftNode(successor.getRightNode());
                successor.getRightNode().setParentNode(successor.getParentNode());
            } else {
                successor.getParentNode().setRightNode(successor.getRightNode());
                successor.getRightNode().setParentNode(successor.getParentNode());
            }
        } else {
            if (successorKey < parentKey) {
                successor.getParentNode().setLeftNode(null);
            } else {
                successor.getParentNode().setRightNode(null);
            }
        }
    }

    public void swapWithPredecessor(BinaryNode<T> deleteNode, BinaryNode<T> predecessor) {
        deleteNode.setKey(predecessor.getKey());
        deleteNode.setValue(predecessor.getValue());

        int predecessorKey = predecessor.getKey();
        int parentKey = predecessor.getParentNode().getKey();
        if (predecessor.getLeftNode() != null) {
            if (predecessorKey < parentKey) {
                predecessor.getParentNode().setLeftNode(predecessor.getLeftNode());
                predecessor.getLeftNode().setParentNode(predecessor.getParentNode());
            } else {
                predecessor.getParentNode().setRightNode(predecessor.getLeftNode());
                predecessor.getLeftNode().setParentNode(predecessor.getParentNode());
            }
        } else {
            if (predecessorKey < parentKey) {
                predecessor.getParentNode().setLeftNode(null);
            } else {
                predecessor.getParentNode().setRightNode(null);
            }
        }
    }

    public void delete (int delete_key) {
        BinaryNode<T> deleteNode = getNode(delete_key);
        if (deleteNode == null) return;

        BinaryNode<T> successor = getSuccessor(deleteNode);
        if (successor == null) {
            BinaryNode<T> predecessor = getPredecessor(deleteNode);
            if (predecessor == null) {
                // success and predecessor do not exist

                BinaryNode<T> parent_node = deleteNode.getParentNode();
                if (parent_node == null) {
                    root = null;
                    current = null;
                } else {
                    int parent_key = parent_node.getKey();
                    if (delete_key < parent_key) {
                        parent_node.setLeftNode(null);
                    } else {
                        parent_node.setRightNode(null);
                    }
                }
            } else {
                swapWithPredecessor(deleteNode, predecessor);
            }
        } else {
            swapWithSuccessor(deleteNode, successor);
        }
    }

    public void insert(int insert_key, T value) {
        if (root == null) {
            BinaryNode<T> node = new BinaryNode<T>(insert_key, value);
            root = current = node;
            return;
        }

        BinaryNode<T> originalPosition = current;
        if (current.getKey() == insert_key) {
          current.setValue(value);
        } if (current.getKey() < insert_key) {
            if (current.getRightNode() == null) {
                BinaryNode<T> node = new BinaryNode<T>(insert_key, value);
                setRight(node, current);
            } else {
                moveToRightNode();
                insert(insert_key, value);
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                BinaryNode<T> node = new BinaryNode<T>(insert_key, value);
                setLeft(node, current);
            } else {
                moveToLeftNode();
                insert(insert_key, value);
            }
        }
        current = originalPosition;
    }

    public void printInorder() {
        if (current == null) return;

        if (current.getLeftNode() != null) {
            moveToLeftNode();
            printInorder();
            moveToParentNode();
        }

        current.print();

        if (current.getRightNode() != null) {
            moveToRightNode();
            printInorder();
            moveToParentNode();
        }
    }
}
