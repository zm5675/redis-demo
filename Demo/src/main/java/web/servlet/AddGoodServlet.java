package web.servlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import dao.PhotoDao;
import domain.GoodType;
import util.JDBCUtils;

@WebServlet("/AddGoodServlet")
public class AddGoodServlet extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
			//String urlString=request.getSession().getServletContext().getRealPath("")+"GoodPic";
			String urlString="F:\\毕业设计\\WorkPlace\\OnlineBookShop\\WebContent\\GoodPic";
			//String urlString="E:\\SpringBootAndMybatisPlus\\Demo\\src\\main\\webapp\\GoodPic";
			request.setCharacterEncoding("utf-8");
			//response.setContentType("text/html;charset=utf-8");
			JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
			List<String> list=new ArrayList<String>();
	        String filename=PhotoDao.getPhotoNewName();
	         ServletContext servletContext=null;
	        servletContext=request.getSession().getServletContext();
	        //数据库中存储格式:/webTest/imgs/***.jpg
	        //第一步:获取页面上上传的图片资源
	         List<FileItem> items=PhotoDao.getRequsetFileItems(request,servletContext);
	         boolean isLoadToSQL=false;
	         for(FileItem item:items) {
	             if(!item.isFormField()){
	                 //判断后缀名是否是jpg
	                 if(PhotoDao.isGif(item)) {
	                    isLoadToSQL=PhotoDao.saveFile(item,filename,urlString);
	                  }else {
	                     System.out.println("后缀格式有误，保存文件失败");
	                     request.setAttribute("msg","后缀格式有误，保存文件失败");
	 	             	request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);
	                }
	             }else { 
	                list.add(item.getString("UTF-8"));
	            }
	         }
	         //存在数据库里面的照片路径是在项目里的相对路径
	         String finalPhotoName= request.getContextPath()+"/GoodPic/"+filename;
	         
	         if(list.get(1)=="") {
	         	request.setAttribute("msg","图书名称！");
	             request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);
	         }
	         else if(list.get(2)=="") {
	         	request.setAttribute("msg","图书数量不能为空！");
	             request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);
	         }
	         else if(list.get(3)=="") {
		         	request.setAttribute("msg","图书单价不能为空！");
		             request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);
		     }
	         else {
	         	String sql = "insert into Good(GoodTypeID,GoodName,GoodCount,GoodUnitPrice,Content,GoodPicUrl,addDate) values('"+list.get(0)+"','"+list.get(1)+"','"+list.get(2)+"','"+list.get(3)+"','"+list.get(4)+"','"+finalPhotoName+"',now())";
				 System.out.println(sql);
	         	int renum=template.update(sql);
	             List<Object> params = new ArrayList<Object>();
	             sql = "select * from  GoodType";
				 //List<GoodType> list1 = template.queryForList(sql, GoodType.class);
				List<GoodType> list1 = template.query(sql,new BeanPropertyRowMapper<GoodType>(GoodType.class),params.toArray());

	             request.setAttribute("goodType",list1);
	             if(renum==1) {
	             	request.setAttribute("msg","添加成功！");
	             	request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);
	             }
	             else {
	             	request.setAttribute("msg","添加失败！");
	             	request.getRequestDispatcher("Admin/AddGood.jsp").forward(request,response);
	             }
	 		} 
   }
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	   doGet(request,response);
	}

}
