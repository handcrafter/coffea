package Tree;

import Base.Tree;
import Node.RadixNode;

import java.util.LinkedList;
import java.util.Queue;


public class RadixTree<T> extends Tree {
    RadixNode<T> current;
    RadixNode<T> root;

    public RadixTree() { root = current = null; }

    public boolean search(String str) {
        if (getNode(str) == null) {
            return false;
        } else {
            return true;
        }
    }

    private RadixNode<T> getNode(String str) {
        assert (root != null) : "Tree is empty";

        char targetPrefix = str.charAt(0);
        RadixNode<T> result = null;
        if (current.getValue() == null) { // when root is not null but its value is null
            if (current.getChild(targetPrefix) != null) {
                current = current.getChild(targetPrefix);
                result = getNode(str);
            } else {
                result = null;
            }
        } else {
            if (str.indexOf(current.getValue()) == 0) { // current is a substring of str
                if (str.length() == current.getValue().length()) {
                    result = current;
                } else {
                    str = str.substring(current.getValue().length());
                    if (current.getChild(str.charAt(0)) != null) {
                        current = current.getChild(str.charAt(0));
                        result = getNode(str);
                    } else {
                        result = null;
                    }
                }
            } else {
                result = null;
            }
        }
        return result;
    }

    public void insert(String str) {
        if (root == null) {
            RadixNode<T> node = new RadixNode<T>(str);
            root = current = node;
            return;
        }

        if (current.getPrefix() == str.charAt(0)) { // prefix matches
            // current is a substring of inserting string
            if (str.indexOf(current.getValue()) == 0) {
                str = str.substring(0+current.getValue().length());
                if (current.getChild(str.charAt(0)) != null) {
                    current = current.getChild(str.charAt(0));
                    insert(str);
                } else {
                    RadixNode<T> child = new RadixNode<T>(str);
                    current.setChild(child);
                    child.setParent(current);
                }
            } else { // prefix match but current is not a substring of inserting string
                // new node with common string and make them children of new node
                String common = "";

                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == current.getValue().charAt(i)) {
                        common += + str.charAt(i);
                    }
                }

                RadixNode<T> node = new RadixNode<T>(common);
                RadixNode<T> childNode = new RadixNode<T>(str.substring(common.length()));
                current.setValue(current.getValue().substring(common.length()));
                node.setChild(current);
                node.setChild(childNode);
                node.setParent(current.getParent());
                current.getParent().replaceChild(node, current);
            }
        } else { // prefix does not match
            if (current.getValue() == null) { // current is not null but has null value
                if (current.getChild(str.charAt(0)) != null) {
                    current = current.getChild(str.charAt(0));
                    insert(str);
                } else {
                    RadixNode<T> node = new RadixNode<T>(str);
                    current.setChild(node);
                    node.setParent(current);
                }
            } else {
                RadixNode<T> node = new RadixNode<T>(str);
                if (current == root) {
                    RadixNode<T> emptyRoot = new RadixNode<T>();
                    current.setParent(emptyRoot);
                    node.setParent(emptyRoot);
                    emptyRoot.setChild(current);
                    emptyRoot.setChild(node);
                    root = emptyRoot;
                } else {
                    current.getParent().setChild(node);
                }
            }
        }
        current = root;
    }

    public void delete(String str) {
        assert (root != null) : "Empty tree";

        RadixNode<T> deleteNode = getNode(str);
        if (deleteNode != null) {
            RadixNode<T> parent = deleteNode.getParent();
            if (deleteNode.numChildren() == 0) { // deleting leaf node
                if (parent != null) {
                    parent.deleteChild(deleteNode);
                } else {
                    root = current = null;
                }
            }
            current = root;
        }
    }

    @Override
    public void printBFS() {
        assert root != null : "Tree must not be empty";
        Queue<RadixNode<T>> queue = new LinkedList<RadixNode<T>>() ;
        queue.add(root);
        while (!queue.isEmpty()) {
            RadixNode<T> node = queue.remove();
            node.print();
            for (int i = 0; i < node.numChildren(); i++) {
                if (node.getChild(i) != null) {
                    queue.add(node.getChild(i));
                }
            }
        }
    }

    @Override
    public void printDFS() {
        assert current != null : "current must not be null";
        current.print();

        for(int i = 0; i < current.numChildren(); i++) {
            if (current.getChild(i) != null) {
                current = current.getChild(i);
                printDFS();
                current = current.getParent();
            }
        }
    }
}
