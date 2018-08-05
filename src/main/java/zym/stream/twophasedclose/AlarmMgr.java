package zym.stream.twophasedclose;

import java.util.logging.Logger;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 10:44
 * @desc 告警功能入口类 是两阶段关闭模式中线程持有者 持有发送告警信息的线程
 */
public class AlarmMgr  {
    private static final Logger log = Logger.getGlobal();

    //保存 AlarmMgr 的唯一实例
    private static final AlarmMgr instance = new AlarmMgr();

    private volatile boolean isshutDownRequested = false;

    //告警发送线程
    private final AlarmSendingThread alarmSendingThread;

    private AlarmMgr() {
        alarmSendingThread = new AlarmSendingThread();
    }

    /**
     * 获取唯一实例
     * @return 返回单例
     */
    public AlarmMgr getInstance() {
        return instance;
    }

    public int sendAlarm(AlarmType alarmType, String id, String extraInfo) {
        log.info(() -> "alarmType: " + alarmType + "id: " + id + "extraInfo：" + extraInfo);
        int dumplicateSubmissionCount = 0;
        AlarmInfo alarmInfo = new AlarmInfo(alarmType, id);
        alarmInfo.setExtraInfo(extraInfo);
        dumplicateSubmissionCount = alarmSendingThread.sendAlarm(alarmInfo);

        return dumplicateSubmissionCount;
    }
    public void init() {
        alarmSendingThread.start();

    }

    public synchronized void shutdown() {
        if (isshutDownRequested) {
            throw new IllegalStateException("shutdown already  request ");
        }
        alarmSendingThread.terminate();
        isshutDownRequested=true;
    }

}
