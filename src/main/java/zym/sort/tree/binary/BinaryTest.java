package zym.sort.tree.binary;

import org.junit.Test;

/**
 * test it
 * @author liangziqiang
 */
public class BinaryTest {

    @Test
    public void testTreePreviousTraverse() {
        Tree binaryTree = buildTree();

        binaryTree.previousTraverse();
    }

    private Tree buildTree() {
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
        return binaryTree;
    }

    @Test
    public void testTreeMiddleTraverse() {
        Tree binaryTree = buildTree();

        binaryTree.middleTraverse();
    }

    @Test
    public void testTreeAfterTraverse() {
        Tree binaryTree = buildTree();
        binaryTree.afterTraverse();
    }
}
