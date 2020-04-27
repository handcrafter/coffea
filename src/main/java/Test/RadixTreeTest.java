package Test;

import Tree.RadixTree;


public class RadixTreeTest {
    public static void main(String[] args) {
        /*
        * - A radix tree is a space-optimized prefix tree where each node is allows to store values as key-value(usually string).
        * - Search, Insertion, Deletion takes O(k) where k is the maximum length of all strings in a set.
        *
        *  Example:
        *
        *                root
        *
        *         /        |      \
        *     water       john    long
        *     /   \        |
        *  slide  pool    son
        *                  |
        *                  s
        *
        * */
        RadixTree<String> rt = new RadixTree<String>();
        rt.insert("water");
        rt.insert("waterslide");
        rt.insert("waterpool");
        rt.insert("john");
        rt.insert("johnson");
        rt.insert("long");
        rt.insert("johnsons");
        rt.printDFS();

        System.out.println("\n" + "Is water in a tree? : " + rt.search("water"));
        System.out.println("Is sand in a tree? : " + rt.search("sand"));
        System.out.print("Delete waterslide : ");
        rt.delete("waterslide");
        rt.printBFS();
        System.out.print("\n" + "Delete johnson : ");
        rt.delete("johnson");
        rt.printBFS();

    }
}
