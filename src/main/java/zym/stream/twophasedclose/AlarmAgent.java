package zym.stream.twophasedclose;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 13:57
 * @desc 负责连接告警服务器，并发送告警信息到告警服务器
 */
public class AlarmAgent {
    private final static Logger log = Logger.getGlobal();
    //是否连接到告警服务器
    private volatile boolean connectToServer = false;

    //模块角色 保护性暂挂 之谓词
    private final Predicate agentConnected = () -> connectToServer;
    //模块角色 保护性暂挂 之Blocker
    private final Blocker blocker = new ConditionVarBlocker();

    //心跳计时器
    private final Timer heartbeatTimer = new Timer(true);

    // 省略其他代码

    public void sendAlarm(final AlarmInfo alarmInfo) {
        //可能需要等待
        GuardedAction<Void> guardedAction = new GuardedAction<Void>(agentConnected) {
            @Override
            public Void call() throws Exception {
                doSendAlarm(alarmInfo);
                return null;
            }
        };
        blocker.callWithGuard(guardedAction);
    }

    void doSendAlarm(AlarmInfo alarmInfo) {
        //省略其他代码
        log.info("sending alarm" + alarmInfo);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.log(Level.WARNING, e, () -> e.getMessage());
        }
    }

    protected void onConnected() {
        blocker.signAfter(() -> {
            connectToServer = true;
            log.info("连接到服务器");
            return Boolean.TRUE;
        });
    }

    public void init() {
        Thread connectingThread = new Thread(new ConnectingTask());
        connectingThread.start();
        heartbeatTimer.schedule(new HearbeatTask(), 60000, 2000);
    }

    private class ConnectingTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                log.log(Level.WARNING, e, () -> "发生阻断性错误");
            }
        }
    }

    protected void onDisconnected() {
        connectToServer = false;
    }
    /** 心跳定时任务：定时检查与告警服务器的连接是否正常*/
    private class HearbeatTask extends TimerTask {

        @Override
        public void run() {
            if (!testConnection()) {
                onDisconnected();
                reconnect();
            }
        }

        private boolean testConnection() {
            //省略其它代码

            return true;
        }

        private void reconnect() {
            ConnectingTask connectingThread = new ConnectingTask();
            //直接在心跳线程中执行
            connectingThread.run();
        }

    }




}