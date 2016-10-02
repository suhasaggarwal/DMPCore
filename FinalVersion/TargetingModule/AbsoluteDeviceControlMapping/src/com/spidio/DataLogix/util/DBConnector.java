package com.spidio.DataLogix.util;





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
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lightning_test","root","root123");
	    
	   
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return conn;
    }   

   public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con=null;
		
		DBConnector db = new DBConnector();
        con = db.getPooledConnection();
		if(con !=null)
			System.out.println("Connection Established");
	
   
   }
   
   
}
 

