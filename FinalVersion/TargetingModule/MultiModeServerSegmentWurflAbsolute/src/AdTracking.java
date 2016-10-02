/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. GETTING REQUEST FOR TRACKERS
 * 2. INSERTING THEM USING DBUTILITY CLASS
 *
 * @author 
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdTracking extends HttpServlet
{
 
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
 {
        DBUtility dbUtil = new DBUtility();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        java.util.Enumeration e = request.getParameterNames();       
        int count = 0;
        
        while (e.hasMoreElements()) 
        {
            count++;
            e.nextElement();
        }
        
        int tr =0;
        String clkurl="NA";
        
        if(request.getParameter("tr")==null)
        {    
            tr=0;
        }
        else
        {
          tr=Integer.parseInt(request.getParameter("tr"));
        }
        if(request.getParameter("clickThrough")==null)
        {    
            clkurl="NA";            
        }
        else
        {
          clkurl=request.getParameter("clickThrough");
        }
        
        
        String uuid = request.getParameter("uuid");
        String adId = request.getParameter("adId");        
        String cmpId = request.getParameter("cmpId");
        String pubId = request.getParameter("pubId");
        String chnlId = request.getParameter("chnlId");
        String advId = request.getParameter("advId");
        String crId = request.getParameter("crId");
        String ipadd = request.getHeader("X-FORWARDED-FOR"); 
        if(ipadd == null)  
        {  
               ipadd = request.getRemoteAddr();  
        } 
        
        /*System.out.println("tr "+request.getParameter("tr"));
        System.out.println("uuid "+request.getParameter("uuid"));
        System.out.println("pubId "+request.getParameter("pubId"));
        System.out.println("cmpId "+request.getParameter("cmpId"));
        */
        
        try 
        {
                if (uuid==null || adId==null || cmpId==null || pubId==null)
                {                
                    out.println("");
                }                
                else if (uuid.equals("") || adId.equals("") || tr==0 || cmpId.equals("") || pubId.equals(""))
                {                
                    out.println("");
                }
                else
                {
                                               
                        int adID=Integer.parseInt(adId);                        
                        //int tracking=Integer.parseInt(tr);
                        int crID=0;
                        int chnlID=0;
                        int pubID=0;
                        int advID=0;
                        if(pubId==null) pubID=0;
                        else pubID=Integer.parseInt(pubId);                         
                        int cmpID=0;
                        if(cmpId==null) cmpID=0;
                        else cmpID=Integer.parseInt(cmpId);                         
                        if(crId==null) crID=0;
                        else crID=Integer.parseInt(crId); 
                        if(chnlId==null) chnlID=0;
                        else chnlID=Integer.parseInt(chnlId); 
                        if(advId==null) advID=0;
                        else advID=Integer.parseInt(advId); 
                        //registering the tracking============
                        dbUtil.registerTrackings(uuid,adID,pubID,chnlID,cmpID,advID,crID,ipadd,tr); 
                        if(tr==99)
                        {
                          String clickURL=request.getParameter("CTU");
                          //System.out.println(clickURL);
                          response.sendRedirect(clickURL);
                        
                        }    
                 }
                 //System.out.println("passed url: "+request.getParameter("fsrc"));
                 //out.println(request.getParameter("fsrc"));
                 out.println("");
                 dbUtil=null;
                  
            
        } 
        catch (Exception e1) 
        {
            e1.printStackTrace();
            System.out.println("Error returning callback xml");
        }
        finally 
        {
            if (dbUtil!=null)dbUtil=null;
            out.close();
        }
 }

 
 
 
 // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
