package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetAllUser {
    public static List<User> getAllUser() throws IOException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<User> query = template.query("select * from user", new BeanPropertyRowMapper<>(User.class));
        return query;
    }
}
