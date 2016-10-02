package com.spidio.segmentTargetingServer;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class DBConnector
{
    
    
   public Connection getPooledConnection()
   {
       try
       {
          Context initCtx = new InitialContext();
          if (initCtx == null ) { throw new Exception("Boom - No Context"); }
          Context envCtx = (Context) initCtx.lookup("java:comp/env");
          DataSource ds = (DataSource) envCtx.lookup("jdbc/eros");
          if (ds == null ) { throw new Exception("Boom - No DataSource"); }  
          return ds.getConnection();          
        }
       catch(Exception e)
       {
          //e.printStackTrace();
          throw new RuntimeException("Database Not available");
       }     
      
    }   

}
 

