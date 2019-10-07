package zym.collections;

import java.util.ArrayList;

/**
 * ArrayList 坑
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("b");
        list.add("a");
        remove(list);

    }

    public static void remove(ArrayList<String> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            String s = list.get(i);
            if (s.equals("b")) {
                list.remove(s);
            }
        }

    }
}
