package zym.stream.imutable;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 7:48
 * @desc 处理 彩信中心的变更
 * 与运维中心对接的类
 * ImmutableObject 操控者
 */
public class OMCAgent extends Thread {
    public void run() {
        boolean isTableModificationMsg = false;

        String updateTableName = null;

        while (true) {
            /*
              从 运维连接的读取 消息并解析
              解析到数据表更新到消息后，重置MMSCRouter实例
             */
            if (isTableModificationMsg) {
                if ("MMSCInfo".equals(updateTableName)) {
                    MMSCRouter.setInstance(new MMSCRouter());

                }
            }
            //......
        }
    }
}
