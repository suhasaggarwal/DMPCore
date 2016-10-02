
import java.io.IOException;
import java.io.PrintWriter;
 



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.wurfl.core.CapabilityNotDefinedException;
import net.sourceforge.wurfl.core.CustomWURFLHolder;
import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.MarkUp;
import net.sourceforge.wurfl.core.WURFLHolder;
import net.sourceforge.wurfl.core.WURFLManager;
import net.sourceforge.wurfl.core.WURFLService;
 
public class DeviceIdentification  {
	
	
//Can Also return screen size, dimensions
	
 
public static Map DeviceDetector(HttpServletRequest request) throws ServletException, IOException {
 
 
        WURFLHolder wurflHolder = WurflHolder.getInstance();
        
    	WURFLManager wurfl = wurflHolder.getWURFLManager();
        Device device = wurfl.getDeviceForRequest(request);
 
        MarkUp deviceMarkUp = device.getMarkUp();
 
        
        
        String markup = null;
 
        if (deviceMarkUp.XHTML_ADVANCED.equals(deviceMarkUp)) {
            markup = "xhtml_advanced";
        } else if (deviceMarkUp.XHTML_SIMPLE.equals(deviceMarkUp)) {
            markup = "xhtml_simple";
        } else if (deviceMarkUp.CHTML.equals(deviceMarkUp)) {
            markup = "chtml";
        } else if (deviceMarkUp.WML.equals(deviceMarkUp)) {
            markup = "wml";
        }
 
       
        Map<String,String> hm = new HashMap<String,String>();
        // Put elements to the map
        
        hm = device.getCapabilities();
    	
     
        return hm;
}
  
}