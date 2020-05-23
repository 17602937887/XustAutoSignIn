package text;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
//        CloseableHttpClient client = HttpClientBuilder.create().build();

        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://api01.monyun.cn:7901/sms/v2/std/single_send");
        StringEntity entity = new StringEntity("{\"apikey\":\"f7088acff805b841c7309b4d8b5ad3eb\",\"mobile\":\"17602937887\",\"content\":\"%C4%E3%BA%C3%D1%BD%A3%AC%B2%E2%CA%D4\",\"svrtype\":\"SMS001\",\"exno\":\"0006\",\"custid\":\"b3d0a2783d31b21b8573\",\"exdata\":\"exdata000002\"}");
//        StringEntity entity = new StringEntity("{\"userid\":\"E10D7M\",\"pwd\":\"34fBoR\",\"mobile\":\"17602937887\",\"content\":\"%d1%e9%d6%a4%c2%eb%a3%ba6666%a3%ac%b4%f2%cb%c0%b6%bc%b2%bb%d2%aa%b8%e6%cb%df%b1%f0%c8%cb%c5%b6%a3%a1\",\"timestamp\":\"0803192020\",\"svrtype\":\"SMS001\",\"exno\":\"0006\",\"custid\":\"b3d0a2783d31b21b8573\",\"exdata\":\"exdata000002\"}", "application/json", "utf-8");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpPost.setHeader("Content-Type", "text/json");
        httpPost.setEntity(entity);
        System.out.println(EntityUtils.toString(entity));
        CloseableHttpResponse response = build.execute(httpPost);
        System.out.println(EntityUtils.toString(response.getEntity()));

//        HttpGet httpGet = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd1.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=");
//        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
//        httpGet.setHeader("Cookie", "JSESSIONID=6DD6CC6FE3DE33759CBA2AD26A5BF953");
//        CloseableHttpClient build = HttpClientBuilder.create().build();
//        CloseableHttpResponse result = build.execute(httpGet);
//        System.out.println(EntityUtils.toString(result.getEntity(), "utf-8"));


//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.getStuXx.biz.ext");
//        request.setHeader("Cookie", "JSESSIONID=69B483BB1A4A3C57B7E72C7A0659DC4B");
//        CloseableHttpResponse response = client.execute(request);
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("stu.txt"));
//        bufferedWriter.write(EntityUtils.toString(response.getEntity()));
//        bufferedWriter.close();
    }
}
