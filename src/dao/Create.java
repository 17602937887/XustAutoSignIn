package dao;

import JdbcUtils.JDBCUtils;
import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class Create {
    public static boolean create(User user){
        if(Find.find(user)){
            Delete.delete(user);
        }
        if(Add.add(user) == 1){
            return true;
        }
        return false;
    }
}
