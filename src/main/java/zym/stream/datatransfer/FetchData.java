package zym.stream.datatransfer;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.stream.Collectors.toList;
import static zym.stream.datatransfer.Config.DETAIL_INFO;
import static zym.stream.datatransfer.Config.WEIJI_URL;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 10:32
 * @desc 访问php
 */
public class FetchData {
    private final ExecutorService executor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public List<Record> getResult(String url) {
        return FetchData.getCarInfoList()
                        .stream()

                        .map(ltm ->
                        CompletableFuture.supplyAsync(
                                () -> getRecord((JSONObject)ltm),executor)).collect(toList()).stream()
                        .map(CompletableFuture::join)
                        .collect(toList());
    }
    private Record getRecord(JSONObject ltm) {
        //根据carId 获取 记录详情
        String result = RequestUtil.doPostJson(DETAIL_INFO,
                new Gson()
                        .toJson(Collections
                                .singletonMap("id", ltm.get("carId"))));
        Map<String, Map> map = new Gson().fromJson(result, Map.class);
        Map<String, Object> srcInfo = (Map<String, Object>) map.get("textData").get("basic");
        Record record = new Record();
        record.setRecordid(Long
                .valueOf(srcInfo.get("srcId").toString()));
        record.setSrcsbbh(srcInfo.get("srcDeviceId").toString());
        record.setSbbh(srcInfo.get("tollgateDeviceId").toString());
        record.setHphm(srcInfo.get("srcCarPlateNumber").toString());
        record.setSrchpzl(ltm.get("carPlateType").toString());
        record.setWfsj(Long.valueOf(srcInfo.get("snapshotTime").toString()));
        record.setSrcwfxw("22");
        record.setScz(0);
        record.setBzz(0);
        record.setZpstr1(srcInfo.get("imageUrl").toString());
        record.setSrctype(1);
        record.setCreatetime(System.currentTimeMillis());
        record.setStatus(0);
        return record;
    }

    private static JSONArray getCarInfoList() {

        String result = RequestUtil.doPostJson(WEIJI_URL,
                JSONObject.toJSONString(RequestUtil.createParameter(10000)));
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getJSONArray("data");
    }

}
