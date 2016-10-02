package com.spidio.segmentTargetingServer;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. DATABASE TRANSACTION
 *
 * @author RAJ
 */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DBUtility 
{
    int numFillers = 0;
    static int tCounter = 0;
    int numAds = 0;
    String dbname="lyxel_AD_Server";

    
    public void registerAdrequest(String ck,String ip,String os,String screen,String timezone,String canvas,String browser,String activex,String refUrl,String font,String UA,String chnlId,String pubId,String city,String state,String country) throws SQLException 
     {
        //System.out.println(" db ck:"+ck); 
        String myip=ip;
        String fingerpid="";
        if(ip.length()>29)
        {
         myip=ip.substring(0,29);
        }
        else { 
            if(ip==null){
            myip=""; }
        }    
        if(refUrl.length()>254)
        {
          refUrl=refUrl.substring(0,253);
        }
        else
        {
          if(refUrl==null){
          refUrl="";
          }
        }    
        if(ck.length()>50)  
        {
          ck=ck.substring(0,49);
        } 
        else
        {
          if(ck==null){
          ck="";
          }
        }    
        if(UA.length()>254)  
        {
          UA=UA.substring(0,29);
        } 
        else
        {
          if(UA==null){
          UA="";
          }
        }    
           String SQL="INSERT INTO `visitlog`(`visit_id`,`cookie_id`,`fingerp_id`,`ip`,`system_os`,`browser_name`,`referrer`,`br_user_agent`,`publisher_id`,`channel_id`,`city`,`state`,`country`) VALUES"
                       + "(NULL,'"+ck+"','"+fingerpid+"','"+myip+"','"+os+"','"+browser+"','"+refUrl+"','"+UA+"','"+pubId+"','"+chnlId+"','"+city+"','"+state+"','"+country+"')";    
              
        Statement INSERTSTMT=null;
        Connection CON = null;        
        DBConnector DB = new DBConnector();        
        try 
        {
            CON = DB.getPooledConnection();            
            INSERTSTMT = CON.createStatement();
            INSERTSTMT.execute(SQL); //REGISTER registerAdrequest 
            System.out.println("registerAdrequest INSERTSQL: "+SQL);
            System.out.println(INSERTSTMT);
        } 
	catch (Exception e) 
	{
            try{ 
                if(CON!=null) { CON.rollback(); }
            
            }
            catch(Exception ee){
            
            }
       //     e.printStackTrace(); 
            System.out.println("Exception in Tracking request : "+e);
        } 
        finally 
        {
            if (INSERTSTMT != null)
            {
                INSERTSTMT.close();
                INSERTSTMT = null;
            }
            if (CON != null) 
            {
                if (!CON.isClosed()) {
                    CON.close();
                }
                CON = null;
            }
            if (DB != null) 
            {
                 DB = null;
            }
        }
      }  
    
    
    
     //below function is used in Adserved.java for log all the ad sent as response    
     public void registerAdserved(String adservedSQL) throws SQLException 
     {
        Statement INSERTSTMT=null;
        Connection CON = null;        
        DBConnector DB = new DBConnector();        
        try 
        {
            CON = DB.getPooledConnection();            
            INSERTSTMT = CON.createStatement();
            INSERTSTMT.execute(adservedSQL); //REGISTER registerAdserved 
            //System.out.println("registerAdserved INSERTSQL: "+adsentSQL);
        } 
	    catch (Exception e) 
	    {
          //  e.printStackTrace();            
            System.out.println("Exception in "+adservedSQL);
        } 
        finally 
        {
            if (INSERTSTMT != null)
            {
                INSERTSTMT.close();
                INSERTSTMT = null;
            }
            if (CON != null) 
            {
                if (!CON.isClosed()) {
                    CON.close();
                }
                CON = null;
            }
            if (DB != null) 
            {
                 DB = null;
            }
        }
      } 
     //below function is used in XMLGenerator3rdServer.java for log all the ad sent as response    
     public void register3SAdserved(String adservedSQL) throws SQLException 
     {
        Statement INSERTSTMT=null;
        Connection CON = null;        
        DBConnector DB = new DBConnector();        
        try 
        {
            CON = DB.getPooledConnection();            
            INSERTSTMT = CON.createStatement();
            INSERTSTMT.execute(adservedSQL); //REGISTER adservedSQL for other server 
            //System.out.println("adservedSQL INSERTSQL: "+adservedSQL);
        } 
	    catch (Exception e) 
	    {
            //e.printStackTrace();            
            //System.out.println("Exception in 3rd server adservedSQL: "+adservedSQL);
        } 
        finally 
        {
            if (INSERTSTMT != null)
            {
                INSERTSTMT.close();
                INSERTSTMT = null;
            }
            if (CON != null) 
            {
                if (!CON.isClosed()) {
                    CON.close();
                }
                CON = null;
            }
            if (DB != null) 
            {
                 DB = null;
            }
        }
      } 
     
     //below function is used in Adplayed.java
     public void registerTrackings(String uuid,int adid,int pubid,int chnlid,int cmpid, int advid, int crid,String ipaddress, int tracking) throws SQLException 
     {
        Statement INSERTSTMT1=null;
        Connection CON = null;        
        DBConnector DB = new DBConnector();
        String INSERTSQL = "INSERT INTO `campaign_event_tracking`(`uuid`,`adid`,`publisher_id`,`channel_id`,`campaign_id`,`advertiser_id`,`creative_id`,`ip`,`tracking_event`) values('"+uuid+"',"+adid+","+pubid+","+chnlid+","+cmpid+","+advid+","+crid+",'"+ipaddress+"','"+tracking+"')";
        System.out.println(INSERTSQL);
        
        try 
        {
            CON = DB.getPooledConnection();            
            INSERTSTMT1 = CON.createStatement();
            INSERTSTMT1.execute(INSERTSQL); //REGISTER CLICKS/IMPRESSION 
            System.out.println("CLICKS/IMPRESSION INSERTSQL"+INSERTSQL);
        } 
	catch (Exception e) 
	{
         //   e.printStackTrace();            
            System.out.println("Exception in Tracking INSERTSQL : "+INSERTSQL);
        } 
        finally 
        {
            if (INSERTSTMT1 != null)
            {
                INSERTSTMT1.close();
                INSERTSTMT1 = null;
            }
            if (CON != null) 
            {
                if (!CON.isClosed()) {
                    CON.close();
                }
                CON = null;
            }
            if (DB != null) 
            {
                 DB = null;
            }
        }
      }  
     
     
    //used in getSpidioAds in this claas
    public int getCreativeId(String adURL,int campid) throws SQLException 
    {
        Statement st = null;
        Connection con = null;       
        ResultSet rs = null;
        DBConnector db = new DBConnector();
        int URLID = 0;
        
        String sql="";
        sql = "SELECT `id` FROM `creative_detail` WHERE `campaign_id`="+campid+" AND `creative_url1`='"+adURL+"'";
        
        try 
        {
            con = db.getPooledConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next())
            {
                URLID = rs.getInt(1);
            }
        //con.close();
        } catch (Exception e) {
            //System.out.println("Error in getAdURL");
            //System.out.println(sql);
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (st != null) {
                st.close();
                st = null;
            }
            if (con != null) {
                if (!con.isClosed()) {
                    con.close();
                }
                con = null;
            }
            if (db != null) {
                db = null;
            }
        }
        return URLID;
    }     
    //This function is used in getSpidioAds.java To get Spidio ads
    public String[][] getSpidioAds(String camps[][],String crID,String pubid,String chnlid,String width,String height,String os,String browser)
    {
        //System.out.println("Running the getAds function "+width+"   ,"+height);
        Statement stAd = null, stAd_FB = null, st_random_ad = null;
        Connection con = null;
        ResultSet rsAds = null, rsAds_FB = null, rs_random_ad = null;
        DBConnector db = new DBConnector();
        int adcounter = 0;
        String creativeid = "0";
        String campaignid = "0";       
        String advid = "0";
        String ad_type = "";
        String adUrl1 = "",adUrl2="";
        String clkThrUrl = "",imprUrl="";
        String camp_width1="",camp_width2="";
        String camp_height1="",camp_height2="",hasbackup="";
        String camp_type="";
        String backup_url1="";
        String backup_url2="";
        String currentdate = "";
        Date ctdt = null;
        String myAd[][] = new String[camps.length][19];
        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        int priorityset = 0;
        try 
        {
            String host="52.4.92.26";//"10.12.3.125";      //from server (private ip)
            Class.forName("com.mysql.jdbc.Driver").newInstance();          
            con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/lyxel_AD_Server", "wifi_spd_user", "aeiou20142015");         //connect to localhost
	    //con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/lyxel_AD_Server", "adserver", "q1w2e3r4t5y6");   //connect to server
            
          
            //con = db.getPooledConnection();
            stAd = con.createStatement();
            stAd_FB = con.createStatement();
            st_random_ad = con.createStatement();
            
            for (int xy = 0; xy < camps.length; xy++) 
            {  
                //System.out.println("bbbbbbbbbbb"+camps[xy][1]);
                if (camps[xy][4] != null) 
                {
                    String SQL = "SELECT (SELECT NOW()) as ctime,`status` FROM `"+dbname+"`.`campaign_publisher-channel_target` WHERE `publisher_id`='" + pubid + "' AND `channel_id`='" + chnlid + "' AND `campaign_id`=" + camps[xy][1] + "";
                    //System.out.println("SQL: "+SQL);
                    rsAds = stAd.executeQuery(SQL);
                    String campStatus ="0";
                    String camp_duration="00:00:30";
                    if (rsAds.next()) 
                    {
                        campStatus = rsAds.getString("status");
                        currentdate = rsAds.getString("ctime");
                        String datetime1[] = new String[2];
                        String datetime2[] = new String[2];
                        datetime1 = camps[xy][8].split(" ");
                        datetime2 = camps[xy][9].split(" ");

                        Date stdt = df.parse(datetime1[0] + " " + camps[xy][10]);
                        Date etdt = df.parse(datetime2[0] + " " + camps[xy][11]);
                        ctdt = df.parse(currentdate);
                       //System.out.println("ctime: "+ctdt+"startdate :"+stdt+" enddate : "+etdt);
                        if (stdt.before(ctdt) && etdt.after(ctdt) && campStatus.equals("1")) {
                            //System.out.println("condition satisfied");
                            adcounter++;
                            campaignid = camps[xy][1];
                            
                            String random_ad_qry="";
                            if(crID==null || crID.equals(""))
                            {
                              //random_ad_qry = "SELECT `id`,`creative_url`,`click_url`,`width`,`height` FROM `"+dbname+"`.`creative_detail` WHERE campaign_id='" + campaignid + "' ORDER BY RAND() LIMIT 1";
                              random_ad_qry = "SELECT * FROM `"+dbname+"`.`creative_detail` WHERE campaign_id='" + campaignid + "' AND `width1`='"+width+"' AND `height1`='"+height+"' ORDER BY RAND() LIMIT 1";
                              //System.out.println("last creative served is(null) :"+crID);
                            
                            }
                            else
                            {
                              String fcrId=crToCheck(crID);  
                              //random_ad_qry = "SELECT `id`,`ad_type`,`creative_url`,`click_url`,`width`,`height`,`backup_url1`,`backup_url2` FROM `"+dbname+"`.`creative_detail` WHERE campaign_id='" + campaignid + "' "+fcrId+" ORDER BY RAND() LIMIT 1";
                              random_ad_qry = "SELECT * FROM `"+dbname+"`.`creative_detail` WHERE `width1`='"+width+"' AND `height1`='"+height+"' AND campaign_id='" + campaignid + "' "+fcrId+" ORDER BY RAND() LIMIT 1";                              //System.out.println("last creative serverd is not null :"+crID);
                            }
                            //System.out.println("creative compare sql :"+random_ad_qry);
                            rs_random_ad = st_random_ad.executeQuery(random_ad_qry);
                            int totalCreative=0;
                            
                            while (rs_random_ad.next()) {
                                totalCreative++;
                                if(rs_random_ad.getString("ad_type").equals("99"))
                                {
                                 adUrl1 = rs_random_ad.getString("external_tag");
                                } 
                                else{
                                adUrl1 = rs_random_ad.getString("creative_url1");
                                }
                                adUrl2 = rs_random_ad.getString("creative_url2");
                                
                                clkThrUrl=rs_random_ad.getString("click_url");
                                imprUrl=rs_random_ad.getString("impression_url");
                                creativeid=rs_random_ad.getString("id");
                                camp_width1=rs_random_ad.getString("width1");
                                camp_height1=rs_random_ad.getString("height1");
                                camp_width2=rs_random_ad.getString("width2");
                                camp_height2=rs_random_ad.getString("height2");
                                backup_url1=rs_random_ad.getString("backup_url1");
                                backup_url2=rs_random_ad.getString("backup_url2");
                                ad_type=rs_random_ad.getString("ad_type");     //flash,image
                                hasbackup=rs_random_ad.getString("hasbackup");   
                                advid=camps[xy][6];
                            }
                            
                            myAd[adcounter - 1][0] = campaignid;
                            myAd[adcounter - 1][1] = clkThrUrl;
                            myAd[adcounter - 1][2] = adUrl1;
                            myAd[adcounter - 1][3] = creativeid;
                            myAd[adcounter - 1][4] = advid;
                            myAd[adcounter - 1][5] = ad_type;
                            myAd[adcounter - 1][6] = camp_width1;
                            myAd[adcounter - 1][7] = camp_height1;
                            myAd[adcounter - 1][8] = camp_type;
                            myAd[adcounter - 1][9] = camps[xy][2];//campaign_name
                            myAd[adcounter - 1][10] = camps[xy][0];  //pubid
                            myAd[adcounter - 1][11] = camps[xy][4];   //chnlid
                            myAd[adcounter - 1][12] = backup_url1;
                            myAd[adcounter - 1][13] = backup_url2;
                            myAd[adcounter - 1][14] = camp_width2;
                            myAd[adcounter - 1][15] = camp_height2;
                            myAd[adcounter - 1][16] = hasbackup;
                            myAd[adcounter - 1][17] = adUrl2;
                            myAd[adcounter - 1][18]=imprUrl;
                           
                           /* 
                           System.out.println("campaign id :"+myAd[adcounter - 1][0]);
                           System.out.println("click url :"+myAd[adcounter - 1][1]);
                           
                           System.out.println("ad url :"+myAd[adcounter - 1][2]);
                           System.out.println("creativeid :"+myAd[adcounter - 1][3]);
                           System.out.println("advid :"+myAd[adcounter - 1][4]);
                           System.out.println("ad type : "+myAd[adcounter - 1][5]);
                           System.out.println("camp width1 :"+myAd[adcounter - 1][6]);
                           System.out.println("camp height1 :"+myAd[adcounter - 1][7]);
                           System.out.println("camp width2 :"+myAd[adcounter - 1][14]);
                           System.out.println("camp height2  :"+myAd[adcounter - 1][15]);
                           System.out.println("bckup url1  :"+myAd[adcounter - 1][12]);
                           System.out.println("bckup url2  :"+myAd[adcounter - 1][13]);
                           System.out.println("camp type:"+myAd[adcounter - 1][8]);
                           */
                          adcounter++; 
                        }
                        
                    }
                }
                
                numAds = adcounter;
                
            }
            
    
        } catch (Exception e) {
            System.out.println("error in getSpidioAds -exception");
    //    e.printStackTrace();       
        } finally {
            try {
                if (rsAds != null) {
                    rsAds.close();
                    rsAds = null;
                }
                if (stAd != null) {
                    stAd.close();
                    stAd = null;
                }
                if (rsAds_FB != null) {
                    rsAds_FB.close();
                    rsAds_FB = null;
                }
                if (stAd_FB != null) {
                    stAd_FB.close();
                    stAd_FB = null;
                }
                if (!con.isClosed()) {
                    con.close();
                    con = null;
                }
            } catch (Exception e) {
              //  e.printStackTrace();
            }
        }

        return myAd;
    }
    
    
    public String crToCheck(String crData) throws SQLException 
    {
        
       String data=""; 
       String[] splittArray = null;
       if (crData != null || !crData.equalsIgnoreCase("")){
         splittArray = crData.split(",");
         //System.out.println(crData + " " + splittArray);
        }
        Statement st = null;
        Connection con = null;        
        ResultSet rs = null;
        DBConnector db = new DBConnector();
        con = db.getPooledConnection();
        st = con.createStatement();
          
        try 
        {
        
        for(int i=0;i<splittArray.length;i++)
        {    
        String crid=splittArray[i].trim();
        String sql = "SELECT `enable_rotation` FROM `"+dbname+"`.`creative_detail` WHERE `id`="+crid;
        //System.out.println("sql for creative value "+sql);
            rs = st.executeQuery(sql);
            while (rs.next()) {
                if(rs.getString("enable_rotation").equals("1"))
                {
                   data=data+"";
                }
                else
                {
                  data=data+" AND `ID`!='"+crid+"' ";
                }    
            }

        }
        } catch (Exception e) {
            //System.out.println("Error in get repeat creative value");
            //e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (st != null) {
                st.close();
                st = null;
            }
            if (con != null) {
                if (!con.isClosed()) {
                    con.close();
                }
                con = null;
            }
            if (db != null) {
                db = null;
            }
        }
        
        //System.out.println("data for creative value "+data);
        return data;
    }
    
   
}
   

