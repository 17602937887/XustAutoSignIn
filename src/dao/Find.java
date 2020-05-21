package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class Find {
    public static boolean find(User user){
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<Map<String, Object>> maps = template.queryForList("select * from user where uid = ? and gh = ?", user.getUid(), user.getGh());
        if(maps.size() > 0){
            return true;
        } else {
            return false;
        }
    }
}
