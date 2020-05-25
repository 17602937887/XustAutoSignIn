package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class Add {
    public static int add(User user){
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        return template.update("insert into user values(?, ?, ?, ?)", user.getUid(), user.getGh(), user.getName(), user.getPhone());
    }
}
