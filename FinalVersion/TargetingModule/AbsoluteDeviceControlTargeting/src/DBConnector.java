
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector
{
    
    
   public Connection getPooledConnection()
   {
	   try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   Connection conn = null;
	   try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lightning_test","root","");
	    
	   
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return conn;
    }   

}
 

