package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Good;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import util.JDBCUtils;

@WebServlet("/AddShopCartServlet")
public class AddShopCartServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String goodId = request.getParameter("GoodID");
        String sql1 = "select a.*,b.GoodTypeName from Good a inner join GoodType b on a.GoodTypeID=b.id where a.id = ?";
        Good good= template.queryForObject(sql1, new BeanPropertyRowMapper<Good>(Good.class), goodId);
        HttpSession session = request.getSession();
        int count = Integer.parseInt(request.getParameter("GoodNum"));
        System.out.println(good);
        System.out.println(count);

        if(request.getParameter("GoodNum").toString()=="") {
        	request.setAttribute("msg","订购数量不能为空！");
            request.getRequestDispatcher("GoodDetailServlet?id="+request.getParameter("GoodID")+"").forward(request,response);
        }
        else if(session.getAttribute("UserID")==null) {
        	request.setAttribute("msg","请先登录再订购！");
            request.getRequestDispatcher("UserLogin.jsp").forward(request,response);        	
        } else if ( count > Integer.parseInt(good.getGoodCount())){
            request.setAttribute("msg","订购数量不能大于最大的图书数量！");
            request.getRequestDispatcher("GoodDetailServlet?id="+request.getParameter("GoodID")+"").forward(request,response);
        }
        else {

        	Integer userId = (int)session.getAttribute("UserID");
        	String sql = "insert into ShopCart(UserID,GoodID,GoodNum,addDate) values('"+userId+"','"+request.getParameter("GoodID")+"','"+request.getParameter("GoodNum")+"',now())";
        	String sql3 = "update good set GoodCount = " + (Integer.parseInt(good.getGoodCount()) - count) + " where id = " + goodId;
            int renum=template.update(sql);
            int renum1=template.update(sql3);
            if(renum==1 && renum1 == 1) {

            	request.setAttribute("msg","订购成功！");
            	request.getRequestDispatcher("GoodDetailServlet?id="+request.getParameter("GoodID")+"").forward(request,response);
            }
            else {
            	request.setAttribute("msg","未知错误，订购失败！");
            	request.getRequestDispatcher("GoodDetailServlet?id="+request.getParameter("GoodID")+"").forward(request,response);
            }
		}        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
