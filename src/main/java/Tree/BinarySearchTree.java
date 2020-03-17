package Tree;

import Node.BinaryNode;


public class BinarySearchTree<T> extends BinaryTree<T> {
    BinaryNode<T> root;

    public BinarySearchTree() { current = root = null; }

    public BinarySearchTree(int key, T value) {
        current = new BinaryNode<T>(key, value);
        root = current;
    }

    public void setLeft(BinaryNode<T> leftChild, BinaryNode<T> parent) {
        assert parent == null : "Parent node must not be null";
        parent.setLeftNode(leftChild);
        leftChild.setParentNode(parent);
    }

    public void setRight(BinaryNode<T> rightChild, BinaryNode<T> parent) {
        assert parent == null : "Parent node must not be null";
        parent.setRightNode(rightChild);
        rightChild.setParentNode(parent);
    }

    public boolean contains(int key) {
        Boolean result = false;

        while (current != null) {
            if (current.getKey() == key) {
                result = true;
                break;
            } else if (current.getKey() < key) {
                moveToRightNode();
            } else if (current.getKey() > key) {
                moveToLeftNode();
            }
        }

        current = root;
        return result;
    }

    public T get_value(int key) {
        T result = null;

        while (current != null) {
            if (current.getKey() == key) {
                result = current.getValue();
                break;
            } else if (current.getKey() < key) {
                moveToRightNode();
            } else if (current.getKey() > key) {
                moveToLeftNode();
            }
        }

        current = root;
        return result;
    }

    protected BinaryNode<T> get_node (int key) {
        BinaryNode<T> result = null;

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

        current = root;
        return result;
    }

    protected BinaryNode<T> get_successor (BinaryNode<T> node) {
        assert node == null : "node must not be null";
        BinaryNode<T> successor = null;

        if (node.getLeftNode() == null && node.getRightNode() == null) {
            successor = node;
        } else if (node.getRightNode() != null){
            node = node.getRightNode();
            while (node.getLeftNode() != null) {
                node = node.getLeftNode();
            }
            successor = node;
        } else if (node.getLeftNode() != null) {
            node = node.getLeftNode();
            while (node.getRightNode() != null) {
                node = node.getRightNode();
            }
            successor = node;
        }
        return successor;
    }

    public void delete (int delete_key) {
        BinaryNode<T> deleteNode = get_node(delete_key);
        BinaryNode<T> successor = get_successor(deleteNode);

        // key not in tree
        if (get_node(delete_key) == null) return;

        if (deleteNode == successor) {
            current = null;
        } else {
            deleteNode.setKey(successor.getKey());
            deleteNode.setValue(successor.getValue());
            // delete successor node
            if (successor.getLeftNode() == null && successor.getRightNode() == null) {
                if (successor == successor.getParentNode().getLeftNode()) {
                    successor.getParentNode().setLeftNode(null);
                } else {
                    successor.getParentNode().setRightNode(null);
                }
            } else if (successor.getLeftNode() != null) {
                if (successor == successor.getParentNode().getLeftNode()) {
                    successor.getParentNode().setLeftNode(successor.getLeftNode());
                    successor.getLeftNode().setParentNode(successor.getParentNode());
                } else {
                    successor.getParentNode().setRightNode(successor.getLeftNode());
                    successor.getLeftNode().setParentNode(successor.getParentNode());
                }
            } else if (successor.getRightNode() != null) {
                if (successor == successor.getParentNode().getLeftNode()) {
                    successor.getParentNode().setLeftNode(successor.getRightNode());
                    successor.getRightNode().setParentNode(successor.getParentNode());
                } else {
                    successor.getParentNode().setRightNode(successor.getRightNode());
                    successor.getRightNode().setParentNode(successor.getParentNode());
                }
            }
        }
    }

    public void insert(int insert_key, T value) {
        if (current == null) {
            BinaryNode<T> node = new BinaryNode<T>(insert_key, value);
            current = node;
        }

        if (current.getKey() < insert_key) {
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
        current = root;
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
