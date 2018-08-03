package zym.stream.pipline;

/**
 * @Author unyielding
 * @date 2018/8/2 0002 16:44
 * @desc 处理器
 */
public interface Handler<IN> {
    /**
     * 字符串的处理,处理完之后传给下一个handler
     * @param ctx 下一个handler 的context
     * @param msg 上一个handler 处理后的传入结果
     */
    void chanelRead(HandlerContext<IN> ctx, IN msg);
}
