package Tree;


import Node.BinaryNode;
import Node.RedBlackNode;

public class RedBlackTree<T> extends BinarySearchTree<T> {

    public RedBlackTree() { current = root = null;}

    public RedBlackTree(int key, T value) {
        current = new RedBlackNode<T>(key, value, true);
        root = (RedBlackNode<T>) current;
    }

    public RedBlackNode<T> getGrandParent(RedBlackNode<T> node) {
        if (node == null || node.getParentNode() == null || node.getParentNode().getParentNode() == null) {
            return null;
        } else {
            return (RedBlackNode<T>) node.getParentNode().getParentNode();
        }
    }

    public RedBlackNode<T> getUncle(RedBlackNode<T> node) {
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

    public RedBlackNode<T> getSibling(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = null;
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        assert (node == null || parent == null) : "node and parent must not be null";

        if (node.getKey() < parent.getKey()) {
            sibling = (RedBlackNode<T>) parent.getRightNode();
        } else if (node.getKey() > parent.getKey()){
            sibling = (RedBlackNode<T>) parent.getLeftNode();
        }
        else {
            if (parent.getLeftNode() != null) {
                sibling = (RedBlackNode<T>) parent.getLeftNode();
            } else if (parent.getRightNode() != null) {
                sibling = (RedBlackNode<T>) parent.getRightNode();
            }
        }
        return sibling;
    }

    public void insert(int insert_key, T value) {
        if (root == null) {
            RedBlackNode<T> node = new RedBlackNode<T>(insert_key, value, true);
            current = node;
            root = (RedBlackNode<T>) current;
            return;
        }

        RedBlackNode<T> insertedNode = null;
        current = root;

        while (true) {
            if (current.getKey() == insert_key) {
                current.setValue(value);
                break;
            }
            if (current.getKey() < insert_key) {
                if (current.getRightNode() == null) {
                    RedBlackNode<T> node = new RedBlackNode<T>(insert_key, value, true);
                    linkNodes(current, node, 'R');
                    insertedNode = node;
                    break;
                } else {
                    moveToRightNode();
                }
            } else if (current.getKey() > insert_key) {
                if (current.getLeftNode() == null) {
                    RedBlackNode<T> node = new RedBlackNode<T>(insert_key, value, true);
                    linkNodes(current, node, 'L');
                    insertedNode = node;
                    break;
                } else {
                    moveToLeftNode();
                }
            }
        }
        adjustAfterInsert(insertedNode);
        current = root;
    }

    public void adjustAfterInsert(RedBlackNode<T> node) {
        assert node == null : "Input node must not be null";

        // colour the node red
        node.setColour(false);

        RedBlackNode<T> gp = getGrandParent(node);
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        RedBlackNode<T> uncle = getUncle(node);

        if (parent != null && !parent.getColour()) {

            if (uncle != null && !uncle.getColour()) {
                parent.setColour(true);
                uncle.setColour(true);
                gp.setColour(false);
                adjustAfterInsert(gp);
            }
            else if (parent.getKey() < gp.getKey()) {
                if (node.getKey() > parent.getKey()) {
                    leftRotation(parent);
                }
                parent.setColour(true);
                gp.setColour(false);
                rightRotation(gp);
            }
            else if (parent.getKey() >  gp.getKey()) {
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

        if (deleteNode == null) return;

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
                    if (nodeToUpdate.getColour()) {
                        nodeToUpdate.setDoubleBlack(true);
                    }
                }
            } else {
                swapWithPredecessor(deleteNode, predecessor);
                nodeToUpdate = (RedBlackNode<T>) predecessor.getParentNode();
                if (predecessor.getColour() && predecessor.getRightNode() == null && predecessor.getLeftNode() == null) {
                    handleDoubleBlack(predecessor);
                }
            }
        } else {
            swapWithSuccessor(deleteNode, successor);
            nodeToUpdate = (RedBlackNode<T>) successor.getParentNode();
            if (successor.getColour() && successor.getLeftNode() == null && successor.getRightNode() == null) {
                handleDoubleBlack(successor);
            }
        }

        if (nodeToUpdate != null) {
            adjustAfterDelete(nodeToUpdate);
        }
    }

    public void adjustAfterDelete(RedBlackNode<T> node) {
        if (node.isDoubleBlack()) {
            handleDoubleBlack(node);
        } else if (node.getColour()) {
            deleteCase1(node);
        }
    }

    public void handleDoubleBlack(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> siblingLeft = (RedBlackNode<T>) sibling.getLeftNode();
        RedBlackNode<T> siblingRight = (RedBlackNode<T>) sibling.getRightNode();
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();

        if (sibling.getColour()) {
            if (node.getKey() > parent.getKey()) {
                if (siblingLeft != null && !siblingLeft.getColour()) {
                    rightRotation(parent);
                } else if (siblingRight != null) {
                    leftRotation(sibling);
                    rightRotation(parent);
                    parent.setColour(true);
                }
            } else if (node.getKey() < parent.getKey()) {
                if (siblingRight != null && !siblingRight.getColour()) {
                    leftRotation(parent);
                } else if (siblingLeft != null) {
                    rightRotation(sibling);
                    leftRotation(parent);
                    parent.setColour(true);
                }
            } else {
                if (parent.getLeftNode() == null) {
                    leftRotation(parent);
                } else if (parent.getRightNode() == null) {
                    rightRotation(parent);
                }
            }
        } else if (!sibling.getColour()) {
            if (node.getKey() < parent.getKey()) {
                leftRotation(parent);
            } else {
                rightRotation(parent);
            }
            parent.setColour(false);
            sibling.setColour(true);
            handleDoubleBlack(node);
        }
    }

    public void deleteCase1(RedBlackNode<T> node) {
        if (node.getParentNode() != null) {
            deleteCase2(node);
        }
    }

    public void deleteCase2(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        if (!sibling.getColour()) {
            parent.setColour(false);
            sibling.setColour(true);
            if (node.getKey() < parent.getKey()) {
                leftRotation(parent);
            } else {
                rightRotation(parent);
            }
            node = parent;
        }
        deleteCase3(node);
    }

    public void deleteCase3(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> siblingLeft = (RedBlackNode<T>) sibling.getLeftNode();
        RedBlackNode<T> siblingRight = (RedBlackNode<T>) sibling.getRightNode();
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();
        if (parent.getColour() && sibling.getColour()) {
            if (siblingLeft != null && siblingRight != null && siblingLeft.getColour() && siblingRight.getColour()) {
                sibling.setColour(false);
                deleteCase1(parent);
            }
        } else if (!parent.getColour() && sibling.getColour()) {
            if (siblingLeft != null && siblingRight != null && siblingLeft.getColour() && siblingRight.getColour()) {
                if (!node.getColour()) {
                    sibling.setColour(false);
                    parent.setColour(true);
                }
            }
        } else {
            deleteCase4(node);
        }
    }

    public void deleteCase4(RedBlackNode<T> node) {
        RedBlackNode<T> sibling = getSibling(node);
        RedBlackNode<T> siblingLeft = (RedBlackNode<T>) sibling.getLeftNode();
        RedBlackNode<T> siblingRight = (RedBlackNode<T>) sibling.getRightNode();
        RedBlackNode<T> parent = (RedBlackNode<T>) node.getParentNode();

        if (sibling.getColour()) {
            if (node.getKey() < parent.getKey() && siblingRight.getColour() && !siblingLeft.getColour()) {
                sibling.setColour(false);
                siblingLeft.setColour(true);
                rightRotation(sibling);
            }  else if (node.getKey() > parent.getKey() && siblingLeft.getColour() && !siblingRight.getColour()) {
                sibling.setColour(false);
                siblingRight.setColour(true);
                leftRotation(sibling);
            }
        } else {
            sibling.setColour(parent.getColour());
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
