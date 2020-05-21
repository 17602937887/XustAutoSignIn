package domain;

import JdbcUtils.JDBCUtils;
import domain.Demo;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@WebServlet("/Bean")
public class Bean extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());
        template.update("insert into ds values(?)", Integer.toString(new Random().nextInt(100)));
        FileWriter fileWriter1 = new FileWriter(new File("hhhhhhhhhh.txt"));
        fileWriter1.write(Demo.class == null ? "Demo.class为空" : "Demo.class不为空" + Demo.class);
        fileWriter1.close();
        List<Demo> query = template.query("select * from ds", new BeanPropertyRowMapper<>(Demo.class));
        FileWriter fileWriter = new FileWriter(new File("ds.txt"));
        for(Demo demo : query){
            fileWriter.write(demo.getName() + "\n");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
