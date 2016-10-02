<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%
String ac="";
ac=request.getParameter("acode");
boolean auth=false;
if(ac!=null) { if(ac.equals("spidio_123")) auth=true; }
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to DMP Platform Test Page</title>
    </head>
    <body>
    <% if(!auth) {%>
    <form name="form1" action="index.jsp" method="post"> 
    <table>
         <tr><td>Enter Authorization Code</td><td><input type="PASSWORD" name="acode" value="" ></td><td colspan="2"><input type="submit" name="subacode" value="submit"></td></tr>
    </table>
    </form>
    <% } else { %>
    <h1>Welcome to DMP Server Test Page</h1>
    
    <form name="form1" action="Controller" method="post">
     <table>
           
         <tr><td>Site ID (Eros id)</td><td><input type="text" name="siteId" value="1060"></td></tr>
         <tr><td>Channel Id</td><td> <input type="text" name="chnlid" value="1164" ></td></tr>
         <tr><td>IP Address </td>
         <td>
           <input type="text" name="IP" value="202.54.157.193">India<br>(UK-213.248.242.13,US-76.21.108.84)</td></tr>
        
         <tr><td>OS </td><td><input type="text" name="os" value="Windows7"></td></tr>
         <tr><td>Browser </td><td><input type="text" name="brtype" value="Firefox"></td></tr>
         <tr><td>Banner width</td><td><input type="text" name="width" value="300"></td></tr>
         <tr><td>Banner height</td><td><input type="text" name="height" value="250"></td></tr>
         <tr><td>Reference URL </td><td><input type="text" name="refURL" value="http://www.example.com/bestvideo.html"></td></tr>
         <tr><td>Exclude Creatives</td><td> <input type="text" name="crId" value="1"></td></tr>
         <tr><td>Fingerprint Id</td><td> <input type="text" name="fingerprintId" value="1"></td></tr>
         <tr><td>&nbsp;</td></tr>
         <tr><td><input type="submit" name="submit" ></td><td></td></tr>
        
       </table>
        
    </form>
      <% } %> 
    </body>
</html>
