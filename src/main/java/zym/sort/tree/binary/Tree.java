package zym.sort.tree.binary;


import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

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

    /**
     * 中序遍历
     */
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

    /**
     * 后序遍历
     */
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

    /**
     * 广度遍历
     */
    public void broadCastTraverse() {
        if (Objects.nonNull(root)) {
            //定义一个存放节点的队列
            LinkedList<Node> nodes = new LinkedList<>();
            nodes.addLast(root);
            while (!nodes.isEmpty()) {
                Node node = nodes.removeFirst();
                System.out.println(node.getData());
                if (Objects.nonNull(node.getLeft())) {
                    nodes.addLast(node.getLeft());
                }
                if (Objects.nonNull(node.getRight())) {
                    nodes.addLast(node.getRight());
                }
            }
        }
    }

    /**
     * 深度遍历 即先序遍历 若不采用递归方式则序借助栈的后进先出
     * 先将根节点事先放到栈中然后执行下面的步骤:
     * 1.从栈顶弹出节点
     * 2.打印节点的値
     * 3.判断该节点的右子节点是否为空，若不为空则将右子节点放入栈中
     * 4.判断该节点的左子节点是否为空，若不为空则将左子节点放入栈中
     * 重复上述步骤，直到栈为空
     */
    public void deepTraverse() {
        if (Objects.nonNull(root)) {
            Stack<Node> nodes = new Stack<>();
            nodes.push(root);
            while (!nodes.isEmpty()) {
                Node node = nodes.pop();
                System.out.println(node.getData());
                if (Objects.nonNull(node.getRight())) {
                    nodes.push(node.getRight());
                }
                if (Objects.nonNull(node.getLeft())) {
                    nodes.push(node.getLeft());
                }
            }
        }
    }

    /**
     * Non-recursive traversal
     *
     * 中序遍历 非递归遍历 借用栈来实现
     * 思路：
     * 1.首先将根节点 入栈
     * 2.依次将左节点入栈直到左节点为空
     * 3.弹出栈顶打印判断是否有右节点，若有则入栈
     */
    public void nonRecursiveTraverse() {
        if (Objects.nonNull(root)) {
            Node node = root;
            Stack<Node> nodes = new Stack<>();
            while (Objects.nonNull(node) || !nodes.isEmpty()) {
                if (Objects.nonNull(node)) {
                    //将根节点入栈
                    nodes.push(node);
                    node = node.getLeft();
                } else {
                    Node someLeft = nodes.pop();
                    System.out.println(someLeft.getData());
                    if (Objects.nonNull(someLeft.getRight())) {
                        node = someLeft.getRight();
                    }
                }

            }
        }
    }

}
