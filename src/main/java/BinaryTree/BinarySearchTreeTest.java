package BinaryTree;

/**
 * Created by jaewonlee on 2020-02-29.
 */
public class BinarySearchTreeTest {
    public static void main(String[] args) {

        /*
        *   BinarySearchTree(BST)
        *
        *   - BST is a binary trees that keeps their keys in sorted order.
        *   - SearchKey method returns true if a node in tree contains the target key value
        *   - DeleteNode removes target node from the tree
        *   - InsertNode inserts a new node into the tree depending on the new node's key
        *   - (left : key < root, right : key > root)
        *
        *   Minimum height : O(logN)
        *   Maximum height : O(N)
        *   Minimum search time : O(log2(N))
        *   Maximum Search time : O(N)
        *   Minimum Insertion time : O(log2(N))
        *   Maximum Insertion time : O(N)
        *
        *   Below example tree has a height of 4
        *                   5
        *                 /   \
        *               2       7
        *              / \     / \
        *             1   3   6   9
        *                  \
        *                   4
        *
        *   program output:
        *   52713694
        *   Is 4 in the tree? : true
        *   Remove 9 from the tree
        *   5271364
        */

        BinarySearchTree bsTree = new BinarySearchTree(5);
        // Insert a node
        bsTree.insertNode(new BinaryNode(2));
        bsTree.insertNode(new BinaryNode(1));
        bsTree.insertNode(new BinaryNode(7));
        bsTree.insertNode(new BinaryNode(3));
        bsTree.insertNode(new BinaryNode(4));
        bsTree.insertNode(new BinaryNode(9));
        bsTree.insertNode(new BinaryNode(6));
        bsTree.printBFS();

        // Search for key
        System.out.println("\n" + "Is 4 in the tree? : " + bsTree.searchKey(4));

        // Remove a node
        System.out.println("Remove 9 from the tree");
        bsTree.deleteNode(new BinaryNode(9));
        bsTree.printBFS();
    }

}
