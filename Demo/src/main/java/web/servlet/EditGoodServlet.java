package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.Admin;
import domain.Good;
import domain.GoodType;
import util.JDBCUtils;

@WebServlet("/EditGoodServlet")
public class EditGoodServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String id = request.getParameter("id").toString();
        String sql = "select * from Good where id = ?";
        Good good= template.queryForObject(sql, new BeanPropertyRowMapper<Good>(Good.class), id);
        List<Object> params = new ArrayList<Object>();
        sql = "select * from GoodType";
        List<GoodType> list1 = template.query(sql,new BeanPropertyRowMapper<GoodType>(GoodType.class),params.toArray());
        request.setAttribute("goodType",list1);
        request.setAttribute("good",good);
        request.getRequestDispatcher("Admin/EditGood.jsp").forward(request,response);        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
