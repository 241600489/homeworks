package zym.stream.datatransfer;

import org.junit.Test;
import zym.stream.fileaccess.DbAccess;

import java.util.List;
import java.util.Optional;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 14:28
 * @desc
 */
public class DataTransferTest {
    @Test
    public void  getDataList() {
        DbAccess dbAccess = new DbAccess();
        long start = System.currentTimeMillis();
        FetchData fetchData = new FetchData();

        List<Record> list = fetchData.getResult(Config.WEIJI_URL);
        list.parallelStream()
                .filter(car -> !car.getHphm().equals(""))
                .forEach(record -> {
                    dbAccess.insert(record, (rs, ps, cn, param) -> {
                        ps = cn.prepareStatement("INSERT INTO tb_traffic_wf_record" +
                                " (recordId,srcsbbh,sbbh,hphm,srchpzl,wfsj,srcwfxw,scz,bzz,zpstr1,srcType,createTime,status)" +
                                "  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setLong(1, param.getRecordid());
                        ps.setString(2, param.getSrcsbbh());
                        ps.setString(3, param.getSbbh());
                        ps.setString(4, param.getHphm());
                        ps.setString(5, param.getSrchpzl());
                        ps.setLong(6, param.getWfsj());
                        ps.setString(7, param.getSrcwfxw());
                        ps.setInt(8, param.getScz());
                        ps.setInt(9, param.getBzz());
                        ps.setString(10, param.getZpstr1());
                        ps.setInt(11, param.getSrctype());
                        ps.setLong(12, param.getCreatetime());
                        ps.setInt(13, param.getStatus());
                        return ps.execute() ? Optional.of(param) : Optional.empty();
                    });
                });
        System.out.println(list.size());
        System.out.println("消耗时间："+(System.currentTimeMillis()-start)/1000+"s");

    }
}
