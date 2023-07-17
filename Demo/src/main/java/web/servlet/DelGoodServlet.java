package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;

import util.JDBCUtils;

@WebServlet("/DelGoodServlet")
public class DelGoodServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String sql = "DELETE FROM Good WHERE id=?;";
		int i = template.update(sql, id);
		request.setAttribute("isDel", "1");
		request.getRequestDispatcher("/GoodManageServlet?isDel=1").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
