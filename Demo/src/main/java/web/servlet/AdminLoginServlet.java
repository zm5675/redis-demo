package web.servlet;

import domain.Admin;
import util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        HttpSession session = request.getSession();
        if(request.getParameter("Account")=="") {
        	request.setAttribute("login_msg","账号不能为空！");
            request.getRequestDispatcher("/AdminLogin.jsp").forward(request,response);
        }
        else if(request.getParameter("Password")=="") {
        	request.setAttribute("login_msg","密码不能为空！");
            request.getRequestDispatcher("/AdminLogin.jsp").forward(request,response);
        }
        else {
        	String sql = "select * from admin where account = ? and password = ?";
            Admin adminData =new Admin();
            try {
            	adminData = template.queryForObject(sql, new BeanPropertyRowMapper<Admin>(Admin.class), request.getParameter("Account"), request.getParameter("Password"));
            }
            catch(Exception e){
            	request.setAttribute("login_msg","账号密码错误！");
                request.getRequestDispatcher("/AdminLogin.jsp").forward(request,response);
            	System.out.println(e);
            }
            if(adminData.getAccount() != null){
                session.setAttribute("Admin",adminData);
                session.setAttribute("Account",adminData.getAccount());
                session.setAttribute("AdminID",adminData.getId());
                response.sendRedirect(request.getContextPath()+"/Admin/Index.jsp");
            }
            else{        	
            	request.setAttribute("login_msg","账号密码错误！");
                request.getRequestDispatcher("/AdminLogin.jsp").forward(request,response);
            }
		}        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
