package zym.stream.datatransfer;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 10:42
 * @desc
 */
public class ReuqestUtil {
    private static final Logger log = Logger.getGlobal();

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            response.getEntity();
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.log(Level.WARNING, e, () -> "response 关闭异常");
            }
        }

        return resultString;
    }

    public static Map<String, Object> createParameter(Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
//        map.put("belt", "1,3");
        map.put("call", 2);
        map.put("rows", rows);
        map.put("start", 0);
        map.put("sortKey", "snapshotTime");
        map.put("sortType", "desc");
        map.put("startTime", 1533038400000L);
        map.put("endTime", 1533088800999L);
        return map;
    }
}
