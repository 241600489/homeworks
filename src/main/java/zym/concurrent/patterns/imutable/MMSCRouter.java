package zym.concurrent.patterns.imutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * @Author unyielding
 * @date 2018/8/4 0004 7:12
 * @desc 彩信路由中心管理器
 * 模式角色：ImmutableObject.ImmutableObject
 */
public class MMSCRouter {
    //使用volatile 修饰,使得在多线程环境中可见
    private static volatile MMSCRouter instance = new MMSCRouter();
    //维护手机号前缀到彩信中心的映射关系
    private final Map<String, MMSCInfo> routeMap;


    MMSCRouter() {
        this.routeMap = retrieveRouteMapFromDB();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDB() {
        HashMap<String, MMSCInfo> map = new HashMap<>();

        //。。。

        return map;
    }

    public static MMSCRouter getInstance() {
        return instance;
    }

    /**
     * 根据手机号前缀获取 彩信中心的信息
     *
     * @param prefix 手机号前缀
     * @return 返回彩信中心的信息
     */
    public MMSCInfo getInfo(String prefix) {
        return routeMap.get(prefix);
    }

    /**
     * 将传入 的新实例赋值给instance
     *
     * @param newInstance 传进来 的新 实例
     */
    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }

    /**
     * 深拷贝
     *
     * @param m
     * @return
     */
    private static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> m) {
        HashMap<String, MMSCInfo> map = new HashMap<>();
        for (String key : m.keySet()) {
            map.put(key, new MMSCInfo(m.get(key)));
        }
        return map;
    }

    public Map<String, MMSCInfo> getRouteMap() {
        //做防御性的复制
        return Collections.unmodifiableMap(deepCopy(routeMap));
    }


}



