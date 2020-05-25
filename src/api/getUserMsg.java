package api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import main.SignIn;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class getUserMsg {
    public static void main(String[] args) throws IOException {
        Map<String, String> map = getUsername("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd_test.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=", "16407020419");
        System.out.println(map.get("name"));
        System.out.println(map.get("phone"));
    }

    public static Map<String, String> getUsername(String url, String gh) throws IOException {
        String cookie = SignIn.getCookie(url);
        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.getJkdkRownum.biz.ext?gh=" + gh);
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        request.setHeader("Cookie", cookie);

        CloseableHttpResponse response = build.execute(request);
        JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        JSONArray list = jsonObject.getJSONArray("list");
        JSONObject json = list.getJSONObject(0);
        Map<String, String> map = new HashMap<>();
        map.put("name", json.getString("XM"));
        map.put("phone", json.getString("SJH2"));
        return map;
    }
}
