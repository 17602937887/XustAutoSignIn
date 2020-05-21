package text;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://ehallplatform.xust.edu.cn/default/jkdk/mobile/mobJkdkAdd_test.jsp?uid=MjYzNUJBQjA2RTU5OUI1RTFGMDQxMzVGNzk3RjlGNzc=");
        CloseableHttpResponse response = client.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
