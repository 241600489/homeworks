package zym.stream.twophasedclose;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 13:47
 * @desc 可停止的抽象线程
 *        两阶段关闭的AbstractTerminableThread
 */
public class AbstractTerminableThread extends Thread implements Termitable {



    @Override
    public void terminate() {

    }
}
