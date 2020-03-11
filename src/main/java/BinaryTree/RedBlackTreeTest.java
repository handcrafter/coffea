package BinaryTree;


public class RedBlackTreeTest {
    public static void main(String[] args) {
        /*
        * - RedBlackTree is a BinarySearchTree having following properties:
        *   - Every node in tree is either Red or Black
        *   - Root node of tree is Black
        *   - All leaves(NIL) are Black
        *   - If a node is Red, then its children are both Black
        *   - Every path from a given node to any of its descendant NIL nodes goes through the same number of black nodes
        *   height: O(logN)
        *   insertion: O(logN)
        *   deletion: O(logN)
        *   search: O(logN)
        *
        *   Example :
        *       Binary Tree                               RedBlackTree
        *
        *           10                                         20
        *             \                                      (Black)
        *              20                               10             40
        *              / \                            (Black)         (Red)
        *             25  30                                        30      50
        *                  \                                     (Black)  (Black)
        *                   40                                  25
        *                    \                                (Red)
        *                     50
        *
        *   Second Example:
        *       Binary Tree                                RedBlackTree
        *           5                                            3
        *          / \                                        (Black)
        *         3   7                                   2             5
        *        /                                      (Black)       (Black)
        *       2                                     1                      7
        *      /                                    (Red)                   (Red)
        *     1
        */
        RedBlackTree rb = new RedBlackTree();
        rb.insert(10, null);
        rb.insert(20, null);
        rb.insert(30, null);
        rb.insert(40, null);
        rb.insert(50, null);
        rb.insert(25, null);
        rb.printBFS();

        System.out.println("\n" + "Second Example");
        RedBlackTree rbt = new RedBlackTree();
        rbt.insert(5, null);
        rbt.insert(3, null);
        rbt.insert(2, null);
        rbt.insert(7, null);
        rbt.insert(1, null);
        rbt.printBFS();
    }
}
