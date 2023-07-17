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
import domain.User;
import util.JDBCUtils;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        HttpSession session = request.getSession();
        if(request.getParameter("Account")=="") {
        	request.setAttribute("login_msg","账号不能为空！");
            request.getRequestDispatcher("/UserLogin.jsp").forward(request,response);
        }
        else if(request.getParameter("Password")=="") {
        	request.setAttribute("login_msg","密码不能为空！");
            request.getRequestDispatcher("/UserLogin.jsp").forward(request,response);
        }
        else {
        	String sql = "select * from User where UserName = ? and UserPassword = ?";
            User userData =new User();
            try {
            	userData = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), request.getParameter("Account"), request.getParameter("Password"));
            }
            catch(Exception e){
            	request.setAttribute("login_msg","账号密码错误！");
                request.getRequestDispatcher("/UserLogin.jsp").forward(request,response);
            	System.out.println(e);
            }
            if(userData.getUserName() != null){
                session.setAttribute("User",userData);
                session.setAttribute("UserName",userData.getUserName());
                session.setAttribute("UserID",userData.getId());
                response.sendRedirect(request.getContextPath()+"/UserIndex.jsp");
            }
            else{        	
            	request.setAttribute("login_msg","账号密码错误！");
                request.getRequestDispatcher("/UserLogin.jsp").forward(request,response);
            }
		}        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
