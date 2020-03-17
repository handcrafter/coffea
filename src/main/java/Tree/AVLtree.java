package Tree;

import Node.AVLNode;


public class AVLtree<T> extends BinarySearchTree<T> {

    public AVLtree() { current = null; }

    public AVLtree(int key, T value) { current = new AVLNode<T>(key, value); }

    public void moveToLeftNode() { current = (AVLNode<T>) current.getLeftNode(); }

    public void moveToRightNode() { current = (AVLNode<T>) current.getRightNode(); }

    public void moveToParentNode() { current = (AVLNode<T>) current.getParentNode(); }

    @Override
    public void setLeftChild(int key, T value) {
        AVLNode<T> node = (AVLNode<T>) current.getLeftNode();
        if (node == null) {
            node = new AVLNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        node.setValue(value);
        node.setHeight(0);
        current.setLeftNode(node);
    }

    @Override
    public void setRightChild(int key, T value) {
        AVLNode<T> node = (AVLNode<T>) current.getRightNode();
        if (node == null) {
            node = new AVLNode<T>();
        }
        node.setKey(key);
        node.setParentNode(current);
        node.setValue(value);
        node.setHeight(0);
        current.setRightNode(node);
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

    public int getDepthDifference(AVLNode<T> target) {
        AVLNode<T> rightNode = (AVLNode<T>) target.getRightNode();
        AVLNode<T> leftNode = (AVLNode<T>) target.getLeftNode();

        if (leftNode == null && rightNode != null) {
            return -rightNode.getHeight() - 1;
        } else if (leftNode != null && rightNode == null) {
            return leftNode.getHeight() + 1;
        } else {
            return leftNode.getHeight() - rightNode.getHeight();
        }
    }

    public void bst_insert(int insert_key, T value) {
        AVLNode<T> node = new AVLNode<T>(insert_key, value);

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
        updateHeight((AVLNode<T>) current);
    }

    public void insert(int key, T value) {
        // Perform standard bst insertion
        bst_insert(key, value);

        // balance the tree
        int depth_difference = getDepthDifference((AVLNode<T>) current);
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
            if (current.getRightNode() == null) return;
            moveToRightNode();
            bst_delete(delete_key);
            moveToParentNode();
        } else if (current.getKey() > delete_key) {
            if (current.getLeftNode() == null) return;
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
                    current = (AVLNode<T>) current.getRightNode();
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
                    current = (AVLNode<T>) current.getLeftNode();
                } else {
                    if (current.getParentNode().getKey() < current.getLeftNode().getKey()) {
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
                AVLNode<T> successor = find_successor();
                bst_delete(successor.getKey());
                current.setKey(successor.getKey());
            }
        }
        updateHeight((AVLNode<T>) current);
    }

    public AVLNode<T> find_successor() {
        AVLNode<T> original_position = (AVLNode<T>) current;
        moveToRightNode();
        while (current.getLeftNode() != null) {
            moveToLeftNode();
        }
        AVLNode<T> successor = (AVLNode<T>) current;
        current = original_position;
        return successor;
    }

    public void delete(int delete_key) {
        // perform standard bst deletion
        bst_delete(delete_key);

        // balance the tree
        int depth_difference = getDepthDifference((AVLNode<T>) current);
        if (depth_difference > 1 && getDepthDifference((AVLNode<T>) current.getLeftNode()) >= 0) { // Left Left case
            rightRotation();
        } else if (depth_difference < -1 && getDepthDifference((AVLNode<T>) current.getRightNode()) <= 0) { // Right Right case
            leftRotation();
        } else if (depth_difference > 1 && getDepthDifference((AVLNode<T>) current.getLeftNode()) < 0) { //Left Right case
            moveToLeftNode();
            leftRotation();
            moveToParentNode();
            rightRotation();
        } else if (depth_difference < -1 && getDepthDifference((AVLNode<T>) current.getRightNode()) > 0) { // Right Left case
            moveToRightNode();
            rightRotation();
            moveToParentNode();
            leftRotation();
        }
    }

    public void rightRotation() {
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
    }

    public void leftRotation() {
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
}
