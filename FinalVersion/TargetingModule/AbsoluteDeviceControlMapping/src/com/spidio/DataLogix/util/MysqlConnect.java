package com.spidio.DataLogix.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConnect{
  public static void main(String[] args) {
  System.out.println("MySQL Connect Example.");
  Connection conn = null;
  String url = "jdbc:sqlserver://10.157.210.20:3306/";
  String dbName = "cmscomments";
  String driver = "com.mysql.jdbc.Driver";
  String userName = "devuser1"; 
  String password = "u$e4dev";
  try {
  Class.forName(driver).newInstance();
  conn = DriverManager.getConnection(url+dbName,userName,password);
  System.out.println("Connected to the database");
  conn.close();
  System.out.println("Disconnected from database");
  } catch (Exception e) {
  e.printStackTrace();
  }
  }
}