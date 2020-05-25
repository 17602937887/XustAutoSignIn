package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import main.SignIn;
import main.SignInAfternoon;
import main.SignInNight;
import org.springframework.jdbc.core.JdbcTemplate;
import sms.SendFailedSms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class QianDao {
    public static void run(User user, int flag) throws IOException, InterruptedException {
        if( (flag != 1) && (flag != 2)){
            return ;
        }
        int t = 3; // 最多尝试3次终止
        if(flag == 1){ // 只会传进来1 和 2
            while(t > 0 && !SignInAfternoon.start(user)){
                t--;
            }
        } else{
            while(t > 0 && !SignInNight.start(user)){
                t--;
            }
        }

        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        if(t == 0){ // 此用户自动签到失败了 打出到日志 并发送短信提醒
            template.update("insert into logs values(?, ?, ?)", "学号:" + user.getGh() + " 姓名:" + user.getName(), "failed", new Date());
            SendFailedSms.sendSms(user.getName(), user.getPhone());
        } else {
            template.update("insert into logs values(?, ?, ?)", "学号:" + user.getGh() + " 姓名:" + user.getName(), "success", new Date());
        }
    }
}
