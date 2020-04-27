package Node;

import Base.Node;

import java.util.List;
import java.util.*;


public class RadixNode<T> extends Node{
    protected String value;
    private RadixNode<T> parent;
    private char prefix;
    private List<RadixNode<T>> children = new ArrayList<RadixNode<T>>();

    public RadixNode() { value = null;}

    public RadixNode(String value) {
        this.value = value;
        prefix = value.charAt(0);
    }

    public void setValue(String value) {
        this.value = value;
        prefix = value.charAt(0);
    }

    public void setChild(RadixNode child) { children.add(child); }

    public void setParent(RadixNode parent) { this.parent = parent; }

    public String getValue() { return value; }

    public char getPrefix() { return prefix; }

    public int numChildren() { return children.size(); }

    public RadixNode<T> getParent() { return parent; }

    public RadixNode<T> getChild(char prefix) {
        int index = -1;
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getPrefix() == prefix) {
                index = i;
                break;
            }
        }
        if (index > -1) {
            return children.get(index);
        } else {
            return null;
        }
    }

    public RadixNode<T> getChild(int index) { return children.get(index); }

    public void replaceChild(RadixNode<T> newChild, RadixNode<T> oldChild) {
        children.remove(oldChild);
        children.add(newChild);
    }

    public void deleteChild(RadixNode<T> delete) { children.remove(delete); }

    @Override
    public void print() { System.out.print("(prefix: " + prefix + ", value: " + value + ") "); }
}
