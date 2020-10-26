package sms;

import JdbcUtils.JDBCUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class SendMessage {

    public static void main(String[] args) {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<Map<String, Object>> maps = template.queryForList("select * from user");
        for(Map<String, Object> map : maps) {
            String name = (String) map.get("name");
            String phone = (String) map.get("phone");
            sendSms(name, phone);
        }
    }
    public static void sendSms(String name, String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "你的key", "你的密钥");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "陈航");
        request.putQueryParameter("TemplateCode", "SMS_195225235");
        request.putQueryParameter("TemplateParam", "{\"name\":" + "\"" + name + "\"" +  "}");
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println(response.getData());
    }
}
