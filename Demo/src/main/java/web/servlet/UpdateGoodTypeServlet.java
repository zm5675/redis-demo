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

import domain.Admin;
import domain.GoodType;
import util.JDBCUtils;

@WebServlet("/UpdateGoodTypeServlet")
public class UpdateGoodTypeServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        GoodType goodType = new GoodType();
        try {
            BeanUtils.populate(goodType,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String sql = "update GoodType set goodTypeName=? where id=?";
        int renum= template.update(sql,goodType.getGoodTypeName(),goodType.getId());
        if(renum==1) {
            sql = "select * from GoodType where id = ?";
            goodType= template.queryForObject(sql, new BeanPropertyRowMapper<GoodType>(GoodType.class), goodType.getId());
            request.setAttribute("goodType",goodType);
        	request.setAttribute("msg","修改成功"); 
        	request.getRequestDispatcher("/EditGoodTypeServlet?id="+goodType.getId()+"").forward(request,response);
        }
        else {
        	request.setAttribute("msg","修改失败"); 
        	request.getRequestDispatcher("/EditGoodTypeServlet?id="+goodType.getId()+"").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
