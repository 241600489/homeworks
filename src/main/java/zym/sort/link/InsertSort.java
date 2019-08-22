package zym.sort.link;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.Objects;

public class InsertSort {
    public static Node sort(Node node) {
        // 插入排序
        //已排好序节点
        Node cur = node;
        Node next = node.getNext();
        cur.setNext(null);
        while (Objects.nonNull(next)) {
            Node temp = cur;
            if (temp.getVal() >= next.getVal()) {
                Node tempNext = next;
                next = next.getNext();
                tempNext.setNext(temp);
                cur = tempNext;
            } else {
                Node l1 = temp;
                while (Objects.nonNull(temp) && next.getVal() > temp.getVal()) {
                    l1 = temp;
                    temp = temp.getNext();
                }

                Node tempNext = next;
                next = next.getNext();
                Node tempNextNext = l1.getNext();
                l1.setNext(tempNext);
                tempNext.setNext(tempNextNext);
            }
        }
        return cur;
    }

    public static Node reverse(Node head) throws InvalidArgumentException {
        if (head == null) {
            throw new InvalidArgumentException(new String[]{"head is null"});
        }
        //前驱节点
        Node pre = null;
        if (head.getNext() == null) {
            return head;
        }
        //当前节点
        Node cur = head;
        //后继节点
        Node after = null;
        while (cur != null) {
            after = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = after;
        }
        return pre;
    }

    public static void main(String[] args) throws InvalidArgumentException {
        Node node = new Node(1);
        Node node1 = new Node(2);
        Node node2 = new Node(3);
        Node node3 = new Node(4);
        node.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);

        Node sort = sort(node);
        System.out.println(sort);

        Node reverse = reverse(sort);
        System.out.println(reverse);
    }
}
