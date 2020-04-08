package Test;

import Tree.TernaryTree;


public class TernaryTreeTest {
    public static void main(String[] args) {
        /*
        * - A ternary tree is a tree data structure in which each node has at most three child nodes
        * - Minimum height: log3(N)+1
        * - Maximum height: O(N)
        *
        * Example:
        *                3
        *            /   |   \
        *          4      5    10
        *        / |    / |    /|
        *       1  11  8  17  6 18
        *
        *
        * output: (key: 3, value: 3) (key: 4, value: 4) (key: 5, value: 5) (key: 10, value: 10) (key: 1, value: 1) (key: 11, value: 11) (key: 8, value: 8) (key: 17, value: 17) (key: 6, value: 6) (key: 18, value: 18)
        * Is 10 in the tree? : false
        * */

        TernaryTree<String> ts = new TernaryTree(3,"3");
        ts.insert(4, "4");
        ts.insert(5, "5");
        ts.insert(10, "10");
        ts.insert(1, "1");
        ts.insert(8, "8");
        ts.insert(6, "6");
        ts.insert(11, "11");
        ts.insert(17, "17");
        ts.insert(18, "18");
        ts.printBFS();
        System.out.println("\n" + "Is 10 in the tree? : " + ts.contains(10));
    }
}
