package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/checkUrl")
public class checkUrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=utf-8");
        String url = request.getParameter("url");
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
        CloseableHttpResponse myResponse = null;
        Map<String, Boolean> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            myResponse = client.execute(httpGet);
            if(myResponse.getStatusLine().getStatusCode() == 200){
                map.put("check", true);
                response.getWriter().write(mapper.writeValueAsString(map));
            } else {
                map.put("check", false);
                response.getWriter().write(mapper.writeValueAsString(map));
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("check", false);
            try {
                response.getWriter().write(mapper.writeValueAsString(map));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
