package zym.stream.fileaccess;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 14:51
 * @desc 处理数据
 */
public class DataHandler {
    public void handleList(Optional<List<Device>> optional) {

        //将数据进行分组
        Map<String, Map<Integer, List<Device>>> map = optional.get()
                .stream()
                .collect(groupingBy(Device::getRoadName,
                        groupingBy(Device::getDirectCode)));
        //
        DbAccess access = new DbAccess();
        Set<String> keySet = map.keySet();
        List<Device> result = new ArrayList<>();
        for (String s : keySet) {
            Map<Integer, List<Device>> listMap = map.get(s);
            Set<Integer> keySet1 = listMap.keySet();
            for (Integer i : keySet1) {
                Device param = new Device();
                param.setRoadName(s);
                Optional<Device> route = access.getDevice(param, (rs, ps, con, de) -> {
                    Device res = new Device();
                    ps = con.prepareStatement("SELECT * FROM tb_tollgate_device WHERE deviceName=?");
                    ps.setString(1, de.getRoadName());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        res.setId(rs.getInt("deviceId"));
                    }
                    return Optional.of(res);
                });

                param = route.orElse(new Device());
                param.setDirectCode(i);
                //maxBy(comparing(Student::getScore))


                Optional<Device> direct =access.getDevice(param, (rs, ps, con, de) -> {
                    ps = con.prepareStatement("SELECT  *  FROM tb_tollgate_device WHERE parentId=? and deviceDirection=? ");
                    ps.setInt(1, de.getId());
                    ps.setInt(2, de.getDirectCode());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        de.setId(rs.getInt("deviceId"));
                        de.setRoadName(rs.getString("deviceName"));
                    }
                    return Optional.of(de);
                });

                List<Device> list = listMap.get(i);
                result.addAll(list.stream().map(t -> {
                    t.setParentId(direct.orElse(null).getId());
//                    t.setRoadName(direct.getRoadName());
                    return t;
                }).collect(toList()));
            }
        }
//        System.out.println(result.size());

        result.stream().forEach(device -> {
            access.getDevice(device,(rs,ps,con,de)->{
                ps = con
                        .prepareStatement("INSERT  into tb_tollgate_device" +
                                " (parentId,deviceCode,deviceDirection,deviceType)" +
                                " VALUES (?,?,?,?)");
                ps.setInt(1, device.getParentId());
                ps.setString(2, device.getDeviceCode());
                ps.setInt(3, device.getDirectCode());
                ps.setInt(4, device.getType());
                return ps.execute() ? Optional.of(device) : Optional.empty();
            });
        });

    }

    public void handleDat(Optional<List<Device>> optional) {
        DbAccess access = new DbAccess();
        optional.get().stream().forEach(device->{
            access.getDevice(device, (rs, ps, cn, param) -> {
                ps = cn
                        .prepareStatement("UPDATE tb_tollgate_device " +
                                " SET deviceName=?  " +
                                "  WHERE deviceCode=? ");
                ps.setString(1, device.getRoadName());
                ps.setString(2, device.getDeviceCode());
                return ps.execute() ? Optional.of(device) : Optional.empty();
            });
        });

    }
}
