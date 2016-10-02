/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. GET GEO INFO SAVED AND COMPARE
 *
 * @author 
 */
import javax.servlet.ServletContext;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

public class GeoInfo {

    String city = "";
    String zip = "";
    String region = "";
    String country = "";
    LookupService cl;

    public GeoInfo(String IP, ServletContext context) {
        try 
        {
            
            cl = (LookupService) context.getAttribute("location");
            if (cl == null)     storeDataInContext(context);
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
           
                String sep = System.getProperty("file.separator");               
                String dir = System.getProperty("user.dir"); // Uncomment for windows                
                //String dir = "/usr/local/share/GeoIP"; // Uncomment for Linux
                String dbfile = dir + sep + "GeoLiteCity.dat";
                cl = new LookupService(dbfile, LookupService.GEOIP_MEMORY_CACHE);
                if(cl!=null) context.setAttribute("location", cl);              
             
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
