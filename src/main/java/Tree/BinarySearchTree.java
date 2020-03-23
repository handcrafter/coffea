package Tree;

import Node.BinaryNode;


public class BinarySearchTree<T> extends BinaryTree<T> {
    BinaryNode<T> root;

    public BinarySearchTree() { current = root = null; }

    public BinarySearchTree(int key, T value) {
        current = new BinaryNode<T>(key, value);
        root = current;
    }

    private void setLeftChild(BinaryNode<T> leftChild, BinaryNode<T> parent) {
        assert parent == null : "Parent node must not be null";
        parent.setLeftNode(leftChild);
        leftChild.setParentNode(parent);
    }

    private void setRightChild(BinaryNode<T> rightChild, BinaryNode<T> parent) {
        assert parent == null : "Parent node must not be null";
        parent.setRightNode(rightChild);
        rightChild.setParentNode(parent);
    }

    public boolean contains(int key) {
        BinaryNode<T> node = getNode(key);
        return node != null;
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

    protected BinaryNode<T> getSuccessor (BinaryNode<T> node) {
        assert node == null : "Input node must not be null";

        if (node.getRightNode() != null) {
            node = node.getRightNode();
            while (node.getLeftNode() != null) {
                node = node.getLeftNode();
            }
        } else {
            node = null;
        }
        return node;
    }

    protected BinaryNode<T> getPredecessor (BinaryNode<T> node) {
        assert node == null : "Input node must not be null";

        if (node.getLeftNode() != null) {
            node = node.getLeftNode();
            while (node.getRightNode() != null) {
                node = node.getRightNode();
            }
        } else {
            node = null;
        }
        return node;
    }

    protected void swapWithSuccessor(BinaryNode<T> deleteNode, BinaryNode<T> successor) {
        deleteNode.setKey(successor.getKey());
        deleteNode.setValue(successor.getValue());

        BinaryNode<T> parent = successor.getParentNode();
        if (successor.getRightNode() != null) {
            if (successor.getKey() < parent.getKey()) {
                linkNodes(parent, successor.getRightNode(), 'L');
            } else {
                linkNodes(parent, successor.getRightNode(), 'R');
            }
        } else {
            if (successor.getKey() < parent.getKey()) {
                successor.getParentNode().setLeftNode(null);
            } else {
                successor.getParentNode().setRightNode(null);
            }
        }
    }

    protected void swapWithPredecessor(BinaryNode<T> deleteNode, BinaryNode<T> predecessor) {
        deleteNode.setKey(predecessor.getKey());
        deleteNode.setValue(predecessor.getValue());

        BinaryNode<T> parent = predecessor.getParentNode();
        if (predecessor.getLeftNode() != null) {
            if (predecessor.getKey() < parent.getKey()) {
                linkNodes(parent, predecessor.getLeftNode(), 'L');
            } else {
                linkNodes(parent, predecessor.getLeftNode(), 'R');
            }
        } else {
            if (predecessor.getKey() < parent.getKey()) {
                parent.setLeftNode(null);
            } else {
                parent.setRightNode(null);
            }
        }
    }

    public void delete(int delete_key) {
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
                    current = parent_node;
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
        current = root;

        while (true) {
            if (current.getKey() == insert_key) {
                current.setValue(value);
                break;
            }
            if (current.getKey() < insert_key) {
                if (current.getRightNode() == null) {
                    BinaryNode<T> node = new BinaryNode<T>(insert_key, value);
                    setRightChild(node, current);
                    break;
                } else {
                    moveToRightNode();
                }
            } else if (current.getKey() > insert_key) {
                if (current.getLeftNode() == null) {
                    BinaryNode<T> node = new BinaryNode<T>(insert_key, value);
                    setLeftChild(node, current);
                    break;
                } else {
                    moveToLeftNode();
                }
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
