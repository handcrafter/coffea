package Tree;

import Node.AVLNode;


public class AVLtree<T> extends BinarySearchTree<T> {

    public AVLtree() { current = root = null; }

    public AVLtree(int key, T value, int height) {
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

    public int getDepthDifference(AVLNode<T> target) {
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

    public void balanceTreeAfterInsertion(int key, AVLNode<T> node) {
        int depth_difference = getDepthDifference(node);
        if (depth_difference > 1 && key < node.getLeftNode().getKey()) { // Left Left case
            rightRotation();
        } else if (depth_difference < -1 && key > node.getRightNode().getKey()) { // Right Right case
            leftRotation();
        } else if (depth_difference > 1 && key > node.getLeftNode().getKey()) { //Left Right case
            leftRightRotation();
        } else if (depth_difference < -1 && key < node.getRightNode().getKey()) { // Right Left case
            rightLeftRotation();
        }
    }

    public void balanceTreeAfterDeletion(AVLNode<T> node) {
        int depth_difference = getDepthDifference(node);
        if (depth_difference > 1 && getDepthDifference((AVLNode<T>) node.getLeftNode()) >= 0) { // Left Left case
            rightRotation();
        } else if (depth_difference < -1 && getDepthDifference((AVLNode<T>) node.getRightNode()) <= 0) { // Right Right case
            leftRotation();
        } else if (depth_difference > 1 && getDepthDifference((AVLNode<T>) node.getLeftNode()) < 0) { //Left Right case
            leftRightRotation();
        } else if (depth_difference < -1 && getDepthDifference((AVLNode<T>) node.getRightNode()) > 0) { // Right Left case
            rightLeftRotation();
        }
    }

    public void AVLinsert(int insert_key, T value) {
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
                AVLinsert(insert_key, value);
                moveToParentNode();
            }
        } else if (current.getKey() > insert_key) {
            if (current.getLeftNode() == null) {
                AVLNode<T> node = new AVLNode<T>(insert_key, value, 0);
                setLeft(node, current);
            } else {
                moveToLeftNode();
                AVLinsert(insert_key, value);
                moveToParentNode();
            }
        }
        updateHeight((AVLNode<T>) current);
        balanceTreeAfterInsertion(insert_key, (AVLNode<T>) current);
    }

    public void AVLdelete(int delete_key) {
        // perform standard bst deletion
        AVLNode<T> deleteNode = (AVLNode<T>) getNode(delete_key);
        if (deleteNode == null) return;
        // If target node is a leaf node, move the pointer to parent node before deletion
        if (deleteNode.getLeftNode() == null && deleteNode.getRightNode() == null) {
            deleteNode = (AVLNode<T>) deleteNode.getParentNode();
        }
        delete(delete_key);
        if (deleteNode.getParentNode() == null) {
            updateHeight(deleteNode);
            balanceTreeAfterDeletion((AVLNode<T>) deleteNode);
        }
        while (deleteNode.getParentNode() != null) {
            updateHeight(deleteNode);
            balanceTreeAfterDeletion((AVLNode<T>) deleteNode);
            deleteNode = (AVLNode<T>) deleteNode.getParentNode();
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
