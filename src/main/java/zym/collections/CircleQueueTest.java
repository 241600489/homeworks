package zym.collections;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircleQueueTest {

    @Test
    public void testPut() {
        CircleQueue<String> circleQueue = new CircleQueue<>(3);

        circleQueue.put("nihao");
        circleQueue.put("wohao");
        assertEquals("nihao", circleQueue.take());
        assertEquals("wohao", circleQueue.take());

        circleQueue.put("nihao");
        circleQueue.put("wohao");

        try {
            circleQueue.put("zengyi");
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertNotNull(e);

        }

        circleQueue.take();
        circleQueue.take();
        try {
            circleQueue.take();
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testSize() {
        CircleQueue<String> circleQueue = new CircleQueue<>(3);
        circleQueue.put("nihoa");
        assertEquals(1, circleQueue.size());
        circleQueue.put("wohao");
        assertEquals(2, circleQueue.size());
        circleQueue.take();
        assertEquals(1, circleQueue.size());
        circleQueue.put("nihao");
        assertEquals(2, circleQueue.size());

        circleQueue.take();
        circleQueue.put("wohao");
        assertEquals(2, circleQueue.size());
    }
}
