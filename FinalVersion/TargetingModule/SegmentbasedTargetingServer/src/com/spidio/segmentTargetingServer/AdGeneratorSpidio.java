package com.spidio.segmentTargetingServer;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. GENERATE AD SCRIPT AND RETURN TO CALLING CLASS 
 * 
 *
 * @author RAJ
 */

import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class AdGeneratorSpidio 
{
    private String ct;
    private String st;
    private String cnt;
    private String clntid;
    private String contId;
    private String refURL,OS,browserInfo,deviceInfo;
    private String ipadd;    
    public AdGeneratorSpidio() {   }
    public String generateAdResponse(List lst,int errflg,String serivceURL,String pubid,String content,String refurl,String os,String browserinfo,String deviceinfo,String ip,String city,String state,String country,boolean flashMode)
    {
        String Response="";
        
        Iterator it;
        UUID x=null;
        ct=city;
        st=state;
        cnt=country;
        clntid=pubid;
        contId=content;
        refURL=refurl;
        OS=os;
        browserInfo=browserinfo;
        deviceInfo=deviceinfo;
        ipadd=ip;
        //System.out.println("in AdGenerator");
        String uuid = x.randomUUID().toString();
        Collections.shuffle(lst);
        it = lst.iterator();
        int newadid=0;        
        int adserved=0;
        DBUtility db = new DBUtility();
        
        try 
        {
            StoredAd ad;
            Adserved adS = new Adserved(lst,clntid,contId,refURL,OS,browserInfo,deviceInfo,uuid,ipadd,ct,st,cnt);
            adS.sent();
            long CHACHEBUSTER = Calendar.getInstance(Locale.US).getTimeInMillis();
            
             
             if(errflg==0 && it.hasNext())
             {
               while(it.hasNext() && (adserved==0)) 
               {
                  ad = (StoredAd)it.next();
                  String sURL=serivceURL;
                  newadid++; 
                  String adType=ad.getAdType();
                  //System.out.println("adType: "+adType); //+" & campaign type: "+ad.getCampType());
                  //adType=3;
                      int cmpid=ad.getCmpId();
                      int chnlid=ad.getchnlId();
                      int advID=ad.getAdvId();
                      int crID=db.getCreativeId(ad.getAdURL1(),ad.getCmpId());
                      String clientimprURL=ad.getImprURL();
                      //String serviceurl="http://localhost:8084/SBanner/AdTracking?";
                      String imptr=sURL+"AdTracking?tr=5&amp;uuid="+uuid+"&amp;adId=1&amp;cmpId="+cmpid+"&amp;crId="+crID+"&amp;advId="+advID+"&amp;pubId="+clntid+"&amp;chnlId="+chnlid;
                      String clickurl=sURL+"AdTracking?tr=99&amp;uuid="+uuid+"&amp;adId=1&amp;cmpId="+cmpid+"&amp;crId="+crID+"&amp;advId="+advID+"&amp;pubId="+clntid+"&amp;chnlId="+chnlid+"&amp;CTU="+ad.getClkThrURL();
                      
                      adserved=1;
                      
                      
                      if(adType.equals("1") || adType.equals("20"))   // falsh or expandable flash wiith built in expand feature
                      { 
                          
                       //Response="<script>var hasFlash = false; try {  var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');  if (fo) { hasFlash = true; } } catch (e) {  if (navigator.mimeTypes  && navigator.mimeTypes['application/x-shockwave-flash'] != undefined  && navigator.mimeTypes['application/x-shockwave-flash'].enabledPlugin) {   hasFlash = true;  }} if (hasFlash) { ";   
                       Response="<script>var hasFlash = false; try {  var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');  if (fo) { hasFlash = true; } } catch (e) {  if (navigator.mimeTypes  && navigator.mimeTypes['application/x-shockwave-flash'] != undefined  && navigator.mimeTypes['application/x-shockwave-flash'].enabledPlugin) {   hasFlash = true;  }} if (hasFlash) { ";
                       Response= Response+"document.write(\"<img src='"+imptr+"' border='0' width='1' height='1'>\");";
                       if(!clientimprURL.equals("")|| clientimprURL!=null) { Response= Response+"document.write(\"<img src='"+clientimprURL+"' border='0' width='1' height='1'>\");"; }
                       Response= Response+"document.write(\"<object id='"+uuid+"' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='https://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0'";
                       Response =Response+" width='"+ad.getCampWidth1()+"' height='"+ad.getCampHeight1()+"'>";
                       Response =Response+"<param name='allowScriptAccess' value='always'/>";
                       Response =Response+"<param name='movie' value='"+ad.getAdURL1()+"'/>";
                       Response =Response+"<param name='FlashVars' value='clickTAG="+clickurl+"'>";
                       Response =Response+"<param name='quality' value='high'/>";
                       Response =Response+"<param name='wmode' value='Opaque'/>";
                       Response =Response+"<embed quality='high' allowScriptAccess='always' width='"+ad.getCampWidth1()+"' height='"+ad.getCampHeight1()+"' wmode='Opaque' type='application/x-shockwave-flash' pluginspage='https://www.macromedia.com/go/getflashplayer' src='"+ad.getAdURL1()+"' flashvars='clickTAG="+ad.getClkThrURL()+"'></embed></object>\"); }";
                       if(ad.hasBackUP().equals("1"))
                       {                       
                         Response =Response+"else { document.write(\"<a href='"+clickurl+"' target='_blank'><img width='"+ad.getCampWidth1()+"' height='"+ad.getCampHeight1()+"' src='"+ad.getBackupURL1()+"'  alt='Click Here!' title='Click Here!'  border='0' ></a>\");}";
                       }
                       Response =Response+"</script>";
                      }
                      else if(adType.equals("3"))  // only images----
                      {  
                       Response =Response+"<script>document.write(\"<a href='"+clickurl+"' target='_blank'><img width='"+ad.getCampWidth1()+"' height='"+ad.getCampHeight1()+"' src='"+ad.getAdURL1()+"'  alt='Click Here!' title='Click Here!'  border='0' ></a>\");";
                       Response=Response+"document.write(\"<img src='"+imptr+"' border='0' width='1' height='1'>\");";
                       if(!clientimprURL.equals("")|| clientimprURL!=null) { Response= Response+"document.write(\"<img src='"+clientimprURL+"' border='0' width='1' height='1'>\");"; }
                       Response =Response+"</script>";
                      }
                      else if(adType.equals("99"))  //External tag;
                      {  
                    	  Response = "<script>";
                          if ((!clientimprURL.equals("")) || (clientimprURL != null)) {
                            Response = Response + "document.write(\"<img src='" + clientimprURL + "' border='0' width='1' height='1' style='position:absolute;'>\");";
                          }
                          String newadurl = ad.getAdURL1().replace("[INSERT_CLICK_TRACKER_MACRO]", "" + clickurl);
                          
                          Response = Response + "</script><div style=\"position:absolute;\">" + newadurl + "</div>";
                          if (((!clientimprURL.equals("")) || (clientimprURL != null))) {
                            Response = Response + "" + clientimprURL + "";
                          }
                          Response = Response + "<img src='" + imptr + "' border='0' width='1' height='1' style='position:absolute;'>";
                      }
                      else
                      {
                        Response="";
                      }    
                   } 
               
                        
              
             
            
            }
                
            ad=null;
        } 
        catch (Exception e1) 
        {   
            e1.printStackTrace();  
            Response="";
            
        } 
        finally
        {
         if (db != null) {
                db = null;
            }
         //Response="";
        }
      
       return Response;   
    }
    private String getError(int err)
    {
        String errorString="";
        switch(err)
        {
            case 1:
                errorString="No ads available this time";
        }
       return errorString; 
    }
 
    
}