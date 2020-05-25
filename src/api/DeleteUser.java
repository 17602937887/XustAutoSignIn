package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import dao.Delete;
import dao.Find;
import domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        String uid = request.getParameter("uid");
        String gh = request.getParameter("gh");
        User user = new User(uid, gh, "匿名用户", "10086", "1");
        Map<String, Boolean> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        if(!Find.find(user)){
            map.put("success", false);
            response.getWriter().write(mapper.writeValueAsString(map));
        } else {
            Delete.delete(user);
            map.put("success", true);
            response.getWriter().write(mapper.writeValueAsString(map));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
