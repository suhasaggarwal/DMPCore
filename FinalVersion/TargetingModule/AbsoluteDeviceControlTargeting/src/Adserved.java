/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. SAVING THE DATA FOR AD JUST BEEN SERVED USING DBUTILITY METHOD.
 * 
 *
 * @author 
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;




public class Adserved
{
    public Adserved(List lstt,String clntid,String cntid,String refurl,String OS,String browserInfo,String deviceInfo,String uud,String ipadd,String city,String state,String country)
    {
       lst=lstt;       
       clientId=clntid;
       contId=cntid;
       ref_url=refurl;
       browserinfo=browserInfo;
       os=OS;
       deviceinfo=deviceInfo;
       uuid=uud; 
       ip =ipadd;
       ct=city;
       est=state;
       cnt=country;
     }
        
    public void sent() throws SQLException
    {
        Iterator it = lst.iterator();
        DBConnector db = new DBConnector();
        Connection con=null;
        Statement st=null;
        String sql="";
        
        try
        {
            int adid=0;
            StoredAd ad;
            //To Insert in DB
            con = db.getPooledConnection();
            st=con.createStatement();           
            con.setAutoCommit(false);           
            
            while(it.hasNext()) 
            {
               adid++;
               ad=(StoredAd)it.next(); 
               sql="INSERT INTO `adserved`(`publisher_id`,`channel_id`,`advertiser_id`,`campaign_id`,`creative_id`,`city`,`state`,`country`,`ref_url`,`uuid`,`adid`,`adurl`,`clickurl`,`callbackurl`,`ip`,`os`,`browser_info`,`device_info`) VALUES"
                       + "("+clientId+","+ad.getchnlId()+",'"+ad.getAdvId()+"','"+ad.getCmpId()+"','"+ad.getCrId()+"','"+ct+"','"+est+"','"+cnt+"','"+ref_url+"','"+uuid+"','"+adid+"','','"+ad.getClkThrURL()+"','"+ad.getCallBackURL()+"','"+ip+"','"+os+"','"+browserinfo+"','"+deviceinfo+"')";    
                //System.out.print("adserved sql"+sql);
               
               st.addBatch(sql); 
               DBUtility dbu=new DBUtility();
               
               if(dbu!=null) dbu=null;               
           
             }
             int recup[]=st.executeBatch();
             con.commit();
             ad=null;
            
         }
        catch(Exception e)
        {
             //e.printStackTrace();
              System.out.print("exception in adserving sql"+sql);
         } 
        finally
        {
            con.rollback();
             if(st!=null)
             {
                 st.close();
             }           
                 st=null;
             if(!con.isClosed())
             {
                 con.close();
             }
                 con=null;               
             if(db!=null)  
             {
                 
                 db=null;
             }
                 
        }
    }
    
  
    

    private List lst;
    private String clientId;
    private String contId;
    private String ref_url,os,browserinfo,deviceinfo;
    private String uuid;
    private String ip;
    private String ct;
    private String est;
    private String cnt;
 }
