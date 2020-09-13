package api;

import dao.Create;
import dao.QianDao;
import domain.User;
import sms.SendSuccessSms;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            String in = request.getParameter("in");
            if( (uid == null || uid.length() == 0) || (!uid.contains("http://ehallplatform.xust.edu.cn")) && (!uid.contains("https://ehallplatform.xust.edu.cn"))){
                response.sendRedirect("/failed.html");
            } else if ((gh == null || gh.length() < 10) || (gh.length() == 10 && Integer.parseInt(gh.substring(0, 2)) >= 16) || gh.length() > 11){
                response.sendRedirect("/failedGh.html");
            } else if(in == null || in.length() == 0){
                response.sendRedirect("/failedIn.html");
            } else {
                Map<String, String> map = getUserMsg.getUsername(uid, gh);
                User user = new User(uid, gh, map.get("name"), map.get("phone"), in);
                Create.create(user); // 创建用户
                SendSuccessSms.sendSms(user.getName(), user.getPhone());
                SimpleDateFormat sdf = new SimpleDateFormat("HH");
                String hour = sdf.format(new Date());
                int now = Integer.parseInt(hour);
                int flag = 0;
                if( (now >= 0 && now <= 7) || (now >= 15 && now <= 24) ){ // 代表晚上的打卡
                    flag = 2;
                } else if(now >= 11 && now <= 14){ // 代表早上的打卡
                    flag = 1;
                }
                // 如果用户在打卡时间段内注册账号 则直接为其打卡一次;
                try {
                    QianDao.run(user, flag);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/success.html");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
