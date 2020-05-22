package text;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        HttpGet httpGet = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd1.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpGet.setHeader("Cookie", "JSESSIONID=6DD6CC6FE3DE33759CBA2AD26A5BF953");
        CloseableHttpClient build = HttpClientBuilder.create().build();
        CloseableHttpResponse result = build.execute(httpGet);
        System.out.println(EntityUtils.toString(result.getEntity(), "utf-8"));
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//        HttpGet request = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/com.primeton.eos.jkdk.xkdjkdkbiz.getStuXx.biz.ext");
//        request.setHeader("Cookie", "JSESSIONID=69B483BB1A4A3C57B7E72C7A0659DC4B");
//        CloseableHttpResponse response = client.execute(request);
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("stu.txt"));
//        bufferedWriter.write(EntityUtils.toString(response.getEntity()));
//        bufferedWriter.close();
    }
}
