


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.dbpedia.spotlight.exceptions.AnnotationException;
import org.dbpedia.spotlight.model.DBpediaResource;
import org.dbpedia.spotlight.model.Text;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.HttpMethod;

public abstract class AnnotationClient {

    //public Logger LOG = Logger.getLogger(this.getClass());
    private List<String> RES = new ArrayList<String>();

    // Create an instance of HttpClient.
    private static HttpClient client = new HttpClient();
    public List<String> getResu(){
        return RES;     
    }

    public String request(GetMethod getMethod) throws AnnotationException {
        String response = null;
        // Provide custom retry handler is necessary
        ( getMethod).getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        try {
            // Execute the method.
            int statusCode = client.executeMethod((org.apache.commons.httpclient.HttpMethod) getMethod);
            if (statusCode != HttpStatus.SC_OK) {
               // LOG.error("Method failed: " + ((HttpMethodBase) method).getStatusLine());
            }

            // Read the response body.
            byte[] responseBody = ((HttpMethodBase) getMethod).getResponseBody(); //TODO Going to buffer response body of large or unknown size. Using getResponseBodyAsStream instead is recommended.

            // Deal with the response.
            // Use caution: ensure correct character encoding and is not binary data
            response = new String(responseBody);

        } catch (HttpException e) {
           // LOG.error("Fatal protocol violation: " + e.getMessage());
            throw new AnnotationException("Protocol error executing HTTP request.",e);
        } catch (IOException e) {
            //((Object) LOG).error("Fatal transport error: " + e.getMessage());
            //((Object) LOG).error(((HttpMethodBase) method).getQueryString());
            throw new AnnotationException("Transport error executing HTTP request.",e);
        } finally {
            // Release the connection.
            ((HttpMethodBase) getMethod).releaseConnection();
        }
        return response;

    }

    protected static String readFileAsString(String filePath) throws java.io.IOException{
        return readFileAsString(new File(filePath));
    }

    protected static String readFileAsString(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        @SuppressWarnings("resource")
        BufferedInputStream f = new BufferedInputStream(new FileInputStream(file));
        f.read(buffer);
        return new String(buffer);
    }

    static abstract class LineParser {

        public abstract String parse(String s) throws ParseException;

        static class ManualDatasetLineParser extends LineParser {
            public String parse(String s) throws ParseException {
                return s.trim();
            }
        }

        static class OccTSVLineParser extends LineParser {
            public String parse(String s) throws ParseException {
                String result = s;
                try {
                    result = s.trim().split("\t")[3];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new ParseException(e.getMessage(), 3);
                }
                return result; 
            }
        }
    }

    public void saveExtractedEntitiesSet(String Question, LineParser parser, int restartFrom) throws Exception {
        String text = Question;
        int i=0;
        //int correct =0 ; int error = 0;int sum = 0;

        for (String snippet: text.split("\n")) {
            String s = parser.parse(snippet);
            if (s!= null && !s.equals("")) {
                i++;

                if (i<restartFrom) continue;

                List<DBpediaResource> entities = new ArrayList<DBpediaResource>();

                try {
                    entities = extract(new Text(snippet.replaceAll("\\s+"," ")));
                    System.out.println(entities.get(0).getFullUri());

                } catch (AnnotationException e) {
                   // error++;
                    //LOG.error(e);
                    e.printStackTrace();
                }
                for (DBpediaResource e: entities) {
                    RES.add(e.uri());
                }
            }
        }
    }


    public abstract List<DBpediaResource> extract(Text text) throws AnnotationException;

    public void evaluate(String Question) throws Exception {
        evaluateManual(Question,0);
    }

    public void evaluateManual(String Question, int restartFrom) throws Exception {
         saveExtractedEntitiesSet(Question,new LineParser.ManualDatasetLineParser(), restartFrom);
    }
}