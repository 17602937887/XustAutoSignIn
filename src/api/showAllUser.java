package api;

import JdbcUtils.JDBCUtils;
import dao.GetAllUser;
import domain.Logs;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/showAllUser")
public class showAllUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

//        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
//        template.update("insert into logs values(?, ?, ?)", "10086", "ceshi", new Date());
//        List<Logs> query = template.query("select * from logs", new BeanPropertyRowMapper<Logs>(Logs.class));
//        request.setAttribute("allLogs", query);
//        request.getRequestDispatcher("/showLogs.jsp").forward(request, response);

        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        List<Map<String, Object>> maps = template.queryForList("select * from user");
        List<User> allUser = new ArrayList<>();
        for(Map<String, Object> map : maps){
            String uid = (String) map.get("uid");
            String gh = (String) map.get("gh");
            String name = (String) map.get("name");
            String phone = (String) map.get("phone");
            String in = (String) map.get("in");
            allUser.add(new User(uid, gh, name, phone, in));
        }
        request.setAttribute("allUser", allUser);
        request.getRequestDispatcher("/showAllUser.jsp").forward(request, response);

//        File log = new File("log");
//        if(!log.exists()){
//            log.mkdir();
//        }
//
//        FileWriter fileWriter1 = new FileWriter(new File(log + File.separator + "request.txt"), true);
//        fileWriter1.write(request == null ? "request为空" : "request不为空");
//        fileWriter1.close();
//
//
//        DataSource ds = JDBCUtils.getDatasource();
//        JdbcTemplate template = new JdbcTemplate(ds);
//
//        FileWriter fileWriter2 = new FileWriter(new File(log + File.separator + "template.txt"), true);
//        fileWriter2.write(template == null ? "template为空" : "template不为空");
//        fileWriter2.write("\n");
//        fileWriter2.write(ds == null ? "ds为空" : "ds不为空");
//        fileWriter2.close();
//
//        List<User> query = new ArrayList<>();
//
//        try {
////            List<Map<String, Object>> maps = template.queryForList("select uid, gh, name from user");
//            FileWriter fileWriter4 = new FileWriter(new File(log + File.separator + "NoError.txt"));
//            fileWriter4.write("No Error");
////            for(Map<String, Object> map : maps){
////                fileWriter4.write(map.toString());
////                fileWriter4.write("\n");
////            }
//            fileWriter4.close();
//            query = GetAllUser.getAllUser();
//        } finally {
//            FileWriter fileWriter3 = new FileWriter(new File(log + File.separator + "query.txt"), true);
//            fileWriter3.write(query == null ? "query为空" : "query不为空" + "query.size() = " + query.size());
//            fileWriter3.close();
//
//            request.setAttribute("allUser", query);
//            request.getRequestDispatcher("/showAllUser.jsp").forward(request, response);
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
