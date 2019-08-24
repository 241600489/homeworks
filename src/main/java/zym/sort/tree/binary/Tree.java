package zym.sort.tree.binary;

import java.util.Objects;

/**
 * 树即二叉树
 *
 * @author 梁自强
 */
public class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * 前序遍历
     */
    public void previousTraverse() {
        if (Objects.nonNull(root)) {
            doPreviousTraverse(root);
        }
    }

    private void doPreviousTraverse(Node root) {
        //先遍历根节点
        System.out.println(root.getData());
        if (Objects.nonNull(root.getLeft())) {
            doPreviousTraverse(root.getLeft());
        }
        if (Objects.nonNull(root.getRight())) {
            doPreviousTraverse(root.getRight());
        }
    }

    public void middleTraverse() {
        if (Objects.nonNull(root)) {
            doMiddleTraverse(root);
        }
    }

    private void doMiddleTraverse(Node root) {
        //先遍历左节点
        if (Objects.nonNull(root.getLeft())) {
            doMiddleTraverse(root.getLeft());
        }
        //遍历根节点
        System.out.println(root.getData());
        //遍历右节点
        if (Objects.nonNull(root.getRight())) {
            doMiddleTraverse(root.getRight());
        }
    }

    public void afterTraverse() {
        if (Objects.nonNull(root)) {
            doAfterTraverse(root);
        }
    }

    private void doAfterTraverse(Node root) {
        //先遍历左节点
        if (Objects.nonNull(root.getLeft())) {
            doAfterTraverse(root.getLeft());
        }
        // 遍历右节点
        if (Objects.nonNull(root.getRight())) {
            doAfterTraverse(root.getRight());
        }
        //遍历根节点
        System.out.println(root.getData());
    }

}
