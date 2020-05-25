package sms;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SendSuccessSms {
    public static void sendSms(String name, String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIlHdZZxWKDTW3", "yS9KudRRWfBTxzNywTBPPsxYBuVhqh");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "陈航");
        request.putQueryParameter("TemplateCode", "SMS_190793505");
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
