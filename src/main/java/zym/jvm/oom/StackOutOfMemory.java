package zym.jvm.oom;

/**
 * 栈 内存 溢出
 *
 * @author 24160
 */
public class StackOutOfMemory {
    public void waitToOOM() {
        while (true) {

        }
    }

    public static void main(String[] args) {

        while (true) {
            new Thread(() -> {
                StackOutOfMemory soom = new StackOutOfMemory();
                soom.waitToOOM();
            }).start();
        }
    }



}
