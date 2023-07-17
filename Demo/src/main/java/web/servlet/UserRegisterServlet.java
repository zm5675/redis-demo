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
import domain.User;
import util.JDBCUtils;

@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        if(request.getParameter("Account")=="") {
        	request.setAttribute("msg","账号不能为空！");
            request.getRequestDispatcher("UserRegister.jsp").forward(request,response);
        }
        else if(request.getParameter("Password")=="") {
        	request.setAttribute("msg","密码不能为空！");
            request.getRequestDispatcher("UserRegister.jsp").forward(request,response);
        }
        else {
        	String sql = "select * from [User] where UserName = ?";
            User userData =new User();
            List<User> list=null;
            try {
            	StringBuilder sb = new StringBuilder(sql);
            	
            	List<Object> params = new ArrayList<Object>();
            	sb.append(" and UserName = ? ");
                params.add(""+request.getParameter("Account")+"");//
            	list = template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
            }
            catch(Exception e){
            	System.out.println(e);
            }
            if(list!=null) {
            	if(list.size()!=0) {
            		request.setAttribute("login_msg","注册失败，该账号已被注册！");
                	request.getRequestDispatcher("UserRegister.jsp").forward(request,response);            		
            	}
            	else {            		
            		sql = "insert into User(UserName,UserPassword,UserTel,UserAddr,Content,addDate) values('"+request.getParameter("Account")+"','"+request.getParameter("Password")+"','"+request.getParameter("Tel")+"','"+request.getParameter("Addr")+"','"+request.getParameter("Content")+"',now())";
                    int renum=template.update(sql);
                    if(renum==1) {
                    	request.setAttribute("login_msg","注册成功！");
                    	request.getRequestDispatcher("UserRegister.jsp").forward(request,response);
                    }
                    else {
                    	request.setAttribute("login_msg","注册失败！");
                    	request.getRequestDispatcher("UserRegister.jsp").forward(request,response);
                    }
            	}
            }
            else {
            	sql = "insert into User(UserName,UserPassword,UserTel,UserAddr,Content,addDate) values('"+request.getParameter("Account")+"','"+request.getParameter("Password")+"','"+request.getParameter("Tel")+"','"+request.getParameter("Addr")+"','"+request.getParameter("Content")+"',now())";
                int renum=template.update(sql);
                if(renum==1) {
                	request.setAttribute("login_msg","注册成功！");
                	request.getRequestDispatcher("UserRegister.jsp").forward(request,response);
                }
                else {
                	request.setAttribute("login_msg","注册失败！");
                	request.getRequestDispatcher("UserRegister.jsp").forward(request,response);
                }
            }
		}        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
