package Test;

import Tree.BinarySearchTree;


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
        *
        *   Another Example :
        *
        *               8
        *             /   \
        *           4       12
        *          / \     /  \
        *        1    7  10    15
        *            /     \
        *           5       11
        *
        *   Second Example
        *   BFS : 8412171015511
        *   Inorder : 1457810111215
        *   Is 15 in the tree? : true
        *   Remove 12 from the tree
        *   14578101115
        */

        BinarySearchTree<String> bsTree = new BinarySearchTree(5, "5");
        // Insert a node
        System.out.println("Left Insertion, expected: 5 2 1");
        bsTree.insert(2, "2");
        bsTree.insert(1, "1");
        bsTree.printBFS();
        System.out.println("Right Insertion, expected: 5 2 7 1");
        bsTree.insert(7, "7");
        bsTree.printBFS();
        System.out.println("Left-Right Insertion, expected: 5 2 7 1 3 4");
        bsTree.insert(3, "3");
        bsTree.insert(4, "4");
        bsTree.printBFS();
        System.out.println("Left-Right Insertion, expected: 5 2 7 1 3 6 9 4");
        bsTree.insert(9, "9");
        bsTree.insert(6, "6");
        bsTree.printBFS();

        // Search for key
        System.out.println("\n" + "Is 7 in the tree? : " + bsTree.contains(7));

        // Remove a node
        System.out.println("Remove 2 from the tree");
        bsTree.delete(2);
        bsTree.printBFS();

        //print tree in order
        System.out.print("\n" + "Inorder : ");
        bsTree.printInorder();


        System.out.println("\n" + "Second Example");
        BinarySearchTree<String> bt = new BinarySearchTree(8, null);
        bt.insert(4, null);
        bt.insert(12, null);
        bt.insert(1, null);
        bt.insert(7, null);
        bt.insert(10, null);
        bt.insert(15, null);
        bt.insert(5, null);
        bt.insert(11, null);
        System.out.print("BFS : ");
        bt.printBFS();
        System.out.print("\n" + "Inorder : ");
        bt.printInorder();
        System.out.print("\n" + "Is 15 in the tree? : " + bt.contains(15));
        System.out.println("\n" + "Remove 12 from the tree");
        bt.delete(12);
        System.out.print("Inorder : ");
        bt.printInorder();
    }
}
