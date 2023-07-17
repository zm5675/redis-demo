package web.servlet;

import java.awt.print.Book;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.ShopCart;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.CreateOrder;
import domain.GoodType;
import util.JDBCUtils;

@WebServlet("/CreateOrderServlet")
public class CreateOrderServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String timestamp = String.valueOf(new Date().getTime());
        System.out.println(timestamp);
        HttpSession session = request.getSession();
        String userId = session.getAttribute("UserID").toString();
        String queryCartSql = "select s.id,g.GoodUnitPrice FROM good as g RIGHT JOIN shopcart as s  on g.id = s.GoodID WHERE s.OrderID is NULL and s.UserID = " + userId;
        List<ShopCart> query = template.query(queryCartSql, new BeanPropertyRowMapper<ShopCart>(ShopCart.class));
        Double totalPrice = 0.0;

        for (ShopCart shopCart : query) {
            totalPrice += Double.parseDouble(shopCart.getGoodUnitPrice());
        }

        if(request.getParameter("totalCount")=="0") {
        	request.setAttribute("msg","生成失败，请选购图书！");
            request.getRequestDispatcher("UserShopCartManageServlet").forward(request,response);        	
        }
        else {
        	String str="select SUM(cast(b.GoodUnitPrice as decimal(10,2))) TotalPrice from ShopCart a inner join Good b on a.GoodID=b.id where a.UserID=?";
            CreateOrder createOrder= template.queryForObject(str, new BeanPropertyRowMapper<CreateOrder>(CreateOrder.class), userId);
        	createOrder.setTotalPrice(totalPrice.doubleValue());
            String sql = "insert into Orders(OrderID,UserID,addDate,TotalPrice,OrderState) values('"+timestamp+"','"+userId+"',now(),'"+createOrder.getTotalPrice()+"','未结算')";
            int renum=template.update(sql);
            sql = "Update ShopCart set OrderID='"+timestamp+"' where UserID='"+userId+"' and OrderID is null";
            template.update(sql);
            //int renum=1;
            if(renum==1) {
               request.setAttribute("msg","添加成功！");
               request.getRequestDispatcher("UserShopCartManageServlet").forward(request,response);
            }
            else {
               request.setAttribute("msg","添加失败！");
               request.getRequestDispatcher("UserShopCartManageServlet").forward(request,response);
            }
        }        		     
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
