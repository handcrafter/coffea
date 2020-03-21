package Tree;

import Node.AVLNode;


public class AVLTree<T> extends BinarySearchTree<T> {

    public AVLTree() { current = root = null; }

    public AVLTree(int key, T value, int height) {
        current = new AVLNode<T>(key, value, height);
        root = current;
    }

    public void updateHeight(AVLNode<T> node) {
        AVLNode<T> rightNode = (AVLNode<T>) node.getRightNode();
        AVLNode<T> leftNode = (AVLNode<T>) node.getLeftNode();

        if (leftNode == null && rightNode == null) {
            node.setHeight(0);
        } else if (leftNode == null && rightNode != null) {
            node.setHeight(1 + rightNode.getHeight());
        } else if (leftNode != null && rightNode == null) {
            node.setHeight(1 + leftNode.getHeight());
        } else {
            node.setHeight(1 + Math.max(leftNode.getHeight(), rightNode.getHeight()));
        }
    }

    public int getHeightDifference(AVLNode<T> target) {
        AVLNode<T> rightNode = (AVLNode<T>) target.getRightNode();
        AVLNode<T> leftNode = (AVLNode<T>) target.getLeftNode();

        if (leftNode == null && rightNode == null) {
            return 0;
        } else if (leftNode == null && rightNode != null) {
            return -rightNode.getHeight() - 1;
        } else if (leftNode != null && rightNode == null) {
            return leftNode.getHeight() + 1;
        } else {
            return leftNode.getHeight() - rightNode.getHeight();
        }
    }

    public void balanceTreeAfterInsertion(int key, AVLNode<T> node, int heightDifference) {
        if (heightDifference > 1) {
            if (key < node.getLeftNode().getKey()) {
                rightRotation();
            } else {
                leftRightRotation();
            }
        } else {
            if (key > node.getRightNode().getKey()) {
                leftRotation();
            } else {
                rightLeftRotation();
            }
        }
    }

    public void balanceTreeAfterDeletion(AVLNode<T> node, int heightDifference) {
        AVLNode<T> originalPosition = (AVLNode<T>) current;
        current = node;
        if (heightDifference > 1) {
            if (getHeightDifference((AVLNode<T>) node.getLeftNode()) >= 0) {
                rightRotation();
            } else {
                leftRightRotation();
            }
        } else {
            if (getHeightDifference((AVLNode<T>) node.getRightNode()) <= 0) {
                leftRotation();
            } else {
                rightLeftRotation();
            }
        }
        current = originalPosition;
    }

    public void insert(int insert_key, T value) {
        if (root == null) {
            AVLNode<T> node = new AVLNode<T>(insert_key, value, 0);
            root = current = node;
            return;
        }

        if (current.getKey() == insert_key) {
          current.setValue(value);
        } if (current.getKey() < insert_key) {
            if (current.getRightNode() == null) {
                AVLNode<T> node = new AVLNode<T>(insert_key, value, 0);
                setRight(node, current);
            } else {
                moveToRightNode();
                insert(insert_key, value);
                moveToParentNode();
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                AVLNode<T> node = new AVLNode<T>(insert_key, value, 0);
                setLeft(node, current);
            } else {
                moveToLeftNode();
                insert(insert_key, value);
                moveToParentNode();
            }
        }
        updateHeight((AVLNode<T>) current);

        int heightDifference = getHeightDifference((AVLNode<T>) current);
        if (heightDifference > 1 || heightDifference < -1) {
            balanceTreeAfterInsertion(insert_key, (AVLNode<T>) current, heightDifference);
        }
    }

    protected void updateHeightAfterDeletion(AVLNode<T> node) {
        assert node == null : "Height of null node cannot be updated";

        while (node != null) {
            updateHeight(node);
            int heightDifference = getHeightDifference((AVLNode<T>) node);
            if (heightDifference > 1 || heightDifference < -1) {
                balanceTreeAfterDeletion((AVLNode<T>) node, heightDifference);
            }
            node = (AVLNode<T>) node.getParentNode();
        }
    }

    public void delete(int delete_key) {
        AVLNode<T> deleteNode = (AVLNode<T>) getNode(delete_key);
        if (deleteNode == null) return;

        AVLNode<T> successor = (AVLNode<T>) getSuccessor(deleteNode);
        if (successor == null) {
            AVLNode<T> predecessor = (AVLNode<T>) getPredecessor(deleteNode);
            if (predecessor == null) {
                // success and predecessor do not exist

                AVLNode<T> parent_node = (AVLNode<T>) deleteNode.getParentNode();
                if (parent_node == null) {
                    root = null;
                    current = null;
                } else {
                    int parent_key = parent_node.getKey();
                    if (delete_key < parent_key) {
                        parent_node.setLeftNode(null);
                        updateHeightAfterDeletion(parent_node);
                    } else {
                        parent_node.setRightNode(null);
                        updateHeightAfterDeletion(parent_node);
                    }
                }
            } else {
                swapWithPredecessor(deleteNode, predecessor);
                updateHeightAfterDeletion((AVLNode<T>) predecessor.getParentNode());
            }
        } else {
            swapWithSuccessor(deleteNode, successor);
            updateHeightAfterDeletion((AVLNode<T>) successor.getParentNode());
        }
    }

    protected void rightRotation() {
        AVLNode<T> newRoot = (AVLNode<T>) current.getLeftNode();

        if (newRoot.getRightNode() != null) {
            current.setLeftNode(newRoot.getRightNode());
        } else {
            current.setLeftNode(null);
        }

        if (current.getParentNode() != null) {
            if (current == current.getParentNode().getLeftNode()) {
                current.getParentNode().setLeftNode(newRoot);
            } else {
                current.getParentNode().setRightNode(newRoot);
            }
        }

        newRoot.setRightNode(current);
        newRoot.setParentNode(current.getParentNode());
        current.setParentNode(newRoot);

        if (current.getLeftNode() != null) {
            current.getLeftNode().setParentNode(current);
        }

        updateHeight((AVLNode<T>) current);
        updateHeight(newRoot);
        current = newRoot;

        if (current.getParentNode() == null) {
            root = current;
        }
    }

    protected void leftRotation() {
        AVLNode<T> newRoot = (AVLNode<T>) current.getRightNode();

        if (newRoot.getLeftNode() != null) {
            current.setRightNode(newRoot.getLeftNode());
        } else {
            current.setRightNode(null);
        }

        if (current.getParentNode() != null) {
            if (current == current.getParentNode().getLeftNode()) {
                current.getParentNode().setLeftNode(newRoot);
            } else {
                current.getParentNode().setRightNode(newRoot);
            }
        }
        newRoot.setLeftNode(current);
        newRoot.setParentNode(current.getParentNode());
        current.setParentNode(newRoot);

        if (current.getRightNode() != null) {
            current.getRightNode().setParentNode(current);
        }

        updateHeight((AVLNode<T>) current);
        updateHeight(newRoot);
        current = newRoot;

        if (current.getParentNode() == null) {
            root = current;
        }
    }

    protected void leftRightRotation() {
        moveToLeftNode();
        leftRotation();
        moveToParentNode();
        rightRotation();
    }

    protected void rightLeftRotation() {
        moveToRightNode();
        rightRotation();
        moveToParentNode();
        leftRotation();
    }
}
