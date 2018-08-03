package zym.stream.pipline;

/**
 * @Author unyielding
 * @date 2018/8/2 0002 17:08
 * @desc 管道类
 */
public class MonkeyPipeline<IN> {
    private HandlerContext<IN> head;//链表头

    private HandlerContext<IN> tail;//链表尾，如果是一个双向链表，这个成员将被用到。

    public void addFirst(Handler<IN> handler) {
        HandlerContext<IN> ctx = new HandlerContext<>(handler);
        HandlerContext<IN> tmp = head;
        head = ctx;//将 ctx 设置为head
        head.setNext(tmp);//将原来的head 设置为要插入的next
    }

    public MonkeyPipeline() {
        head = tail = new HeadContext<>(new HeadHandler<IN>());
    }

     void request(IN msg) {
        head.doWork(msg);
    }

    final class HeadContext<IN> extends HandlerContext<IN>{
        public HeadContext(Handler<IN> handler) {
            super(handler);
        }
    }
    final class HeadHandler<IN> implements Handler<IN>{

        @Override
        public void chanelRead(HandlerContext<IN> ctx, IN msg) {
            String result = (String) msg + "end";
            System.out.println(result);
        }

    }

}
