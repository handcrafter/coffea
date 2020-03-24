package Test;

import Tree.CartesianTree;

public class CartesianTreeTest {
    public static void main(String[] args) {
        /*
        *
        * - Cartesian Tree is a binary tree derived from sequence of numbers
        * - Inorder traversing of the tree returns the original sequence
        * - building Cartesian tree: O(NlogN)
        *
        * MaxHeap Example:                                              MinHeap Example:
        * sequence: 3, 4, 5, 2, 8, 11, 7                        sequence: 5, 4, 3, 9, 5, 6, 1, 2
        *              11                                                       1
        *             /   \                                                   /   \
        *            8     7                                                 3     2
        *           /                                                      /   \
        *          5                                                      4      5
        *         / \                                                    /      / \
        *        4   2                                                  5      9   6
        *       /
        *      3
        *
        * */

        System.out.println("MaxHeap Example");
        int[] a = {3,4,5,2,8,11,7};
        CartesianTree<String> ct = new CartesianTree(a, a.length );
        ct.heapTree(true);
        ct.printInOrder();

        System.out.println("\n"+"MinHeap Example");
        int[] b = {5,4,3,9,5,6,1,2};
        CartesianTree<String> cts = new CartesianTree(b, b.length);
        cts.heapTree(false);
        cts.printInOrder();
    }
}