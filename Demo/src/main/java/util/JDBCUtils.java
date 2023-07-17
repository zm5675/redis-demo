package util;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


public class JDBCUtils {
    public static DataSource getDataSource(){
    	DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/onlinebookshopdb");
        //dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }
}