package zym.stream.fileaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @Author 梁自强
 * @date 2018/7/19 0019 17:11
 * @desc 数据库处理 减少模板代码
 */
public interface DbAccessProcess<T>  {
    Optional<T> process(ResultSet rs, PreparedStatement ps, Connection cn, Device param) throws SQLException;
}
