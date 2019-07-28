package zym.concurrent.patterns.imutable;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 7:15
 * @desc 彩信中心的信息
 *      模块角色：ImmutableObject.ImmutableObject
 */
public class MMSCInfo {

    private final String deviceID;

    private final String url;

    private final int maxAttachmentSizeInBytes;

     MMSCInfo(MMSCInfo mmscInfo) {
        this.deviceID = mmscInfo.getDeviceID();
        this.url = mmscInfo.getUrl();
        this.maxAttachmentSizeInBytes = mmscInfo.getMaxAttachmentSizeInBytes();
    }

    public MMSCInfo(String deviceID, String url, int maxAttachmentSizeInBytes) {
        this.deviceID = deviceID;
        this.url = url;
        this.maxAttachmentSizeInBytes = maxAttachmentSizeInBytes;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachmentSizeInBytes() {
        return maxAttachmentSizeInBytes;
    }
}
