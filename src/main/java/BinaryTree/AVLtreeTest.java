package BinaryTree;

/**
 * Created by jaewonlee on 2020-03-04.
 */
public class AVLtreeTest {
    public static void main(String[] args) {
        /*
        * - AVL tree is a self-balancing Binary Search Tree
        * - Difference between heights of left and right subtrees cannot be more than one
        * - Height: O(logN)
        * - Searching : O(logN)
        * - Deletion : O(logN)
        * - Insertion : O(logN)
        *
        * Example:
        *       Binary Tree                                 AVLtree
        *
        *           10                                         30
        *             \                                       /  \
        *              20                                    20   40
        *              / \                                  /  \    \
        *             25  30                              10    25   50
        *                  \
        *                   40
        *                    \
        *                     50
        */

        AVLtree a = new AVLtree(10, null);
        a.insert(20, null);
        a.insert(30, null);
        a.insert(40, null);
        a.insert(50, null);
        a.insert(25, null);
        a.printBFS();

        a.delete(40);
        a.delete(50);
        System.out.println(a.contains(20));
        a.printBFS();
    }
}
