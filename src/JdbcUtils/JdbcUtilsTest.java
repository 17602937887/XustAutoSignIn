package JdbcUtils;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JdbcUtilsTest {
    public static void main(String[] args) throws IOException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<Map<String, Object>> maps = template.queryForList("select * from user");
    }
}

