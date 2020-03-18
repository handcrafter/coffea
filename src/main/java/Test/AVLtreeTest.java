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

        AVLtree<String> a = new AVLtree(10, "10", 0);
        a.insert(20, "20");
        a.insert(30, "30");
        a.insert(40, "40");
        a.insert(50, "50");
        a.insert(25, "25");
        a.printBFS();

        System.out.println("\n"+"Second example :");
        AVLtree<String> b = new AVLtree(5, "5", 0);
        b.insert(3, "3");
        b.insert(2, "2");
        b.insert(7, "7");
        b.insert(1, "1");
        b.printBFS();
    }
}
