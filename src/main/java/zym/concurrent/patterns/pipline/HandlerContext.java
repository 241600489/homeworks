package zym.concurrent.patterns.pipline;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author unyielding
 * @date 2018/8/2 0002 16:46
 * @desc HandlerContext的作用比较大，首先它是链表的一部分，
 *      因此需要有指向下一个context的指针；然后它负责调用handler，
 *      而我们要实现一个并发的处理程序，那么HandlerContext就需要维护一个线程池来供handler处理。
 */
public class HandlerContext<IN> {
    private ExecutorService executor = Executors.newCachedThreadPool();//线程池

    private Handler handler;//具体处理对象

    private HandlerContext<IN> next;//下一个context的引用

    public HandlerContext(Handler handler) {
        this.handler = handler;
    }

    public void setNext(HandlerContext<IN> next) {
        this.next = next;
    }

    public void doWork(IN msg) {
        if (next == null) {
            return;
        }else {
            executor.submit(()->{
                handler.chanelRead(next, msg);
            });
        }
    }

    public void write(IN msg) {
        doWork(msg);
    }
}
