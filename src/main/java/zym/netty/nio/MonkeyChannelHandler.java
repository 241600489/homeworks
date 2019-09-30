package zym.netty.nio;

/**
 * channnel 处理器
 *
 * @author liangziqiang
 * @date 2019/9/30 17:34
 */
public interface MonkeyChannelHandler<T> {

    void processRead(T msg);
}
