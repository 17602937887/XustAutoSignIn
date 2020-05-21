package main;

import JdbcUtils.JDBCUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class SignIn {

    public static boolean start(User user) throws IOException {
        String url = user.getUid();
        String gh = user.getGh();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        String requestUrl = "http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.getJkdkRownum.biz.ext?gh=" + gh;
        HttpGet request = new HttpGet(requestUrl);
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        String cookie = getCookie(url);
        request.setHeader("Cookie", cookie);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return post(cookie, JSONObject.parseObject(result), url);
    }

    public static String getCookie(String url) throws IOException {
        String cookie = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        request.setHeader("Host", "ehallplatform.xust.edu.cn");
        CloseableHttpResponse response = client.execute(request);
        Header[] allHeaders = response.getAllHeaders();
        for(Header header : allHeaders){
            if(header.getName().equalsIgnoreCase("Set-Cookie")){
                StringBuffer stringBuffer = new StringBuffer();
                String val = header.getValue();
                for(int i = 0; i < val.length(); i++){
                    if(val.charAt(i) == ';'){
                        break;
                    }
                    stringBuffer.append(val.charAt(i));
                }
                cookie = stringBuffer.toString();
                break;
            }
        }
        return cookie;
    }

    public static boolean post(String cookie, JSONObject json, String Referer) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDay = sdf.format(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        String nextDay = sdf.format(calendar.getTime());

        JSONArray list = json.getJSONArray("list");

        //
        JSONObject tmpJsonObject = list.getJSONObject(0);
        tmpJsonObject.put("dm", tmpJsonObject.getString("BJDM"));
        tmpJsonObject.put("jrtwfw5", tmpJsonObject.get("jrtwfw5".toUpperCase()));
        tmpJsonObject.put("xxdz41", tmpJsonObject.getString("XXDZ4_1"));
        Set<String> strings = tmpJsonObject.keySet();
        ArrayList<String> arr = new ArrayList<>();
        for(String val : strings){
            arr.add(val);
        }
        for(int i = 0; i < arr.size(); i++){
            tmpJsonObject.put(arr.get(i).toLowerCase(), tmpJsonObject.getString(arr.get(i)));
        }
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).equals(arr.get(i).toUpperCase()))
                tmpJsonObject.remove(arr.get(i));
        }
        tmpJsonObject.remove("xxdz4_1");
        tmpJsonObject.put("jrrq1", nextDay);
        tmpJsonObject.put("tbsj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tmpJsonObject.put("time", nowDay);
        tmpJsonObject.put("guo", "中国");
        tmpJsonObject.remove("procinstid");
        tmpJsonObject.remove("id");


        //

        JSONObject jsonObject = new JSONObject();
        for(String key : tmpJsonObject.keySet()){
            String lowKey = key.toLowerCase();
            String val = tmpJsonObject.getString(key);
            if(key.equalsIgnoreCase("PROCINSTID") || key.equalsIgnoreCase("ID")){
                jsonObject.put(lowKey, Integer.parseInt(tmpJsonObject.getString(key)));
                continue;
            }
            jsonObject.put(lowKey, val);
        }

        jsonObject.put("time", nowDay);
        jsonObject.put("guo", "中国");
        jsonObject.put("dm", tmpJsonObject.getString("BJDM"));

        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        template.update("update user set name = ? where gh = ?", tmpJsonObject.getString("xm"), tmpJsonObject.getString("gh"));

        JSONObject postJson = new JSONObject();
        postJson.put("xkdjkdk", jsonObject);

        //
        JSONObject tmpPostJson = new JSONObject();
        tmpPostJson.put("xkdjkdk", tmpJsonObject);
        //


        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.jt.biz.ext");
        StringEntity entity = new StringEntity(tmpPostJson.toString(), "application/json", "utf-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpPost.setHeader("Referer", Referer);

        CloseableHttpResponse response = client.execute(httpPost);
        StatusLine statusLine = response.getStatusLine();
        return statusLine.getStatusCode() == 200 && response.getEntity().getContentLength() == 2;
    }
}


