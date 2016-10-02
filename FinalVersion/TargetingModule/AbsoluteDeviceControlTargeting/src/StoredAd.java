/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. CLASS STORED AD OBJECT WITH ALL ITS INFORMATIONS
 *
 * @author 
 */
public class StoredAd 
{
    
    public StoredAd(String id,String adurl1,String adurl2,String cturl,String campname,String crid,String impurl,String c_width1,String c_height1,String c_width2,String c_height2,String backupurl1,String backupurl2,String c_hasbackup,String c_type,String adplc,int cp,String cat,String scp,String callbackURL,String sToken,int SvrID,int cmpid,int advId,int pubId,int chnlId,String ad_type,String ack_type) 
    {
        adId=id;
        
        clickThroughURL=cturl;
        //imprURL=impurl;
        imprURL  = impurl.replace( "&", "&amp;" );
        camp_name=campname;
        crId=crid;
        //camp_duration=campduration;
        camp_width1=c_width1;
        camp_height1=c_height1;
        camp_width2=c_width2;
        camp_height2=c_height2;
        backupURL1=backupurl1;
        backupURL2=backupurl2;
        hasBackup=c_hasbackup;
        camp_type=c_type;
        formatedclickThroughURL  = clickThroughURL;
        formatedclickThroughURL  = clickThroughURL.replace( "&", "&amp;" );
        long timestamp = java.util.Calendar.getInstance(java.util.Locale.US).getTimeInMillis();
        formatedclickThroughURL=formatedclickThroughURL.replace("[timestamp]",""+timestamp);
    
        formatedadURL1  = adurl1;
        formatedadURL2  = adurl2;
        cbURL=callbackURL;
        cpm=cp;
        adPlacement=adplc;
        category=cat;
        scope=scp;
        sTok=sToken;
        servID=SvrID;
        cmpdid=cmpid;
        advid=advId;
        pubid=pubId;
        chnlid=chnlId;
        adType=ad_type;
        ackType=Integer.parseInt(ack_type);
        //System.out.println("AD: clickurl: "+formatedclickThroughURL);
   }
    
    private String adId;
    
    private String camp_name;
    private String camp_detail;
    private String crId;
    private String camp_width1;
    private String camp_height1;
    private String camp_width2;
    private String camp_height2;
    private String backupURL1;
    private String backupURL2;
    private String hasBackup;
    private String camp_type;
    private int servID=0;
    private String clickThroughURL;
    private String formatedclickThroughURL;
    private String sTok;
    private int cpm;    
    private String adPlacement;
    private String category;
    private String scope;
    private String cbURL,imprURL;
    private String formatedadURL1,formatedadURL2;
    private int cmpdid=0;
    private int advid=0;
    private int pubid=0;
    private int chnlid=0;
    private String adType="";
    private int ackType=0;

    
    
    public String getAdPlacement(){      return adPlacement;            }    
    public String getAdId()       {      return adId;                   }   
    public String getAdURL1()      {      return formatedadURL1;          }   
    public String getAdURL2()      {      return formatedadURL2;          }    
    public String getClkThrURL()  {      return formatedclickThroughURL;}    
    public String getImprURL()  {      return imprURL;}    
    public String getCategory()   {      return category;               }    
    public String getScope()      {      return scope;                  }
    public String getCallBackURL(){      return cbURL;                  }
    public String getSToken()     {      return sTok;                   }
    
    public int getCmpId()         {      return cmpdid;                 }
    public int getAdvId()         {      return advid;                  }
    public int getPubId()         {      return pubid;                  }
    public int getchnlId()         {      return chnlid;                  }
    public String getAdType()        {      return adType;                 }
    public int getAckType()       {      return ackType;                }   
    public String getCampName()   {      return camp_name;              }   
    public String getCampDetail() {      return camp_detail;            } 
    public String getCrId() {      return crId;            }  
    public String getCampWidth1() {      return camp_width1;            }   
    public String getCampHeight1() {      return camp_height1;            }  
    public String getCampWidth2() {      return camp_width2;            }   
    public String getCampHeight2() {      return camp_height2;            }   
    public String hasBackUP() {      return hasBackup;            }  
    public String getBackupURL1() {      return backupURL1;            }  
    public String getBackupURL2() {      return backupURL2;            }   
    public String getCampType() {      return camp_type;            }   
}
