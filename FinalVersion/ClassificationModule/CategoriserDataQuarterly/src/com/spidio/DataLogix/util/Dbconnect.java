package com.spidio.DataLogix.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;





public class Dbconnect {

	
	
	  public static void main(String[] args)
	  {
		  ResultSet rs = null;
		  PreparedStatement cs=null;
		  Connection conn = null;
		  String url = "jdbc:sqlserver://10.157.210.20:1433;DatabaseName=cmscomments";
//		  String dbName = "cmscomments";
		  String driver = "com.mysql.jdbc.Driver";
		  String userName = "devuser1"; 
		  String password = "u$e4dev";
		
		  try {
		  Class.forName(driver).newInstance();
		  conn = DriverManager.getConnection(url,userName,password);
		  } catch (Exception e) {
			  e.printStackTrace();
			  } 
		  try {
		      cs=conn.prepareStatement("exec sp_getLiveBlogComments 153,12233121");
		      cs.setEscapeProcessing(true);
		      cs.setQueryTimeout(90);

		     
		      //commented, because no need to register parameters out!, I got results from the resultset. 
		      //cs.registerOutParameter(1, Types.VARCHAR);
		      //cs.registerOutParameter(2, Types.VARCHAR);

		      rs = cs.executeQuery();
		      
		      
		       } catch (SQLException se) {
		          System.out.println("Error al ejecutar SQL"+ se.getMessage());
		          se.printStackTrace();
		          throw new IllegalArgumentException("Error al ejecutar SQL: " + se.getMessage());

		      } 

		        finally{
		          try {

		              rs.close();
		              cs.close();
		              

		          } catch (SQLException ex) {
		              ex.printStackTrace();
		          }
		      }
	
		  }

	  }
