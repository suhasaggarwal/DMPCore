/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. GET GEO INFO SAVED AND COMPARE
 *
 * @author 
 */
import com.maxmind.geoip.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

public class GeoInfo {

    String city = "";
    String zip = "";
    String region = "";
    String country = "";

   
    
    public GeoInfo(String IP, ServletContext context) {
        try 
        {
            
            LookupService cl = LookupService.getInstance();
            Location loc = cl.getLocation(IP);
            if(loc!=null)
            {
            //System.out.println(loc.countryCode);
                if (loc.postalCode == null) {
                    zip = "";
                } else {
                    zip = loc.postalCode;
                }
                if (loc.city == null) {
                    city = "";
                } else {
                    city = loc.city;
                }
                if (loc.region == null) {
                    region = "";
                } else {
                    region = loc.region;
                }
                if (loc.countryCode == null) {
                    country = "";
                } else {
                    country = loc.countryCode;
                }
            }
            cl.close();
        } catch (Exception e) 
        { 
           e.printStackTrace();  
           //System.out.println(" IP "+IP);
        }
    }

    private synchronized void storeDataInContext(ServletContext context) 
    {
        try 
        {
           
           //     String sep = System.getProperty("file.separator");               
            //    String dir = System.getProperty("user.dir"); // Uncomment for windows                
                //String dir = "/usr/local/share/GeoIP"; // Uncomment for Linux
           // //    String dbfile = dir + sep + "GeoLiteCity.dat";
             //   cl = new LookupService(dbfile, LookupService.GEOIP_MEMORY_CACHE);
             //   if(cl!=null) cacheObject.putGeoInfo("location", cl);         
             
        } catch (Exception e) 
        {
            e.printStackTrace(); 
        }

    }

    public String getZip()    {     return zip;     }
    public String getRegion() {     return region;  }
    public String getCity()   {     return city;    }
    public String getCountry(){     return country; }
}
