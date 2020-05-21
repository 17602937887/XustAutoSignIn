package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import main.SignIn;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class readSql {
    public static void main(String[] args) throws IOException {
        read();
    }
    public static void read() throws IOException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<Map<String, Object>> maps = template.queryForList("select * from user");
        for(Map<String, Object> map : maps){
            String uid = (String) map.get("uid");
            String gh = (String) map.get("gh");
            final User user = new User(uid, gh, "匿名用户");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        QianDao.run(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
