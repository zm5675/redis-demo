package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.User;
import util.JDBCUtils;

@WebServlet("/UpdateUserInfoServlet")
public class UpdateUserInfoServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String sql = "update User set UserName=?,UserPassword=?,UserTel=?,UserAddr=?,Content=? where id=?";
        int renum= template.update(sql,user.getUserName(),user.getUserPassword(),user.getUserTel(),user.getUserAddr(),user.getContent(),user.getId());
        if(renum==1) {
            sql = "select * from User where id = ?";
            user= template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getId());
            request.setAttribute("user",user);
        	request.setAttribute("msg","修改成功"); 
        	request.getRequestDispatcher("/UserInfoServlet?id="+user.getId()+"").forward(request,response);
        }
        else {
        	request.setAttribute("msg","修改失败"); 
        	request.getRequestDispatcher("/UserInfoServlet?id="+user.getId()+"").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
