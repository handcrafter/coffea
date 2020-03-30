package Tree;

import Node.WeightBalancedNode;


public class WeightBalancedTree<T> extends BinarySearchTree<T> {

    public WeightBalancedTree() { current = null; }

    public WeightBalancedTree(int key, T value, int weight) {
        current = new WeightBalancedNode<T>(key, value, weight);
        root = current;
    }

    public void insert(int insert_key, T value, int weight) {
        current = insertKey(insert_key, value, weight, (WeightBalancedNode<T>) root);
        if (current.getParentNode() == null) {
            root = current;
        }
    }

    private WeightBalancedNode<T> insertKey(int insert_key, T value, int weight, WeightBalancedNode<T> node) {
        if (node == null) { return null; }
        WeightBalancedNode<T> right = (WeightBalancedNode) node.getRightNode();
        WeightBalancedNode<T> left = (WeightBalancedNode) node.getLeftNode();

        if (node.getKey() < insert_key) {
            if (right == null) {
                WeightBalancedNode<T> newNode = new WeightBalancedNode(insert_key, value, weight);
                linkNodes(node, newNode, 'R');
            } else {
                insertKey(insert_key, value, weight, right);
            }
            if (((WeightBalancedNode<T>) node.getRightNode()).getWeight() > node.getWeight()) {
                node = leftRotation(node);
            }
        } else if (node.getKey() > insert_key) {
            if (left == null) {
                WeightBalancedNode<T> newNode = new WeightBalancedNode(insert_key, value, weight);
                linkNodes(node, newNode, 'L');
            } else {
                insertKey(insert_key, value, weight, left);
            }
            if (((WeightBalancedNode<T>) node.getLeftNode()).getWeight() > node.getWeight()) {
                node = rightRotation(node);
            }
        }
        return node;
    }

    public void delete(int key) {
        deleteKey(key, (WeightBalancedNode<T>) current);
        current = root;
    }

    private void deleteKey(int delete_key, WeightBalancedNode<T> node) {
        assert node == null : "Tree must not be empty";

        WeightBalancedNode<T> deleteNode = (WeightBalancedNode<T>) getNode(delete_key);

        assert deleteNode == null : "Delete key does not exist";

        WeightBalancedNode<T> right = (WeightBalancedNode) deleteNode.getRightNode();
        WeightBalancedNode<T> left = (WeightBalancedNode) deleteNode.getLeftNode();
        WeightBalancedNode<T> parent = (WeightBalancedNode) deleteNode.getParentNode();

        WeightBalancedNode<T> replacer = null;
        // Deleting a node with no children
        if (left == null && right == null) {
            if (parent == null) { // deleting the only node
                root = null;
                return;
            }
            if (parent.getKey() < deleteNode.getKey()) {
                parent.setRightNode(null);
            } else {
                parent.setLeftNode(null);
            }
        } else if (left == null && right != null) { // Deleting a node with one child
            replacer = right;
        } else if (left != null && right == null) {
            replacer = left;
        } else { // Deleting a node with two children
            deleteTwoChildrenNode(deleteNode);
        }

        if (replacer != null) {
            if (parent == null) { // Removing a root node
                replacer.setParentNode(null);
                root = replacer;
            } else {
                if (parent.getKey() < replacer.getKey()) {
                    linkNodes(parent, replacer, 'R');
                } else {
                    linkNodes(parent, replacer, 'L');
                }
            }
        }
    }

    public void deleteTwoChildrenNode(WeightBalancedNode<T> node) {
        int deleteKey = node.getKey();
        WeightBalancedNode<T> right = (WeightBalancedNode) node.getRightNode();
        WeightBalancedNode<T> left = (WeightBalancedNode) node.getLeftNode();

        if (right.getWeight() > left.getWeight()) {
            node = leftRotation(node);
            deleteKey(deleteKey, (WeightBalancedNode<T>) node.getLeftNode());
        } else {
            node = rightRotation(node);
            deleteKey(deleteKey, (WeightBalancedNode<T>) node.getRightNode());
        }
        current = node;
        if (current.getParentNode() == null) root = current;
    }

    public WeightBalancedNode<T> rightRotation(WeightBalancedNode<T> node) {
        WeightBalancedNode<T> newRoot = (WeightBalancedNode<T>) node.getLeftNode();

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

        return newRoot;
    }

    public WeightBalancedNode<T> leftRotation(WeightBalancedNode<T> node) {
        WeightBalancedNode<T> newRoot = (WeightBalancedNode<T>) node.getRightNode();

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

        return newRoot;
    }
}
