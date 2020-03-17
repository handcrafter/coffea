package Test;

import Tree.AVLtree;


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
        * Example :
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
        *
        * Second Example :
        *         Binary Tree                                   AVLtree
        *             5                                             3
        *          3     7                                        2    5
        *        2                                             1          7
        *      1
        */

        AVLtree a = new AVLtree(10, null, 0);
        a.insert(20, null);
        a.insert(30, null);
        a.insert(40, null);
        a.insert(50, null);
        a.insert(25, null);
        a.printBFS();

        System.out.println("\n"+"Second example :");
        AVLtree b = new AVLtree(5, null, 0);
        b.insert(3, null);
        b.insert(2, null);
        b.insert(7, null);
        b.insert(1, null);
        b.printBFS();
    }
}
