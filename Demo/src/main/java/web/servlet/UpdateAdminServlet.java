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

import com.alibaba.druid.sql.visitor.functions.Nil;

import domain.Admin;
import util.JDBCUtils;

@WebServlet("/UpdateAdminServlet")
public class UpdateAdminServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        Admin admin = new Admin();
        try {
            BeanUtils.populate(admin,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String sql = "update Admin set Account=?,Password=? where id=?";
        int renum= template.update(sql,admin.getAccount(),admin.getPassword(),admin.getId());
        if(renum==1) {
            sql = "select * from Admin where id = ?";
            admin= template.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class), admin.getId());
            request.setAttribute("admin",admin);
        	request.setAttribute("msg","修改成功"); 
        	request.getRequestDispatcher("/EditAdminServlet?id="+admin.getId()+"").forward(request,response);
        }
        else {
        	request.setAttribute("msg","修改失败"); 
        	request.getRequestDispatcher("/EditAdminServlet?id="+admin.getId()+"").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
