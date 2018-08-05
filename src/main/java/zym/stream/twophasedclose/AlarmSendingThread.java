package zym.stream.twophasedclose;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 11:03
 * @desc
 */
public class AlarmSendingThread extends AbstractTerminableThread {

    private final AlarmAgent alarmAgent = new AlarmAgent();

    public int sendAlarm(AlarmInfo alarmInfo) {
        //
        return 0;
    }

    public void terminate() {

    }
}
