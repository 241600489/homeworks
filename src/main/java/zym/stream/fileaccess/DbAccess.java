package zym.stream.fileaccess;

import java.sql.*;
import java.util.Optional;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 15:24
 * @desc 数据库连接池基类
 */
public class DbAccess {

    public static Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/business?"
                + "user=root&password=nihao1995&useUnicode=true&characterEncoding=UTF8&useSSL=false";
        String user = "root";
        String password = "admin@.123";
        try {
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public Optional<Device> getDevice(Device device, DbAccessProcess<Device> dbc) {
        Connection conn = getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            return dbc.process(rs, ps, conn, device);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
