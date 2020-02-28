package BinaryTree;

public class BinaryTreeTest {

    public static void main(String[] args) {

        /*
         *  Binary Tree
         *  Tree data structure with at most 2 children per a parent node
         *  Traverse through the tree using getLeft/RightNode();
         *  Insert child node using setLeft/RightNode()
         *
         *  Below example tree has depth of 3
         *         0
         *       /   \
         *      1     2
         *     / \   / \
         *    3   4 5   6
         *
         *    Running the program will traverse the tree and print values in BFS and DFS order
         */


        BinaryTree bt = new BinaryTree(0);
        bt.root.setLeftNode(new BinaryNode(1));
        bt.root.setRightNode(new BinaryNode(2));
        bt.root.getLeftNode().setLeftNode(new BinaryNode(3));
        bt.root.getLeftNode().setRightNode(new BinaryNode(4));
        bt.root.getRightNode().setLeftNode(new BinaryNode(5));
        bt.root.getRightNode().setRightNode(new BinaryNode(6));

        System.out.print("BFS : ");
        bt.printBFS();
        System.out.print("DFS : ");
        bt.printDFS();
    }

}
