package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import domain.Admin;
import domain.Message;
import domain.PageBean;
import util.JDBCUtils;

@WebServlet("/UserMessageManageServlet")
public class UserMessageManageServlet extends HttpServlet {
	private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        String isDel=request.getParameter("isDel");
        if(currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if(rows == null || "".equals(rows)){
            rows = "5";
        }
        Map<String, String[]> condition = request.getParameterMap();
        HttpSession session = request.getSession();
        String userId = session.getAttribute("UserID").toString();
        String sql = "select a.*,b.UserName from Message a join User b on a.UserID=b.id  where 1 = 1 and a.UserID='"+userId+"'";
        StringBuilder sb = new StringBuilder(sql);
        Set<String> keySet = condition.keySet();       
        List<Object> params = new ArrayList<Object>();
        if(isDel==null) {
        	for (String key : keySet) {
                if("currentPage".equals(key) || "rows".equals(key)){
                    continue;
                }
                String value = condition.get(key)[0];//
                if(key.toString()=="id") {
                	System.out.println("xxxx");
                }//ֵ
                if(value != null && !"".equals(value)){//ֵ
                    sb.append(" and "+key+" like ? ");
                    params.add("%"+value+"%");//ֵ
                }
            }
        }
        sb.append(" limit "+(Integer.parseInt(currentPage)-1)*Integer.parseInt(rows)+","+(Integer.parseInt(currentPage)) * Integer.parseInt(rows)+"");
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);       
        List<Message> list = template.query(sql,new BeanPropertyRowMapper<Message>(Message.class),params.toArray());
        PageBean<Message> pb = new PageBean<Message>();
        pb.setCurrentPage(Integer.parseInt(currentPage));
        pb.setRows(Integer.parseInt(rows));
        String sql1 = "select count(*) from Message a join User b on a.UserID=b.id where 1 = 1 ";
        StringBuilder sb1 = new StringBuilder(sql1);
        Set<String> keySet1 = condition.keySet();
        List<Object> params1 = new ArrayList<Object>();
        if(isDel==null) {
        	for (String key : keySet1) {
                if("currentPage".equals(key) || "rows".equals(key)){
                    continue;
                }
                String value = condition.get(key)[0];//ֵ
                if(value != null && !"".equals(value)&&key!="id"){//ֵ
                    sb1.append(" and "+key+" like ? ");//
                    params1.add("%"+value+"%");//ֵ
                }
            }
        }        
        int totalCount = template.queryForObject(sb1.toString(),Integer.class,params1.toArray());
        pb.setTotalCount(totalCount);
        System.out.println(pb);
        pb.setList(list);
        int totalPage = (totalCount % Integer.parseInt(rows))  == 0 ? totalCount/Integer.parseInt(rows) : (totalCount/Integer.parseInt(rows)) + 1;
        pb.setTotalPage(totalPage);
        request.setAttribute("pb",pb);
        request.setAttribute("condition",condition);
        request.getRequestDispatcher("UserMessageManage.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}
