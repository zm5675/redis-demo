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

@WebServlet("/GoodDetailServlet")
public class GoodDetailServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String id = request.getParameter("id").toString();
        String sql = "select a.*,b.GoodTypeName from Good a inner join GoodType b on a.GoodTypeID=b.id where a.id = ?";
        Good good= template.queryForObject(sql, new BeanPropertyRowMapper<Good>(Good.class), id);
        request.setAttribute("good",good);
        request.getRequestDispatcher("GoodDetail.jsp").forward(request,response);        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
