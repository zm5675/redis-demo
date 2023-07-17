package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.Admin;
import util.JDBCUtils;

@WebServlet("/AddAdminServlet")
public class AddAdminServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        if(request.getParameter("Account")=="") {
        	request.setAttribute("msg","账号不能为空！");
            request.getRequestDispatcher("Admin/AddAdmin.jsp").forward(request,response);
        }
        else if(request.getParameter("Password")=="") {
        	request.setAttribute("msg","密码不能为空！");
            request.getRequestDispatcher("Admin/AddAdmin.jsp").forward(request,response);
        }
        else {
        	String sql = "insert into Admin(Account,Password) values('"+request.getParameter("Account")+"','"+request.getParameter("Password")+"')";
            int renum=template.update(sql);
            if(renum==1) {
            	request.setAttribute("msg","添加成功！");
            	request.getRequestDispatcher("Admin/AddAdmin.jsp").forward(request,response);
            }
            else {
            	request.setAttribute("msg","添加失败！");
            	request.getRequestDispatcher("Admin/AddAdmin.jsp").forward(request,response);
            }
		}        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
