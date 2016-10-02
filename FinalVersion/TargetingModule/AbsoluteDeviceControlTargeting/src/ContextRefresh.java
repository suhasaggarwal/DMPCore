/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. REFRESHING THE VALUES IN THE CONTEXT WHEN INFORMATION HAS MODIFIED
 *
 * @author 
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextRefresh extends HttpServlet {

    public void init(ServletConfig sconf)
    {
         sc = sconf.getServletContext();
         
    }
    
     ServletContext sc;   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        System.out.print(" Calling the reload context method.."); 
        
        
        
        System.out.println("The ApidioAD Servering Platform is started and intialize At: "+ new java.util.Date());
        
     
        String[] siteList;
        //sc = sconf.getServletContext();
        DBConnector db = new DBConnector();
        Connection con = null;
        Statement st = null, st1 = null, st2=null, st3=null,st4=null;
        ResultSet rs = null, rs1 = null, rs2 =null, rs3=null,rs4=null;
        
        
        try 
        {
            String host="localhost";
            Class.forName("com.mysql.jdbc.Driver").newInstance();          
            con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/lightning_test", "root", "");
            st = con.createStatement();
            //=================getting all active publisherlist and save it in context
            String sql = "SELECT `publisher_id` FROM `publisher_detail` WHERE `status`='1'";
            System.out.println("Servlet initialization query #1: " + sql);
            rs = st.executeQuery(sql);
            rs.last();
            int rcount = rs.getRow();
            int count = 0;
            siteList = new String[rcount];
            rs.beforeFirst();
            while (rs.next())
            {
                siteList[count] = rs.getString("publisher_id");
                count++;
            }
            sc.setAttribute("siteList", siteList);            
            //================================================================Saved publisher list
            
            //Campaign mapping detail=======================
            String campinfo[][];            
            String sqlquery="SELECT cpbd.`campaign_id`,cpbd.`publisher_id`,cpbd.`channel_id`,C.`campaign_name`,C.`campaign_type`, cpbd.`cap`, C.`geo_targeted`, cpbd.`cpm-cpc`, C.`geo_target_type`, cpbd.`campaign_start_date`, cpbd.`campaign_end_date`, cpbd.`start_time`, cpbd.`end_time`, C.`advertiser_id` FROM `campaign_detail` C, `campaign_publisher-channel_target` cpbd,`creative_detail` ad WHERE C.`campaign_id`=cpbd.`campaign_id` AND cpbd.`status`='1' AND ad.`status`=1 GROUP BY cpbd.`publisher_id`,cpbd.`campaign_id` ORDER BY cpbd.`publisher_id`";
            //String sqlquery="SELECT cpbd.`campaign_id`,cpbd.`publisher_id`,cpbd.`channel_id`,C.`campaign_name`,C.`campaign_type`, cpbd.`cap`, C.`geo_targeted`, cpbd.`cpm-cpc`, C.`geo_target_type`, cpbd.`campaign_start_date`, cpbd.`campaign_end_date`, cpbd.`start_time`, cpbd.`end_time`, C.`advertiser_id` FROM `campaign_detail` C, `campaign_publisher-channel_target` cpbd,`creative_detail` ad WHERE C.`campaign_id`=cpbd.`campaign_id` AND cpbd.`status`='1' AND cpbd.`campaign_start_date`<=NOW() AND cpbd.`campaign_end_date`>=NOW() AND ad.`campaign_id`=C.`campaign_id` AND ad.`status`=1 GROUP BY cpbd.`publisher_id`,cpbd.`campaign_id` ORDER BY cpbd.`publisher_id`";
            System.out.println("Servlet initialization query #2: " + sqlquery);
            st2 = con.createStatement();
            st3 = con.createStatement();
            st4 = con.createStatement();
            rs2 = st2.executeQuery(sqlquery);
            rs2.last();
            
            campinfo = new String[rs2.getRow()][15];
            rs2.beforeFirst();
            int cnter = 0;
            while (rs2.next()) 
            {
                int cmpid=rs2.getInt("campaign_id");                
                campinfo[cnter][0] = rs2.getString("publisher_id");                
                campinfo[cnter][1] = ""+cmpid; //rs2.getString("campaign_id");
                campinfo[cnter][2] = rs2.getString("campaign_name");                
                campinfo[cnter][3] = rs2.getString("geo_targeted");
                campinfo[cnter][4] = rs2.getString("channel_id");                 
                campinfo[cnter][5] = ""+rs2.getFloat("cpm-cpc");
                campinfo[cnter][6] = rs2.getString("advertiser_id");
                
                String ccsql="SELECT * FROM `campaign_country` WHERE `campaign_id`="+cmpid;
                rs3 = st3.executeQuery(ccsql);
                campinfo[cnter][12] = "";
                campinfo[cnter][13] = "";                
                campinfo[cnter][14] = "";
                while (rs3.next()) 
                {
                    campinfo[cnter][12] = rs3.getString("country_code")+"="+rs3.getString("state")+"="+rs3.getString("city")+"||"+campinfo[cnter][12]; 
                    campinfo[cnter][13] =rs3.getString("country_name")+"="+rs3.getString("state_name")+"="+rs3.getString("city")+"||"+campinfo[cnter][13]; 
                }
               //System.out.print(" mob target "+campinfo[cnter][21]); 
                
                
                campinfo[cnter][7] = ""+rs2.getInt("geo_target_type");
                campinfo[cnter][8] = ""+rs2.getString("campaign_start_date");
                campinfo[cnter][9] = ""+rs2.getString("campaign_end_date");
                campinfo[cnter][10] = ""+rs2.getString("start_time");
                campinfo[cnter][11] = ""+rs2.getString("end_time");
                
                cnter++;
            }
            sc.setAttribute("campinfo", campinfo);
            
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println("Error in Getting Site List/mapping List");
        } 
        finally 
        {
            if (rs != null) 
            {
                try 
                {
                    rs.close();
                } catch (SQLException sq) {  }
                rs = null;
            }
            if (rs1 != null)
            {
                try
                {
                    rs1.close();
                } catch (SQLException sq) {  }
                rs1 = null;
            }
            if (rs2 != null)
            {
                try 
                {
                    rs2.close();
                } catch (SQLException sq) {  }
                rs2 = null;
            }
            if (rs3 != null)
            {
                try 
                {
                    rs3.close();
                } catch (SQLException sq) {  }
                rs3 = null;
            }
            if (rs4 != null)
            {
                try 
                {
                    rs4.close();
                } catch (SQLException sq) {  }
                rs4 = null;
            }
           
            if (st != null)
            {
                try 
                {
                    st.close();
                } catch (SQLException sq) {  }
                st = null;
            }
            if (st1 != null) 
            {
                try 
                {
                    st1.close();
                } catch (SQLException sq) {  }
                st1 = null;
            }
             if (st2 != null) 
             {
                try 
                {
                    st2.close();
                } catch (SQLException sq) {  }
                st2 = null;
            }
             if (st3 != null) 
             {
                try 
                {
                    st3.close();
                } catch (SQLException sq) {  }
                st3 = null;
            }
             if (st4 != null) 
             {
                try 
                {
                    st4.close();
                } catch (SQLException sq) {  }
                st4 = null;
            }

            if (con != null) 
            {
                try 
                {
                    if (!con.isClosed()) {    con.close();   }
                } catch (SQLException sq) { }
                con = null;
            }
            if (db != null) 
            {
                db = null;
            }
            siteList = null;
        }
    

    }

    
    //private String allcampinfo[][]=null;
    //private String mycampinfo[][]=null;// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
