/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. IT IS RESPONSIBLE TO RETURN THE AD USNING DBUTILITY CLASS
 *
 * @author 
 */
import java.sql.*;
import java.util.*;

public class GetSpidioAds
{
    public GetSpidioAds() {   }
    public List fetchAds(String crId,String campInfo[][],String pubId,String chnlId,String width,String height,String os,String browser)
    {
        //System.out.println("in GetSpidio.fetchad"+width +" ,"+height);
        GMadList = new ArrayList();
        GMpubId = pubId;
        GMchnlId= chnlId;
        GMcrId = crId;
        GMwidth=width;
        GMheight=height;
        GMadsReturned=getmads(campInfo,GMwidth,GMheight); 
        
        GMos=os;
        GMbrowser=browser;
        return GMadList;  
    }
    
    private int getmads(String ci[][],String w,String h)
    {
        DBUtility db = new DBUtility();
        
        try 
        {
            //System.out.println("Getting Ad for publisher id: "+GMpubId+","+GMchnlId);
            int pubid=Integer.parseInt(GMpubId);   
            GMads = db.getSpidioAds(ci,GMcrId,GMpubId,GMchnlId,w,h,GMos,GMbrowser);
            GMadsReturned = (db.numAds-1);
            
            //System.out.println("GMadsReturned: "+GMadsReturned);
            if(GMadsReturned<=0) 
            {               
               System.out.println("No ad found ");
            }
            else
            {  
                  for (int j = 0; j < GMadsReturned; j++) 
                  {
                        if(GMads[j][1]==null || GMads[j][1].equals("0")) continue;
                        String id = "" +(GMadId+1);
                        String callbackURL = "";
                        int cpm = 0;
                        String adPlcmnt = "";
                        String catg = "";
                        String scope = "";
                        int campid = Integer.parseInt(""+GMads[j][0]);
                        //int pubid = Integer.parseInt(""+GMads[j][10]);
                        int chnlid = Integer.parseInt(""+GMads[j][11]);
                        int svrid = 0;
                        int advid=Integer.parseInt(GMads[j][4]);                    
                        StoredAd ad = new StoredAd(id, GMads[j][2],GMads[j][17], GMads[j][1],GMads[j][9],GMads[j][3],GMads[j][18],GMads[j][6],GMads[j][7],GMads[j][14],GMads[j][15],GMads[j][12],GMads[j][13],GMads[j][16],GMads[j][8], adPlcmnt, cpm, catg, scope, callbackURL,GMads[j][0],svrid,campid,advid,pubid,chnlid,GMads[j][5],"0");
                        //System.out.println("arrayz :"+GMads[j][2]);
                        GMadList.add(ad);                    
                        ad = null;
                        GMadId++;
                        
                     }
                  
            }
        } catch (Exception e) {
            System.out.println("Error Getting  ads!!!!!!!");
            e.printStackTrace();
        } finally {
            if (db != null) {
                db = null;
            }
        }
        return GMadsReturned;
    }
    
    
    private List GMadList;
    int GMadId = 0;
    private String GMpubId = "",GMchnlId="",GMwidth="",GMheight="",GMos="",GMbrowser="";
    private String GMcrId="";    
    private String GMads[][]=new String[24][19];
    private int GMadsReturned=0;
    
}




