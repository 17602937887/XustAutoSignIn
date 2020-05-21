package api;

import JdbcUtils.JDBCUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Create;
import dao.Delete;
import dao.QianDao;
import domain.User;
import main.SignIn;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/createUser")
public class createUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("delete") != null){
            request.getRequestDispatcher("/DeleteUser").forward(request, response);
        } else {
            request.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            String uid = request.getParameter("uid");
            String gh = request.getParameter("gh");
            if(!uid.contains("http://ehallplatform.xust.edu.cn")){
                response.sendRedirect("https://xust.hangcc.cn/failed.html");
            } else {
                User user = new User(uid, gh);
                boolean flag = Create.create(user);
                Map<String, Boolean> map = new HashMap<>();
                ObjectMapper mapper = new ObjectMapper();
                if(flag){
                    map.put("success", true);
                } else {
                    map.put("success",  false);
                }
                QianDao.run(user);
                Create.create(user);
                response.sendRedirect("https://xust.hangcc.cn/success.html");
                response.getWriter().write(mapper.writeValueAsString(map));
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
