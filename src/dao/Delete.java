package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class Delete {
    public static void delete(User user){
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        template.update("delete from user where uid = ? and gh = ?", user.getUid(), user.getGh());
    }
}
