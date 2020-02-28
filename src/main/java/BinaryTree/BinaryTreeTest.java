package BinaryTree;

public class BinaryTreeTest {

    public static void main(String[] args) {

        /*
         *  Binary Tree
         *  Tree data structure with at most 2 children per a parent node
         *  Traverse through the tree using getLeft/RightNode();
         *  Insert child node using setLeft/RightNode()
         *  Searching requires to travel all nodes in the tree since it is not sorted in any order, and because so, the running time would be O(n)
         *  As long as the a node is not null, there are no limit on height of the tree
         *
         *  Below example tree has depth of 3
         *         0
         *       /   \
         *      1     2
         *     / \   / \
         *    3   4 5   6
         *
         *   Running the program will traverse the tree and print values in BFS and DFS order
         *   BFS : 0123456  DFS : 0134256
         */


        BinaryTree bt = new BinaryTree(0);
        bt.setLeftChild(1);
        bt.setRightChild(2);
        bt.moveToLeftNode();
        bt.setLeftChild(3);
        bt.setRightChild(4);
        bt.moveToParentNode();
        bt.moveToRightNode();
        bt.setLeftChild(5);
        bt.setRightChild(6);
        bt.moveToParentNode();

        System.out.print("BFS : ");
        bt.printBFS();
        System.out.print("  DFS : ");
        bt.printDFS();
    }

}
