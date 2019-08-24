package zym.sort.tree.binary;

import org.junit.Test;

/**
 * test it
 * @author liangziqiang
 */
public class BinaryTest {

    @Test
    public void testTreePreviousTraverse() {
        Node root = new Node(1);
        Tree binaryTree = new Tree(root);
        Node four = new Node(4);
        root.setLeft(four);
        Node three = new Node(3);
        root.setRight(three);
        Node two = new Node(2);
        four.setLeft(two);
        Node five = new Node(5);
        three.setRight(five);

        binaryTree.previousTraverse();
    }
}
