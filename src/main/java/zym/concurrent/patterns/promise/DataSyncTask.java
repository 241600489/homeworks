package zym.concurrent.patterns.promise;

import java.util.Map;

/**
 * @Author unyielding
 * @date 2018/8/7 0007 7:10
 * @desc 数据同步模块的入口类
 */
public class DataSyncTask implements Runnable{
    private final Map<String,String> taskParameters;

    public DataSyncTask(Map<String, String> taskParameters) {
        this.taskParameters = taskParameters;
    }

    @Override
    public void run() {
        String ftpServer = taskParameters.get("ftpServer");
        String ftpUserName = taskParameters.get("ftpUserName");
        String ftpPassword = taskParameters.get("ftpPasswordz");
        
    }
}
