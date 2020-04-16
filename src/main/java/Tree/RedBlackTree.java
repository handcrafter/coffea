package Tree;


import Node.RedBlackNode;

public class RedBlackTree<T> extends BinarySearchTree<T> {

    public RedBlackTree() { current = root = null;}

    private RedBlackNode<T> getGrandParent(RedBlackNode<T> node) {
        if (node == null || node.getParentNode() == null || node.getParentNode().getParentNode() == null) {
            return null;
        } else {
            return (RedBlackNode<T>) node.getParentNode().getParentNode();
        }
    }

    private RedBlackNode<T> getUncle(RedBlackNode<T> node) {
        RedBlackNode<T> gp = getGrandParent(node);
        RedBlackNode<T> uncle = null;
        if (gp == null) return null;

        if (node.getParentNode().getKey() < gp.getKey()) {
            uncle = (RedBlackNode<T>) gp.getRightNode();
        } else {
            uncle = (RedBlackNode<T>) gp.getLeftNode();
        }
        return uncle;
    }

    private RedBlackNode<T> getSibling(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = null;
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        assert (node != null) : "node must not be null";

        if (parent == null) {
            sibling = null;
        } else {
            if (node.getKey() < parent.getKey()) {
                sibling = (RedBlackNode<T>) parent.getRightNode();
            } else {
                sibling = (RedBlackNode<T>) parent.getLeftNode();
            }
        }
        return sibling;
    }

    public void insert(int insert_key, T value) {
        if (root == null) {
            RedBlackNode<T> node = new RedBlackNode<T>(insert_key, value, true);
            current = node;
            root = current;
            return;
        }

        RedBlackNode<T> originalPosition = (RedBlackNode<T>) current;
        current = root;

        while (true) {
            if (current.getKey() == insert_key) {
                current.setValue(value);
                break;
            }
            if (current.getKey() < insert_key) {
                if (current.getRightNode() == null) {
                    RedBlackNode<T> node = new RedBlackNode<T>(insert_key, value, false);
                    linkNodes(current, node, 'R');
                    adjustTreeAfterInsert(node);
                    break;
                } else {
                    moveToRightNode();
                }
            } else if (current.getKey() > insert_key) {
                if (current.getLeftNode() == null) {
                    RedBlackNode<T> node = new RedBlackNode<T>(insert_key, value, false);
                    linkNodes(current, node, 'L');
                    adjustTreeAfterInsert(node);
                    break;
                } else {
                    moveToLeftNode();
                }
            }
        }
        current = originalPosition ;
    }

    private void adjustTreeAfterInsert(RedBlackNode<T> node) {
        assert node != null : "Input node must not be null";

        RedBlackNode<T> gp = getGrandParent(node);
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        RedBlackNode<T> uncle = getUncle(node);

        if (parent != null && !parent.isBlack()) { // when node is not a root and parent is red

            if (uncle != null && !uncle.isBlack()) { // uncle exist and red
                parent.setColour(true);
                uncle.setColour(true);
                gp.setColour(false);
                adjustTreeAfterInsert(gp);
            }
            else if (parent.getKey() < gp.getKey()) { // parent is left child of grandparent and uncle is black
                if (node.getKey() > parent.getKey()) {
                    leftRotation(parent);
                }
                parent.setColour(true);
                gp.setColour(false);
                rightRotation(gp);
            }
            else if (parent.getKey() > gp.getKey()) { // parent is right child if grandparnt and uncle is black
                if (node.getKey() < parent.getKey()) {
                    rightRotation(parent);
                }
                parent.setColour(true);
                gp.setColour(false);
                leftRotation(gp);
            }
        }

        RedBlackNode<T> rt = (RedBlackNode<T>) root;
        rt.setColour(true);
        root = rt;
    }

    public void delete(int delete_key) {
        RedBlackNode<T> deleteNode = (RedBlackNode<T>) getNode(delete_key);
        RedBlackNode<T> nodeToUpdate = deleteNode;

        assert (deleteNode != null) : "Delete key does not exist in the tree";

        RedBlackNode<T> successor = (RedBlackNode<T>) getSuccessor(deleteNode);
        if (successor == null) {
            RedBlackNode<T> predecessor = (RedBlackNode<T>) getPredecessor(deleteNode);
            if (predecessor == null) {
                // success and predecessor do not exist
                RedBlackNode<T> parent_node = (RedBlackNode<T>) deleteNode.getParentNode();
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

                    nodeToUpdate = deleteNode;
                    // If deleting node is a leaf and black then mark it as double black
                    if (nodeToUpdate.isBlack()) {
                        nodeToUpdate.setDoubleBlack(true);
                    }
                }
            } else { // predecessor exist
                swapWithPredecessor(deleteNode, predecessor);
                if (predecessor.isBlack() && predecessor.getRightNode() == null && predecessor.getLeftNode() == null) {
                    handleDoubleBlack(predecessor);
                }
                nodeToUpdate = (RedBlackNode<T>) deleteNode.getLeftNode();
            }
        } else { // successor exist
            swapWithSuccessor(deleteNode, successor);
            if (successor.isBlack() && successor.getLeftNode() == null && successor.getRightNode() == null) {
                handleDoubleBlack(successor);
            } else {
                nodeToUpdate = (RedBlackNode<T>) deleteNode.getRightNode();
            }
        }

        if (nodeToUpdate != null) {
            adjustTreeAfterDelete(nodeToUpdate);
        }
    }

    private void adjustTreeAfterDelete(RedBlackNode<T> node) {
        if (node.isDoubleBlack()) {
            handleDoubleBlack(node);
        } else if (node.getParentNode() != null) { // perform balancing on Tree if a node is not a root
            deleteCase1(node);
        }
    }

    private void handleDoubleBlack(RedBlackNode<T> node) { // when a black node is deleted and replaced by black child
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> siblingLeft = (RedBlackNode<T>) sibling.getLeftNode();
        RedBlackNode<T> siblingRight = (RedBlackNode<T>) sibling.getRightNode();
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();

        if (sibling.isBlack()) {
            if (node.getKey() > parent.getKey()) {
                if (siblingLeft != null && !siblingLeft.isBlack()) {
                    rightRotation(parent);
                    siblingLeft.setColour(parent.isBlack());
                } else if (siblingRight != null) {
                    leftRotation(sibling);
                    rightRotation(parent);
                    parent.setColour(true);
                } else if (parent == root) {
                    sibling.setColour(false);
                }
            } else {
                if (siblingRight != null && !siblingRight.isBlack()) {
                    leftRotation(parent);
                    siblingRight.setColour(parent.isBlack());
                } else if (siblingLeft != null) {
                    rightRotation(sibling);
                    leftRotation(parent);
                    parent.setColour(true);
                } else if (parent == root) {
                    sibling.setColour(false);
                }
            }
        } else { // when sibling is red
            if (node.getKey() < parent.getKey()) {
                leftRotation(parent);
            } else {
                rightRotation(parent);
            }
            parent.setColour(false);
            handleDoubleBlack(node);
        }
    }

    private void deleteCase1(RedBlackNode<T> node) { // Sibling is red. In this case we reverse the colors of Parent and Sibling, and then rotate left at Parent, turning Sibling into node's grandparent.
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        if (!sibling.isBlack()) {
            parent.setColour(false);
            sibling.setColour(true);
            if (node.getKey() < parent.getKey()) {
                leftRotation(parent);
            } else {
                rightRotation(parent);
            }
            node = parent;
        }
        deleteCase2(node);
    }

    private void deleteCase2(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> siblingLeft = (RedBlackNode<T>) sibling.getLeftNode();
        RedBlackNode<T> siblingRight = (RedBlackNode<T>) sibling.getRightNode();
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();

        if (parent.isBlack() && sibling.isBlack()) { // P, S, and S's children are black. In this case, we simply repaint S red.
            if (siblingLeft != null && siblingRight != null && siblingLeft.isBlack() && siblingRight.isBlack()) {
                sibling.setColour(false);
                if (node.getParentNode() != null) {
                    deleteCase1(node);
                }
            } else if (node.getLeftNode() == null && node.getRightNode() == null) {
                node.setColour(true);
            }
        } else if (!parent.isBlack() && sibling.isBlack()) { // S and S's children are black, but P is red. In this case, we simply exchange the colors of S and P.
            if (siblingLeft != null && siblingRight != null && siblingLeft.isBlack() && siblingRight.isBlack()) {
                if (!node.isBlack()) {
                    sibling.setColour(false);
                    parent.setColour(true);
                }
            }
        } else {
            deleteCase3(node);
        }
    }

    private void deleteCase3(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> siblingLeft = (RedBlackNode<T>) sibling.getLeftNode();
        RedBlackNode<T> siblingRight = (RedBlackNode<T>) sibling.getRightNode();
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();

        if (sibling.isBlack()) { // S is black, S's left child is red, S's right child is black, and N is the left child of its parent. In this case we rotate right at S, so that S's left child becomes S's parent and N's new sibling.
            if (node.getKey() < parent.getKey() && siblingRight.isBlack() && !siblingLeft.isBlack()) {
                sibling.setColour(false);
                siblingLeft.setColour(true);
                rightRotation(sibling);
            }  else if (node.getKey() < parent.getKey() && siblingLeft.isBlack() && !siblingRight.isBlack()) { // S is black, S's right child is red, and N is the left child of its parent P. In this case we rotate left at P, so that S becomes the parent of P and S's right child.
                sibling.setColour(false);
                siblingRight.setColour(true);
                leftRotation(sibling);
            }
        } else {
            sibling.setColour(parent.isBlack());
            parent.setColour(true);

            if (node.getKey() < parent.getKey()) {
                siblingRight.setColour(true);
                leftRotation(parent);
            } else {
                siblingLeft.setColour(true);
                rightRotation(parent);
            }
        }
    }

    protected void rightRotation(RedBlackNode<T> oldRoot) {
        RedBlackNode<T> newRoot = (RedBlackNode<T>) oldRoot.getLeftNode();

        if (newRoot.getRightNode() != null) {
            linkNodes(oldRoot, newRoot.getRightNode(), 'L');
        } else {
            linkNodes(oldRoot, null, 'L');
        }

        RedBlackNode<T> parentNode = (RedBlackNode<T>) oldRoot.getParentNode();
        if (parentNode != null) {
            if (oldRoot == parentNode.getLeftNode()) {
                linkNodes(parentNode, newRoot, 'L');
            } else {
                linkNodes(parentNode, newRoot, 'R');
            }
        }

        newRoot.setRightNode(oldRoot);
        newRoot.setParentNode(parentNode);
        oldRoot.setParentNode(newRoot);

        if (oldRoot.getLeftNode() != null) {
            oldRoot.getLeftNode().setParentNode(oldRoot);
        }

        if (parentNode == null) {
            root = newRoot;
        }
    }

    protected void leftRotation(RedBlackNode<T> oldRoot) {
        RedBlackNode<T> newRoot = (RedBlackNode<T>) oldRoot.getRightNode();

        if (newRoot.getLeftNode() != null) {
            linkNodes(oldRoot, newRoot.getLeftNode() ,'R');
        } else {
            linkNodes(oldRoot, null, 'R');
        }

        RedBlackNode<T> parentNode = (RedBlackNode<T>) oldRoot.getParentNode();
        if (parentNode != null) {
            if (oldRoot == parentNode.getLeftNode()) {
                linkNodes(parentNode, newRoot, 'L');
            } else {
                linkNodes(parentNode, newRoot, 'R');
            }
        }

        newRoot.setLeftNode(oldRoot);
        newRoot.setParentNode(parentNode);
        oldRoot.setParentNode(newRoot);

        if (oldRoot.getRightNode() != null) {
            oldRoot.getRightNode().setParentNode(oldRoot);
        }

        if (parentNode == null) {
            root = newRoot;
        }
    }
}
