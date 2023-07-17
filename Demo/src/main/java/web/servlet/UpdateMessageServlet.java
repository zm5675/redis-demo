package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.Admin;
import domain.Message;
import util.JDBCUtils;

@WebServlet("/UpdateMessageServlet")
public class UpdateMessageServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        Message message = new Message();
        try {
            BeanUtils.populate(message,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String sql = "update Message set Content1=?,replyDate=now() where id=?";
        int renum= template.update(sql,request.getParameter("Content1"),message.getId());
        if(renum==1) {
            sql = "select * from Message where id = ?";
            message= template.queryForObject(sql, new BeanPropertyRowMapper<Message>(Message.class), message.getId());
            request.setAttribute("Message",message);
        	request.setAttribute("msg","回复成功"); 
        	request.getRequestDispatcher("/EditMessageServlet?id="+message.getId()+"").forward(request,response);
        }
        else {
        	request.setAttribute("msg","回复失败"); 
        	request.getRequestDispatcher("/EditMessageServlet?id="+message.getId()+"").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
