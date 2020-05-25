package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import main.SignIn;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.soap.SOAPBinding;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class readSql {
    public static void main(String[] args) throws IOException, InterruptedException {

    }
    public static void read() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String hour = sdf.format(new Date());
        int now = Integer.parseInt(hour);
        int flag = 0;
        if( (now >= 0 && now <= 7) || (now >= 18 && now <= 24) ){ // 代表晚上的打卡
            flag = 2;
        } else if(now >= 11 && now <= 13){ // 代表早上的打卡
            flag = 1;
        }
        if(flag == 0){ // 非打卡时间段内触发 直接return
            return ;
        }
        final int tmp_flag = flag;
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<Map<String, Object>> maps = template.queryForList("select * from user");
        for(Map<String, Object> map : maps){
            String uid = (String) map.get("uid");
            String gh = (String) map.get("gh");
            String name = (String) map.get("name");
            String phone = (String) map.get("phone");
            String in = (String) map.get("in");
            final User user = new User(uid, gh, name, phone, in);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        QianDao.run(user, tmp_flag);
                    } catch (IOException | InterruptedException e) {
                        File logs = new File("logs");
                        if(!logs.exists()){
                            logs.mkdir();
                        }
                        File file = new File(logs + File.separator + "Exception.txt");
                        if(!file.exists()){
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        try {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
                            bufferedWriter.write(Exception.getExceptionToString(e));
                            bufferedWriter.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
