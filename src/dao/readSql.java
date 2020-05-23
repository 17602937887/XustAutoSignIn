package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import main.SignIn;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.soap.SOAPBinding;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class readSql {
    public static void main(String[] args) throws IOException, InterruptedException {
        read();
    }
    public static void read() throws IOException, InterruptedException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        template.update("insert into logs values(?, ?, ?)", "10000", "readSql执行", new Date());
        List<Map<String, Object>> maps = template.queryForList("select * from user");
        for(Map<String, Object> map : maps){
            Thread.sleep(3000);
            String uid = (String) map.get("uid");
            String gh = (String) map.get("gh");
            final User user = new User(uid, gh, "匿名用户");
            File file = new File("readSql.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("在" + new Date() + "执行了readSql函数");
            fileWriter.close();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        QianDao.run(user);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
