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

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class SignIn {

    public static void main(String[] args) throws IOException {
        User user = new User("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd_test.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=", "16407020419", "陈航");
        System.out.println(start(user));
    }

    public static boolean start(User user) throws IOException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        template.update("insert into logs values(?, ?, ?)", "10000", "SignIn执行" + user.getName(), new Date());
        String url = user.getUid();
        String gh = user.getGh();

        CloseableHttpClient client = HttpClientBuilder.create().build();
        String requestUrl = "http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.getJkdkRownum.biz.ext?gh=" + gh;
        HttpGet request = new HttpGet(requestUrl);
        request.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        String cookie = getCookie(url);


        // Get下 判断是否签到成功
        System.out.println(getUidSuffix(url));
        HttpGet httpGet = new HttpGet(getUidSuffix(url));
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpGet.setHeader("Cookie", cookie);
        CloseableHttpClient build = HttpClientBuilder.create().build();
        CloseableHttpResponse res = build.execute(httpGet);
        String resultStr = EntityUtils.toString(res.getEntity(), "utf-8");



        // 测试
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ceshi.txt"));
//        bufferedWriter.write(resultStr);
//        bufferedWriter.newLine();
//        bufferedWriter.write(cookie);
//        bufferedWriter.close();
//        System.out.println(cookie);
//        if(1 == 1){
//            return true;
//        }
        //

        if ( resultStr.contains("您今日健康打卡已完成") && resultStr.length() < 3000 ){
            template.update("insert into logs values (?, ?, ?)", "gh = " + gh, "已经打卡成功", new Date());
            return true;
        }
        template.update("insert into logs values (?, ?, ?)", "gh = " + gh, "没有打卡", new Date());

        request.setHeader("Cookie", cookie);
        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return post(cookie, JSONObject.parseObject(result), url, user);
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

    public static boolean post(String cookie, JSONObject json, String Referer, User user) throws IOException {
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
        template.update("insert into logs values(?, ?, ?)", "name = " + tmpJsonObject.getString("xm"), "gh = " + tmpJsonObject.getString("gh") + "执行了增加姓名操作", new Date());

        JSONObject postJson = new JSONObject();
        postJson.put("xkdjkdk", jsonObject);

        //
        JSONObject tmpPostJson = new JSONObject();
        tmpPostJson.put("xkdjkdk", tmpJsonObject);
        //

        File Dir = new File("logs");
        if(!Dir.exists()){
            Dir.mkdir();
        }
        File file = new File(Dir + File.separator + "logs.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("学号: " + tmpJsonObject.getString("gh") + " 姓名: " + tmpJsonObject.getString("xm") + " 在" + new Date() + "进行了SingnIn操作");
        bufferedWriter.newLine();
        bufferedWriter.write("数据是:" + tmpPostJson.toString());
        bufferedWriter.newLine();
        bufferedWriter.write("用户的Cookie是: " + cookie);
        bufferedWriter.newLine();
        bufferedWriter.close();

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.jt.biz.ext");
        StringEntity entity = new StringEntity(tmpPostJson.toString(), "application/json", "utf-8");
        httpPost.setEntity(entity);

        httpPost.setHeader("Cookie", cookie);
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpPost.setHeader("Referer", Referer);
        httpPost.setHeader("Accept", "*/*");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Content-Type", "text/json");
        httpPost.setHeader("Host", "ehallplatform.xust.edu.cn");
        httpPost.setHeader("Origin", "https://ehallplatform.xust.edu.cn");
        httpPost.setHeader("Sec-Fetch-Dest", "empty");
        httpPost.setHeader("Sec-Fetch-Mode", "cors");
        httpPost.setHeader("Sec-Fetch-Site", "same-origin");
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
//        httpPost.setHeader("Content-Length", String.valueOf(entity.getContentLength()));

        // post数据
        client.execute(httpPost);

        // 重新Get下 判断是否签到成功
        HttpGet httpGet = new HttpGet(getUidSuffix(user.getUid()));
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpGet.setHeader("Cookie", cookie);
        CloseableHttpClient build = HttpClientBuilder.create().build();
        CloseableHttpResponse result = build.execute(httpGet);
        String resultStr = EntityUtils.toString(result.getEntity(), "utf-8");

        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("返回的长度: " + resultStr.length());
        bufferedWriter.close();

        if (resultStr.contains("您今日健康打卡已完成") && resultStr.length() < 3000){
            return true;
        }

        if(tmpJsonObject.getString("jdlx").equals("0")){
            tmpJsonObject.replace("jdlx", "1");
        } else {
            tmpJsonObject.replace("jdlx", "0");
        }

        tmpPostJson = new JSONObject();
        tmpPostJson.put("xkdjkdk", tmpJsonObject);

        Dir = new File("logs");
        if(!Dir.exists()){
            Dir.mkdir();
        }
        file = new File(Dir + File.separator + "logs.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("学号: " + tmpJsonObject.getString("gh") + " 姓名: " + tmpJsonObject.getString("xm") + " 在" + new Date() + "进行了SingnIn的二次操作");
        bufferedWriter.newLine();
        bufferedWriter.write("数据是:" + tmpPostJson.toString());
        bufferedWriter.newLine();
        bufferedWriter.write("用户的Cookie是: " + cookie);
        bufferedWriter.newLine();
        bufferedWriter.close();


        client = HttpClientBuilder.create().build();
        httpPost = new HttpPost("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.jt.biz.ext");
        entity = new StringEntity(tmpPostJson.toString(), "application/json", "utf-8");
        httpPost.setEntity(entity);

//         post数据
        client.execute(httpPost);

        // 重新Get下 判断是否签到成功
        httpGet = new HttpGet(getUidSuffix(user.getUid()));
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpGet.setHeader("Cookie", cookie);
        build = HttpClientBuilder.create().build();
        result = build.execute(httpGet);
        resultStr = EntityUtils.toString(result.getEntity(), "utf-8");


        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write("返回的长度: " + resultStr.length());
        bufferedWriter.close();
        return resultStr.contains("您今日健康打卡已完成") && resultStr.length() < 3000;
    }

    public static String getUidSuffix(String target){
        return "http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd1.jsp?" + target.substring(target.indexOf("uid="), target.length());
    }
}


