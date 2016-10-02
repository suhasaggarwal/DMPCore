/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** USE OF THIS 
 * 1. LOADING THE REQUIRED INFORMATION INTO CONTEXT FOR FUTURE USE
 * 2. CLASSGETTING REQUEST FROM PUBLISHERS AND SEND BACK THE RESPONSES
 * 3. THE BASE CLASS TO CALL
 *
 * @author 
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;

public class Controller extends HttpServlet {

	ServletContext sc;

	public void init(ServletConfig sconf) {
		String[] siteList;
		sc = sconf.getServletContext();
		DBConnector db = new DBConnector();
		Connection con = null;
		Statement st = null, st1 = null, st2 = null, st3 = null, st4 = null;
		ResultSet rs = null, rs1 = null, rs2 = null, rs3 = null, rs4 = null;

		System.out
				.println("The Ad serving platform is started and intialize At: "
						+ new java.util.Date());
		try {
			String host = "localhost"; // FROM LOCALHOST
			// host="52.4.92.26";//"10.12.3.125"; //from server (private ip)
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://" + host
					+ ":3306/lightning_test", "root", ""); // connect to
															// localhost
			// con =
			// DriverManager.getConnection("jdbc:mysql://"+host+":3306/lyxel_AD_Server",
			// "adserver", "q1w2e3r4t5y6"); //connect to server
			st = con.createStatement();
			/**
			 * =================getting all active publisher n channel list and
			 * save it in context---------------------
			 **/
			String sql = "SELECT `publisher_id` FROM `publisher_detail` WHERE `status`='1'";
			// System.out.println("Servlet initialization query #1: " + sql);
			rs = st.executeQuery(sql);
			rs.last();
			int rcount = rs.getRow();
			int count = 0;
			siteList = new String[rcount];
			rs.beforeFirst();
			while (rs.next()) {
				siteList[count] = rs.getString("publisher_id");
				count++;
			}
			sc.setAttribute("siteList", siteList);
			/***
			 * ================================================================
			 * publisher list saved=============
			 **/

			/*****
			 * ===========================================Campaign mapping
			 * detail=======================
			 ***/
			String campinfo[][];
			String sqlquery = "SELECT cpbd.`campaign_id`,cpbd.`publisher_id`,cpbd.`channel_id`,C.`campaign_name`,C.`campaign_type`, cpbd.`cap`, C.`geo_targeted`, cpbd.`cpm-cpc`, C.`geo_target_type`, cpbd.`campaign_start_date`, cpbd.`campaign_end_date`, cpbd.`start_time`, cpbd.`end_time`, C.`advertiser_id` FROM `campaign_detail` C, `campaign_publisher-channel_target` cpbd,`creative_detail` ad WHERE C.`campaign_id`=cpbd.`campaign_id` AND cpbd.`status`='1' AND ad.`status`=1 GROUP BY cpbd.`channel_id`,cpbd.`campaign_id` ORDER BY cpbd.`publisher_id`";
			// String
			// sqlquery="SELECT cpbd.`campaign_id`,cpbd.`publisher_id`,cpbd.`channel_id`,C.`campaign_name`,C.`campaign_type`, cpbd.`cap`, C.`geo_targeted`, cpbd.`cpm-cpc`, C.`geo_target_type`, cpbd.`campaign_start_date`, cpbd.`campaign_end_date`, cpbd.`start_time`, cpbd.`end_time`, C.`advertiser_id` FROM `campaign_detail` C, `campaign_publisher-channel_target` cpbd,`creative_detail` ad WHERE C.`campaign_id`=cpbd.`campaign_id` AND cpbd.`status`='1' AND cpbd.`campaign_start_date`<=NOW() AND cpbd.`campaign_end_date`>=NOW() AND ad.`campaign_id`=C.`campaign_id` AND ad.`status`=1 GROUP BY cpbd.`publisher_id`,cpbd.`campaign_id` ORDER BY cpbd.`publisher_id`";
			// System.out.println("Servlet initialization query #2: " +
			// sqlquery);
			st2 = con.createStatement();
			st3 = con.createStatement();
			st4 = con.createStatement();
			rs2 = st2.executeQuery(sqlquery);
			rs2.last();

			campinfo = new String[rs2.getRow()][15];
			rs2.beforeFirst();
			int cnter = 0;
			while (rs2.next()) {
				int cmpid = rs2.getInt("campaign_id");
				campinfo[cnter][0] = rs2.getString("publisher_id");
				campinfo[cnter][1] = "" + cmpid; // rs2.getString("campaign_id");
				campinfo[cnter][2] = rs2.getString("campaign_name");
				campinfo[cnter][3] = rs2.getString("geo_targeted");
				campinfo[cnter][4] = rs2.getString("channel_id");
				campinfo[cnter][5] = "" + rs2.getFloat("cpm-cpc");
				campinfo[cnter][6] = rs2.getString("advertiser_id");

				String ccsql = "SELECT * FROM `campaign_country` WHERE `campaign_id`="
						+ cmpid;
				rs3 = st3.executeQuery(ccsql);
				campinfo[cnter][12] = "";
				campinfo[cnter][13] = "";
				campinfo[cnter][14] = "";
				while (rs3.next()) {
					campinfo[cnter][12] = rs3.getString("country_code") + "="
							+ rs3.getString("state") + "="
							+ rs3.getString("city") + "||"
							+ campinfo[cnter][12];
					campinfo[cnter][13] = rs3.getString("country_name") + "="
							+ rs3.getString("state") + "="
							+ rs3.getString("city") + "||"
							+ campinfo[cnter][13];
				}

				campinfo[cnter][7] = "" + rs2.getInt("geo_target_type");
				if (rs2.getString("campaign_start_date").equals("0000-00-00")) {
					java.text.SimpleDateFormat hdf = new java.text.SimpleDateFormat(
							"yyyy-MM-dd");
					campinfo[cnter][8] = hdf.format(Calendar.getInstance(
							Locale.US).getTime());

				} else {
					campinfo[cnter][8] = ""
							+ rs2.getString("campaign_start_date");
				}
				if (rs2.getString("campaign_end_date").equals("0000-00-00")) {
					java.text.SimpleDateFormat hdf = new java.text.SimpleDateFormat(
							"yyyy-MM-dd");
					campinfo[cnter][9] = hdf.format(Calendar.getInstance(
							Locale.US).getTime());

				} else {
					campinfo[cnter][9] = ""
							+ rs2.getString("campaign_end_date");
				}
				// campinfo[cnter][9] = ""+rs2.getString("campaign_end_date");
				campinfo[cnter][10] = "" + rs2.getString("start_time");
				campinfo[cnter][11] = "" + rs2.getString("end_time");

				cnter++;
			}
			sc.setAttribute("campinfo", campinfo);

			/*****
			 * ==========================Saved mapping
			 * data=======================================================
			 ***/
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in Getting Site List/mapping List");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sq) {
				}
				rs = null;
			}
			if (rs1 != null) {
				try {
					rs1.close();
				} catch (SQLException sq) {
				}
				rs1 = null;
			}
			if (rs2 != null) {
				try {
					rs2.close();
				} catch (SQLException sq) {
				}
				rs2 = null;
			}
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (SQLException sq) {
				}
				rs3 = null;
			}
			if (rs4 != null) {
				try {
					rs4.close();
				} catch (SQLException sq) {
				}
				rs4 = null;
			}

			if (st != null) {
				try {
					st.close();
				} catch (SQLException sq) {
				}
				st = null;
			}
			if (st1 != null) {
				try {
					st1.close();
				} catch (SQLException sq) {
				}
				st1 = null;
			}
			if (st2 != null) {
				try {
					st2.close();
				} catch (SQLException sq) {
				}
				st2 = null;
			}
			if (st3 != null) {
				try {
					st3.close();
				} catch (SQLException sq) {
				}
				st3 = null;
			}
			if (st4 != null) {
				try {
					st4.close();
				} catch (SQLException sq) {
				}
				st4 = null;
			}

			if (con != null) {
				try {
					if (!con.isClosed()) {
						con.close();
					}
				} catch (SQLException sq) {
				}
				con = null;
			}
			if (db != null) {
				db = null;
			}
			siteList = null;

		}
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DBUtility dbu = new DBUtility();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String error = "";
		String creativeId = "";
		String browser = request.getParameter("brtype");
		String os = request.getParameter("os");
		String device = request.getParameter("device");
		String chnlId = request.getParameter("chnlid");
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		String siteId = request.getParameter("siteId"); // --like
														// eros,indyarocks
		String ipAdd = request.getRemoteAddr();
		String fingerprintId = request.getParameter("fingerprintId");
		// Node node = nodeBuilder().node();
		// client = node.client();

		// ipAdd = "59.93.78.9";
		String sURL = "";
		String refURL = ""; // request.getParameter("refURL");
		String refURLO = request.getHeader("referer");
		try {
			if (refURLO != null) {
				refURL = java.net.URLEncoder.encode(refURLO, "UTF-8");
			} else if (request.getParameter("refURL") != null) {
				refURL = request.getParameter("refURL");
			} else {
				refURL = "";
			}

		} catch (Exception w) {
		}
		// System.out.println("encoded refurl "+refURL);

		String reqUrl = request.getRequestURL().toString();
		String userAgent = request.getHeader("User-Agent");

		if (request.getParameter("crId") == null
				|| request.getParameter("crId").equals("")
				|| request.getParameter("crId").equals(" ")) {
			creativeId = "";
		} else {
			creativeId = request.getParameter("crId");
		}

		try {
			if (ipAdd == null) {

				ipAdd = "100.100.100.100";

			} else if (ipAdd.equals("") || siteId.equals("")) {

				out.println("");
				System.out.println("IP Error");
			}

			else {
				sURL = serviceURL(reqUrl); // getting the service url
				try {
					String IP = "";
					// System.out.println("ip "+ipAdd);
					if (ipAdd.indexOf("\"") == 0) {
						IP = ipAdd.substring(1, ipAdd.length() - 1);
					} else {
						IP = ipAdd;
					}

					// System.out.println("ip "+ipAdd);
					String ipc_zip = "";
					String ipc_country = "";
					String ipc_city = "";
					String ipc_region = "";
					boolean mobRequest = false;

					GeoInfo ipc = new GeoInfo(IP, sc);
					ipc_zip = ipc.getZip();
					ipc_country = ipc.getCountry();
					ipc_city = ipc.getCity();
					ipc_region = ipc.getRegion();
					dbu.registerAdrequest("", ipAdd, "", "", "", "", browser,
							"", refURL, "", userAgent, siteId, chnlId,
							ipc_city, ipc_region, ipc_country);
					String campinfofound[][] = campInfo(siteId, chnlId,
							ipc_country, ipc_region, ipc_city, mobRequest,
							fingerprintId);

					ProcessRequest rq = new ProcessRequest(siteId, chnlId,
							creativeId, campinfofound, ipAdd, refURL, sURL,
							width, height, os, browser, device, ipc_zip,
							ipc_country, ipc_city, ipc_region);
					out.print(rq.doProcess());

					/*
					 * ==============HARDCODED AD FOR QUICK
					 * TEST============================= String adType="1";
					 * String Response=""; String
					 * uuid="39941c84-334e-4302-b4cb-675e99404f56";
					 * if(adType.equals("1") || adType.equals("20")) // falsh or
					 * expandable flash wiith built in expand feature {
					 * //Response=
					 * "<script>var hasFlash = false; try {  var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');  if (fo) { hasFlash = true; } } catch (e) {  if (navigator.mimeTypes  && navigator.mimeTypes['application/x-shockwave-flash'] != undefined  && navigator.mimeTypes['application/x-shockwave-flash'].enabledPlugin) {   hasFlash = true;  }} if (hasFlash) { "
					 * ; Response=
					 * "<script>var hasFlash = false; try {  var fo = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');  if (fo) { hasFlash = true; } } catch (e) {  if (navigator.mimeTypes  && navigator.mimeTypes['application/x-shockwave-flash'] != undefined  && navigator.mimeTypes['application/x-shockwave-flash'].enabledPlugin) {   hasFlash = true;  }} if (hasFlash) { "
					 * ; //Response=
					 * Response+"document.write(\"<img src='"+imptr
					 * +"' border='0' width='1' height='1'>\");"; Response=
					 * Response+"document.write(\"<object id='"+uuid+
					 * "' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='https://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0'"
					 * ; Response =Response+" width='300' height='250'>";
					 * Response
					 * =Response+"<param name='allowScriptAccess' value='always'/>"
					 * ; Response =Response+
					 * "<param name='movie' value='http://54.84.151.220/demo/da/tbnr/Emi.swf'/>"
					 * ; Response =Response+
					 * "<param name='FlashVars' value='clickTAG=http://monsoonads.com'>"
					 * ; Response
					 * =Response+"<param name='quality' value='high'/>";
					 * Response
					 * =Response+"<param name='wmode' value='Opaque'/>";
					 * Response =Response+
					 * "<embed quality='high' allowScriptAccess='always' width='300' height='250' wmode='Opaque' type='application/x-shockwave-flash' pluginspage='https://www.macromedia.com/go/getflashplayer' src='http://54.84.151.220/demo/da/tbnr/Emi.swf' flashvars='clickTAG=http://monsoonads.com'></embed></object>\"); }"
					 * ; //if(ad.hasBackUP().equals("1")) //{ Response
					 * =Response+
					 * "else { document.write(\"<a href='http://monsoonads.com' target='_blank'><img width='300' height='250' src='http://54.84.151.220/demo/da/tbnr/Emi.jpg'  alt='Click Here!' title='Click Here!'  border='0' ></a>\");}"
					 * ; //} Response =Response+"</script>"; }
					 * out.print(Response);
					 * /*===============================================
					 */

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("IP1:" + ipAdd + " siteId:" + siteId);
				}

			}
			out.close();

			siteId = null;// --like eros,indyarocks
			ipAdd = null;

			sURL = null;
			refURL = null;

			reqUrl = null;
			siteId = null;
			error = null;

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("Error Returning response");
		}
	}

	private String serviceURL(String reqURL) {
		String serviceUrl = "";
		int end = 0;
		end = reqURL.indexOf("Controller");
		serviceUrl = reqURL.substring(0, end);
		// System.out.println(" serviceURL  "+serviceUrl);
		return serviceUrl;
	}

	private String[][] campInfo(String siteID, String chnlID, String country,
			String state, String city, boolean MR, String fingerprintId) {
		allcampinfo = (String[][]) sc.getAttribute("campinfo");
		allcamplength = allcampinfo.length;
		selectedcampinfo=new String[allcamplength][21];
		boolean targetingModeEnabled = true;
		boolean singlecampaignMode = false;
		
		String[] fingerprintSegments = fingerprintId.split("\\.");
		String[] campData = null;
		System.out.println(fingerprintSegments[0] + ":"
				+ fingerprintSegments[1] + ":" + fingerprintSegments[2]);
				
		if (fingerprintSegments.length > 1)
			fingerprintId = fingerprintSegments[0] + "."
					+ fingerprintSegments[1] + "." + fingerprintSegments[2];
		ArrayList targetedCampaigns = new ArrayList();

		if (targetingModeEnabled == true) {

			Client client = new TransportClient()
					.addTransportAddress(new InetSocketTransportAddress(
							"localhost", 9300));
			String campaignId = null;
			SearchHit[] fingerprintDetails = ElasticSearchAPIs
					.searchDocumentFingerprintIds(client, "dmpdata", "core2",
							"fingerprint_id", fingerprintId);
			// Get Search Hits results
			// Returns a Map, returns audience segment from the map

			int index;
			int counter = 0;
			int flag = 0;
			int tracker = 0;

			for (SearchHit hit : fingerprintDetails) {
				System.out.println("------------------------------");
				Map<String, Object> result = hit.getSource();
				campaignId = (String) result.get("campaign_mapping");
                System.out.println("Campaign_Id:"+campaignId);
				
				if (campaignId != null & !campaignId.isEmpty()) {
					if (singlecampaignMode == false)
					{
					    if(campaignId.contains(",")==true){
						 campData = campaignId.split(",");
					     for(int i = 0; i < campData.length; i++)	
					       { 
					    	 targetedCampaigns.add(campData[i]);
					         System.out.println("Campaigns_in_list:"+campData[i]);
					       }
					    }
					    else
					     targetedCampaigns.add(campaignId);	
					
					
					}		
						else {
						targetedCampaigns.clear();
						targetedCampaigns.add(campaignId);
					}
				    
					
				
				}
                   
				if(campaignId !=null && !campaignId.isEmpty())
				break;
                   
			}  	
				index = (int) (Math.random() * targetedCampaigns.size());
				if (targetedCampaigns != null && !targetedCampaigns.isEmpty())
					campaignId = (String) targetedCampaigns.get(index);
                    System.out.println("CampaignId:"+campaignId);
				
				
				if (campaignId != null && !campaignId.isEmpty()) {
					for (int x1 = 0; x1 < allcamplength; x1++) {

						if (campaignId.trim().equalsIgnoreCase(
								(allcampinfo[x1][1]).trim())) {
							
							System.out.println("Found Match");
							System.out.println("MatchedCampaign:"+allcampinfo[x1][1]);
							selectedcampinfo[tracker][0] = allcampinfo[x1][0];
							selectedcampinfo[tracker][1] = allcampinfo[x1][1];
							selectedcampinfo[tracker][2] = allcampinfo[x1][2];
							selectedcampinfo[tracker][3] = allcampinfo[x1][3];
							selectedcampinfo[tracker][4] = allcampinfo[x1][4];
							selectedcampinfo[tracker][5] = allcampinfo[x1][5];
							selectedcampinfo[tracker][6] = allcampinfo[x1][6];
							selectedcampinfo[tracker][7] = allcampinfo[x1][7];
							selectedcampinfo[tracker][8] = allcampinfo[x1][8];
							selectedcampinfo[tracker][9] = allcampinfo[x1][9];
							selectedcampinfo[tracker][10] = allcampinfo[x1][10];
							selectedcampinfo[tracker][11] = allcampinfo[x1][11];
							selectedcampinfo[tracker][12] = allcampinfo[x1][12];
							selectedcampinfo[tracker][13] = allcampinfo[x1][13];
							selectedcampinfo[tracker][14] = allcampinfo[x1][14];
							tracker++;
							break;
						}

						System.out.println(campaignId);

					}

					// Convert allcampinfo to specific campaign data returned by
					// elastic search, it can give a mixture of campaignIds with
					// geo targeting enabled or disabled.

				}

				if (tracker > 0)
					return selectedcampinfo;

			

		}
		mycampinfo = new String[allcamplength][21];

		int mcicnt = 0;
		for (int x = 0; x < allcamplength; x++) {
			// System.out.println("allcampinfo[x][4]: "+allcampinfo[x][4]+" allcampinfo[x][0]: "+allcampinfo[x][0]+" siteId:"+siteID+" , chnlID"+chnlID);
			if (allcampinfo[x][4].equals(chnlID)
					&& allcampinfo[x][0].equals(siteID)) // match for the
															// publisher
			{ // For audience segmentation , call will come for channel id,
				// publisher id will be mentioned.

				if (allcampinfo[x][3].equals("1")) // if it is geo targeted
				{
					// System.out.println("GEO TARGETED - YES ");
					StringTokenizer token;
					int count = 0;
					if (MR)
						token = new StringTokenizer(allcampinfo[x][13], "||");
					else
						token = new StringTokenizer(allcampinfo[x][12], "||");
					count = token.countTokens();
					String full_location[] = new String[count];
					count = 0;
					while (token.hasMoreTokens()) {
						full_location[count] = token.nextToken();
						count++;
					}
					if (allcampinfo[x][7].equals("1")) // type of geo targetting
														// - look match
					{
						// System.out.println(" Ad type IS "+allcampinfo[x][13]);
						// System.out.println(" count ad start IS "+allcampinfo[x][14]);
						boolean matched = false;
						for (int ix = 0; ix < full_location.length; ix++) {
							// System.out.println("TYPE OF GEO TARGETING - LOOK MATCH in "+full_location[ix]);
							StringTokenizer tokenagain;
							int countagain = 0;
							tokenagain = new StringTokenizer(full_location[ix],
									"=");
							String location[] = new String[3];
							while (tokenagain.hasMoreTokens()) {
								location[countagain] = tokenagain.nextToken();
								// System.out.println(" COUNTRY IS "+location[countagain]);
								countagain++;
							}
							try {
								if (location[0] != null && location[1] != null
										&& location[2] != null) {
									// System.out.println(" Looking all match country,state and city");
									if ((location[0].equals(country))
											&& (location[1].equals(state))
											&& (location[2].equals(city))) {
										matched = true;
										break;
									}
								} else if (location[0] != null
										&& location[1] != null) {
									// System.out.println(" Looking exact country and state match");
									if ((location[0].equals(country))
											&& (location[1].equals(state))) {
										matched = true;
										break;
									}
								} else {
									// System.out.println(" Looking exact country match"+location[0]);
									if (location[0].equals(country)) {
										matched = true;
										break;
									}
								}

							} catch (Exception es) {
								es.printStackTrace();
							}

						}
						if (matched) {
							// System.out.println(" country list "+allcampinfo[x][4]);
							// System.out.println(" SUCCESS..MATCH FOUND For "+
							// allcampinfo[x][1]);
							mycampinfo[mcicnt][0] = allcampinfo[x][0];
							mycampinfo[mcicnt][1] = allcampinfo[x][1];
							mycampinfo[mcicnt][2] = allcampinfo[x][2];
							mycampinfo[mcicnt][3] = allcampinfo[x][3];
							mycampinfo[mcicnt][4] = allcampinfo[x][4];
							mycampinfo[mcicnt][5] = allcampinfo[x][5];
							mycampinfo[mcicnt][6] = allcampinfo[x][6];
							mycampinfo[mcicnt][7] = allcampinfo[x][7];
							mycampinfo[mcicnt][8] = allcampinfo[x][8];
							mycampinfo[mcicnt][9] = allcampinfo[x][9];
							mycampinfo[mcicnt][10] = allcampinfo[x][10];
							mycampinfo[mcicnt][11] = allcampinfo[x][11];

							mycampinfo[mcicnt][12] = country;
							mycampinfo[mcicnt][13] = state;
							mycampinfo[mcicnt][14] = city;
							mcicnt++;
						} else
							continue;
					} // end of if //type of geo targetting - look match

					else // type of geo targetting - look for not match
					{
						// System.out.println("TYPE OF GEO TARGETING - LOOK MATCH (NOT IN) ");
						boolean notmatched = true;
						for (int ix = 0; ix < full_location.length; ix++) {
							String location[] = full_location[ix].split("+");
							if ((location[1].equals("") || location[1] == null)
									&& (location[2].equals("") || location[2] == null)) {
								if (location[0].equals(country)) {
									notmatched = false;
									break;
								}
							} else if (location[2].equals("")
									|| location[2] == null) {
								if ((location[0].equals(country))
										&& (location[1].equals(state))) {
									notmatched = false;
									break;
								}
							} else {
								if ((location[0].equals(country))
										&& (location[1].equals(state))
										&& (location[2].equals(city))) {
									notmatched = false;
									break;
								}
							}

						}

						if (notmatched) {
							mycampinfo[mcicnt][0] = allcampinfo[x][0];
							mycampinfo[mcicnt][1] = allcampinfo[x][1];
							mycampinfo[mcicnt][2] = allcampinfo[x][2];
							mycampinfo[mcicnt][3] = allcampinfo[x][3];
							mycampinfo[mcicnt][4] = allcampinfo[x][4];
							mycampinfo[mcicnt][5] = allcampinfo[x][5];
							mycampinfo[mcicnt][6] = allcampinfo[x][6];
							mycampinfo[mcicnt][7] = allcampinfo[x][7];
							mycampinfo[mcicnt][8] = allcampinfo[x][8];
							mycampinfo[mcicnt][9] = allcampinfo[x][9];
							mycampinfo[mcicnt][10] = allcampinfo[x][10];
							mycampinfo[mcicnt][11] = allcampinfo[x][11];

							mycampinfo[mcicnt][12] = country;
							mycampinfo[mcicnt][13] = state;
							mycampinfo[mcicnt][14] = city;
							// System.out.println(" in targeted with not match ");
							mcicnt++;
						} else {
							continue;
						}

					} // end of else //type of geo targetting - look for not
						// match

				}

				else // if it is not geo targeted
				{
					// System.out.println("GEO TARGETED - NO (ALL WORLD) ");
					mycampinfo[mcicnt][0] = allcampinfo[x][0];
					mycampinfo[mcicnt][1] = allcampinfo[x][1];
					mycampinfo[mcicnt][2] = allcampinfo[x][2];
					mycampinfo[mcicnt][3] = allcampinfo[x][3];
					mycampinfo[mcicnt][4] = allcampinfo[x][4];
					mycampinfo[mcicnt][5] = allcampinfo[x][5];
					mycampinfo[mcicnt][6] = allcampinfo[x][6];
					mycampinfo[mcicnt][7] = allcampinfo[x][7];
					mycampinfo[mcicnt][8] = allcampinfo[x][8];
					mycampinfo[mcicnt][9] = allcampinfo[x][9];
					mycampinfo[mcicnt][10] = allcampinfo[x][10];
					mycampinfo[mcicnt][11] = allcampinfo[x][11];

					mycampinfo[mcicnt][12] = country;
					mycampinfo[mcicnt][13] = state;
					mycampinfo[mcicnt][14] = city;

					// System.out.println(" in not targeted  ");
					mcicnt++;
				} // end of else

			} // end if match for the publisher

		} // end for

		return mycampinfo;
	}

	private boolean loadSites(String siteID) {
		boolean res = false;
		String[] siteList = (String[]) sc.getAttribute("siteList");
		for (int x = 0; x < siteList.length; x++) {
			if (siteList[x].equals(siteID)) {
				res = true;
			}
		}
		siteList = null;
		return res;
	}

	private String allcampinfo[][] = null;
	private int allcamplength = 0;
	private String mycampinfo[][] = null;
	private String selectedcampinfo[][] = null;
	private boolean audienceSegmentEnabled = GlobalConfiguration
			.getBoolean("dmpenabled");

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}
	// </editor-fold>
}
