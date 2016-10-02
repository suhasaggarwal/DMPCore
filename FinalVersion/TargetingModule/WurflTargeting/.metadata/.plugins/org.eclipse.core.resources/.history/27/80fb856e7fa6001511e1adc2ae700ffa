/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. THIS CALSS IS CALLED BY THE SERVICE CLASS FURTHER PROCESS THE CALLS
 *
 * @author 
 */
import java.util.*;
import java.io.*;

public class ProcessRequest 
{        
    public ProcessRequest(String pubId,String channelid,String crId,String campInfo[][], String ipAdd, String refURL, String serviceURL, String width,String height,String os,String browser, String deviceinfo,String ipcZip, String ipcCountry, String ipcCity, String ipcRegion)
    {
        /*
        System.out.println(campInfo[0][0]);
        System.out.println(campInfo[0][1]);
        System.out.println(campInfo[0][2]);
        System.out.println(campInfo[0][3]);
        System.out.println(campInfo[0][4]);
        System.out.println(campInfo[0][5]);
        System.out.println(campInfo[0][6]);
        System.out.println(campInfo[0][7]);
        System.out.println(campInfo[0][8]);
        System.out.println(campInfo[0][9]);
        System.out.println(campInfo[0][10]);
        System.out.println(campInfo[0][11]);
        System.out.println(campInfo[0][12]);
         */
        //System.out.println(width+" ,"+height);
        
        
        //Stroring all info into private variable
        RPCampInfo=campInfo;        
        RPipcZip=ipcZip;
        RPipcCountry=ipcCountry;
        RPipcCity=ipcCity;
        RPipcRegion=ipcRegion;
        RPwidth=width;
        RPheight=height;
        RPos=os;
        RPbrowser=browser;
        RPpubId = pubId;
        RPchnlId= channelid;
        RPcrId=crId;
        RPrefURL = refURL;
        RPsURL = serviceURL; 
        RPdevice=deviceinfo;
        RPip = ipAdd;
        RPerrSent = 0;
       
    }   
    public String doProcess()
    {
        
         SAds=new GetSpidioAds();    //Calling for ad list
         adList=SAds.fetchAds(RPcrId,RPCampInfo,RPpubId,RPchnlId,RPwidth,RPheight,RPos,RPbrowser);
         if(adList.isEmpty())
         {
            RPresponse="";
            System.out.println("no ad found..");
         }
         else
         { 
             AdGen= new AdGeneratorSpidio();
             RPresponse = AdGen.generateAdResponse(adList,RPerrSent,RPsURL,RPpubId,RPcontentId,RPrefURL,RPos,RPbrowser,RPdevice,RPip,RPipcCity,RPipcRegion,RPipcCountry,RPvastMode);  
             
         }
          //System.out.print("Response is :"+RPresponse);
          adList.clear();
          adList=null;

        
        if(RPresponse==null) RPresponse="";
        RPCampInfo=null;
        return RPresponse;
    }

    private GetSpidioAds SAds;
    private AdGeneratorSpidio AdGen;
    private String RPpubId="",RPcontentId="",RPsURL="",RPdevice="",RPcrId="",RPip="",RPwidth="",RPheight="",RPos="",RPbrowser="",RPchnlId="",RPrefURL="";
    private String RPresponse="";
    private String RPipcZip="",RPipcCountry="",RPipcCity="",RPipcRegion="";
    private int RPerrSent;
    
    private String RPCampInfo[][]=null;
    private boolean RPvastMode=false;
    private List adList;
    
}
