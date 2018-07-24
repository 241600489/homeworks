package zym.stream.fileaccess;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 11:17
 * @desc 设备
 */
public class Device {
    private Integer parentId;


    private Integer id;
    private String roadName;

    private Integer type;//节点类型：0分组 1方向,2卡口,3电警

    private String direct;

    private Integer directCode;

    private String deviceCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public Integer getDirectCode() {
        return directCode;
    }

    public void setDirectCode(Integer directCode) {
        this.directCode = directCode;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    @Override
    public String toString() {
        return "Device{" +
                "parentId=" + parentId +
                ", id=" + id +
                ", roadName='" + roadName + '\'' +
                ", type=" + type +
                ", direct='" + direct + '\'' +
                ", directCode=" + directCode +
                ", deviceCode='" + deviceCode + '\'' +
                '}';
    }
}
