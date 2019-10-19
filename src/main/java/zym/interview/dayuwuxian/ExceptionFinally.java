package zym.interview.dayuwuxian;


import org.junit.jupiter.api.Test;

public class ExceptionFinally {
    public static void main(String[] args) {
        System.out.println("返回值为" + str(null, "B"));
        System.out.println("返回值为" + str(null, 6));
    }

    private static String str(String a, String b) {
        try {
            return a + b;
        } catch (Exception e) {
            System.out.println("exception");
        }finally {
            System.out.println("finally");
        }
        return "";
    }
    private static Integer str(Integer a, Integer b) {
        try {
            return a + b;
        } catch (Exception e) {
            System.out.println("int throws exception");
        }finally {
            System.out.println("int finally");
        }
        return 0;
    }

    @Test
    public void testJiaJia() {
        int i = 3;
        i *= ++i;
        System.out.println(i);

    }
}
