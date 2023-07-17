package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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
import domain.Good;
import domain.GoodType;
import util.JDBCUtils;

@WebServlet("/UpdateGoodServlet")
public class UpdateGoodServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        Good good = new Good();
        try {
            BeanUtils.populate(good,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String sql = "update Good set GoodTypeID=?,GoodName=?,GoodCount=?,GoodUnitPrice=?,Content=? where id=?";
        int renum= template.update(sql,request.getParameter("goodTypeID"),good.getGoodName(),good.getGoodCount(),good.getGoodUnitPrice(),good.getContent(),good.getId());
        if(renum==1) {
            sql = "select * from Good where id = ?";
            good= template.queryForObject(sql, new BeanPropertyRowMapper<Good>(Good.class), good.getId());
            request.setAttribute("good",good);
            List<Object> params = new ArrayList<Object>();
            sql = "select * from GoodType";
            List<GoodType> list1 = template.query(sql,new BeanPropertyRowMapper<GoodType>(GoodType.class),params.toArray());
            request.setAttribute("goodType",list1);
        	request.setAttribute("msg","修改成功"); 
        	request.getRequestDispatcher("/EditGoodServlet?id="+good.getId()+"").forward(request,response);
        }
        else {
        	request.setAttribute("msg","修改失败"); 
        	request.getRequestDispatcher("/EditGoodServlet?id="+good.getId()+"").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
