package Test;

import Tree.BinaryTree;


public class BinaryTreeTest {

    public static void main(String[] args) {

        /*
         *  Binary Tree
         *  - Tree data structure with at most 2 children per a parent node
         *  - Traverse through the tree using moveToLeft/RightNode();
         *  - Insert child node using setLeft/RightNode()
         *  - Since the tree is not sorted in any order, the searching will take O(N) times
         *  - Minimum height: log2(N)+1
         *  - Maximum height: O(N)
         *
         *  Below example tree has depth of 3
         *         1
         *       /   \
         *      2     3
         *     / \   / \
         *    4   5 6   7
         *
         *   Running the program will traverse the tree and print values in BFS and DFS order
         *   BFS : 0123456  DFS : 0134256
         */

        BinaryTree<String> bt = new BinaryTree(1, null);
        bt.setLeftChild(2, null);
        bt.setRightChild(3, null);
        bt.moveToLeftNode();
        bt.setLeftChild(4, null);
        bt.setRightChild(5, null);
        bt.moveToParentNode();
        bt.moveToRightNode();
        bt.setLeftChild(6, null);
        bt.setRightChild(7, null);
        bt.moveToParentNode();

        System.out.print("BFS : ");
        bt.printBFS();
        System.out.print("  DFS : ");
        bt.printDFS();
    }

}
