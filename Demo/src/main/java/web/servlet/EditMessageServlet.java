package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.Admin;
import domain.Message;
import util.JDBCUtils;

@WebServlet("/EditMessageServlet")
public class EditMessageServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String id = request.getParameter("id").toString();
        String sql = "select * from Message where id = ?";
        Message message= template.queryForObject(sql, new BeanPropertyRowMapper<Message>(Message.class), id);
        request.setAttribute("message",message);
        request.getRequestDispatcher("Admin/EditMessage.jsp").forward(request,response);        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
