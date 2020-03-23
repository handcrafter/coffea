package Test;

import Tree.AVLTree;


public class AVLTreeTest {
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
        *           10                                          20
        *          /   \                                      /    \
        *         8    20                                    8      35
        *        /       \                                 /   \    / \
        *       5         30                              4    10  30 40
        *      / \          \                            / \
        *     2  4          40                          2   5
        *                  /
        *                 35
        *
        *
        */

        AVLTree<String> a = new AVLTree(10, "10", 0);
        // right-right insert
        a.insert(20, "20");
        a.insert(30, "30");
        a.insert(40, "40");
        System.out.println("Right-right insert case expected output : 20 10 30 40");
        a.printBFS();
        // right-left insert
        a.insert(35, "35");
        System.out.println("\n" + "Right-left insert case expected output : 20 10 35 30 40");
        a.printBFS();
        // left-left insert
        a.insert(8, "8");
        a.insert(5, "5");
        System.out.println("\n" + "left-left case insert expected output : 20 8 35 5 10 30 40");
        a.printBFS();
        // left-right insert
        a.insert(2, "2");
        a.insert(4, "4");
        System.out.println("\n" + "left-right case insert expected output : 20 8 35 4 10 30 40 2 5");
        a.printBFS();


        System.out.println("\n" + "Another example");
        AVLTree<String> b = new AVLTree<>(7,"7",0);
        b.insert(4,"4");
        b.insert(9,"9");
        b.insert(6,"6");
        b.insert(2,"2");
        b.insert(8,"8");
        b.insert(10,"10");
        b.insert(1,"1");
        b.insert(11,"11");
        // left-left delete
        System.out.println("\n" + "left-left case delete expected output : 7 2 9 1 4 8 10 11");
        b.delete(6);
        b.printBFS();
        // right-right delete
        System.out.println("\n" + "right-right delete expected output : 7 2 10 1 4 8 9 11");
        b.delete(8);
        b.printBFS();

        System.out.println("Another example");
        AVLTree<String> c = new AVLTree<>(3,"1",0);
        c.insert(1, "1");
        c.insert(7, "7");
        c.insert(2, "2");
        c.insert(6, "6");
        c.printBFS();
        System.out.println("\n" + "left-right delete expected output : 2 1 3");
        //c.delete(6);
        //c.delete(7);
        c.printBFS();
        System.out.println("\n" + "right-left delete expected output : 6 3 7");
        c.delete(1);
        c.delete(2);
        c.printBFS();
    }
}
