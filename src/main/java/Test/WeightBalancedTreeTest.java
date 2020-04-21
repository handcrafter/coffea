package Test;

import Tree.WeightBalancedTree;


public class WeightBalancedTreeTest {
    public static void main(String[] args) {
        /*
        * - Weight-Balanced Binary Tree is a binary tree balanced based on a weight of each node
        * - A node with the most weight appears at the root
        * - Provides efficient searching when target node has higher weight
        * Average search time : O(logN)
        * Best-case(Highest weight) : O(1)
        * Worst-case(Lowest weight) : O(n)
        *
        * Example:
        *           Binary Search Tree                          Weight-Balanced Tree
        *               24                                              17(100)
        *              /  \                                           /         \
        *            14    63                                     1(50)         63(94)
        *           / \      \                                      \           /   \
        *          1   17     70                                   14(6)    24(28)  70(91)
        *           \                                               /
        *            7                                             7(5)
        * */

        WeightBalancedTree<String> wb = new WeightBalancedTree<>(24, "24", 28);
        wb.insert(63, "63", 94);
        wb.insert(14, "14", 6);
        wb.insert(1, "1", 50);
        wb.insert(70, "70", 91);
        wb.insert(17, "17", 100);
        wb.insert(7, "7", 5);
        //wb.delete(63);
        //wb.delete(17);
        wb.delete(1);
        wb.printBFS();
    }
}
