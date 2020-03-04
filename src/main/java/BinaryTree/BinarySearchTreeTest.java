package BinaryTree;

/**
 * Created by jaewonlee on 2020-02-29.
 */
public class BinarySearchTreeTest {
    public static void main(String[] args) {

        /*
        *   BinarySearchTree(BST)
        *
        *   - BST is a binary trees that keeps the keys in sorted order.
        *   - contains method returns true if a node in tree contains the target key value
        *   - Delete method removes target node from the tree
        *   - Insert method inserts a new node into the tree depending on the new node's key
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
        *   Is 7 in the tree? : true
        *   Remove 2 from the tree
        *   5371469
        *   Inorder :
        *   1345679
        */

        BinarySearchTree bsTree = new BinarySearchTree(5);
        // Insert a node
        bsTree.insert(2);
        bsTree.insert(1);
        bsTree.insert(7);
        bsTree.insert(3);
        bsTree.insert(4);
        bsTree.insert(9);
        bsTree.insert(6);
        bsTree.printBFS();

        // Search for key
        System.out.println("\n" + "Is 7 in the tree? : " + bsTree.contains(7));

        // Remove a node
        System.out.println("Remove 2 from the tree");
        bsTree.delete(2);
        bsTree.printBFS();

        //print tree in order
        System.out.println("\n" + "Inorder : ");
        bsTree.printInorder();
    }

}
