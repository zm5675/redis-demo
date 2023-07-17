package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcTemplate;

import util.JDBCUtils;

@WebServlet("/AddMessageServlet")
public class AddMessageServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        if(request.getParameter("Content")=="") {
        	request.setAttribute("msg","留言内容不能为空！");
            request.getRequestDispatcher("AddMessage.jsp").forward(request,response);
        }
        else {
        	HttpSession session = request.getSession();
        	Integer userId = (int)session.getAttribute("UserID");
        	String sql = "insert into Message(UserID,Content,addDate) values('"+userId+"','"+request.getParameter("Content")+"',now())";
            int renum=template.update(sql);
            if(renum==1) {
            	request.setAttribute("msg","添加成功！");
            	request.getRequestDispatcher("AddMessage.jsp").forward(request,response);
            }
            else {
            	request.setAttribute("msg","添加失败！");
            	request.getRequestDispatcher("AddMessage.jsp").forward(request,response);
            }
		}        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
