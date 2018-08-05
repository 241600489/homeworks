package zym.stream.twophasedclose;

/**
 * @Author unyielding
 * @date 2018/8/5 0005 11:17
 * @desc 告警信息
 */
public class AlarmInfo {
    private AlarmType type;
    private String id;
    private String extraInfo;

    public AlarmInfo(AlarmType type, String id) {
        this.type = type;
        this.id = id;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
