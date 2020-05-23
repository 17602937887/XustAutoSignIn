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

public class Demo {
    public static void main(String[] args) throws IOException {
//        CloseableHttpClient build = HttpClientBuilder.create().build();
//        HttpGet httpGet = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd_test.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=");
//        BufferedWriter noCookie = new BufferedWriter(new FileWriter("NoCookie"));
//        noCookie.write(EntityUtils.toString(build.execute(httpGet).getEntity()));
//        noCookie.close();

        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpGet httpGet2 = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd1.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=");
        httpGet2.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpGet2.setHeader("Cookie", "JSESSIONID=C0AB9109A1EC986AB58C5E8F154D49C8");
        BufferedWriter cookie = new BufferedWriter(new FileWriter("cookie1"));
        cookie.write(EntityUtils.toString(build.execute(httpGet2).getEntity()));
        cookie.close();

        // Get下 判断是否签到成功
        CloseableHttpClient build2 = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd1.jsp?uid=M0YyNkIxQzNGNkExQkVCRThGRkNFQTEzMzI2RjY4Q0U=");
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        httpGet.setHeader("Cookie", "JSESSIONID=C0AB9109A1EC986AB58C5E8F154D49C8");
        BufferedWriter cookieFile = new BufferedWriter(new FileWriter("cookie2"));
        cookieFile.write(EntityUtils.toString(build2.execute(httpGet).getEntity()));
        cookieFile.close();

    }
}
