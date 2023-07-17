package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Good;
import domain.ShopCart;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import util.JDBCUtils;

@WebServlet("/DelShopCartServlet")
public class DelShopCartServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String sql1 = "select * from ShopCart where id = ?";
		ShopCart shopCart= template.queryForObject(sql1, new BeanPropertyRowMapper<ShopCart>(ShopCart.class), id);
		String goodNum = shopCart.getGoodNum();
		// 将购物车中的数量添加到图书中
		String sql3 = "update good set GoodCount = GoodCount + " + goodNum + " where id = " + shopCart.getGoodID() ;
		String sql = "DELETE FROM ShopCart WHERE id=?;";
		int j = template.update(sql3);
		System.out.println(j);

		int i = template.update(sql, id);
		request.setAttribute("isDel", "1");
		request.getRequestDispatcher("/UserShopCartManageServlet?isDel=1").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
