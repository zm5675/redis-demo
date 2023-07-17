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
import domain.GoodType;
import util.JDBCUtils;

@WebServlet("/AddGoodLoadingServlet")
public class AddGoodLoadingServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        List<Object> params = new ArrayList<Object>();
        String sql = "select * from GoodType";
        List<GoodType> list = template.query(sql,new BeanPropertyRowMapper<GoodType>(GoodType.class),params.toArray());
        request.setAttribute("goodType",list);
        request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
