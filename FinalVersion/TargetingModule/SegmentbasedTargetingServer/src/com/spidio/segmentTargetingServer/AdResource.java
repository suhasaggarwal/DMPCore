package com.spidio.segmentTargetingServer;
import java.io.IOException;



/**
 * Represents the resource interface to the ad service.
 * Fetches ad markup using a HTTP Client.
 * <p/>
 * <p/>
 * Single-threaded access only.
 */
class AdResource {
    private static String TAG = "Tapad/AdResource";
    
    private static final String RESOURCE_URL = "http://localhost:8084/SBanner/ad";

    // Just a sanity check so we don't waste too much bandwidth in case of some
    // markup error / glitch.
    private static final int MAX_CONTENT_LENGTH = 1024 * 8;

    public static final String PARAM_DEVICE_ID = "uid";
    public static final String PARAM_AD_SIZE = "adSize";
    public static final String PARAM_CONTEXT_TYPE = "contextType";
    public static final String PARAM_PUBLISHER_ID = "pub";
    public static final String PARAM_PROPERTY_ID = "prop";
    public static final String PARAM_PLACEMENT_ID = "placementId";
    public static final String PARAM_WRAP_HTML = "wrapHtml";

    private DeviceIdentifier deviceId;
    private String propertyId;
    private String publisherId;


    AdResource(DeviceIdentifier deviceId, String publisherId, String propertyId, String userAgent) {
        this.deviceId = deviceId;
        this.publisherId = publisherId;
        this.propertyId = propertyId;
        
    }

    AdResponse get(AdRequest req) throws IOException {
        String query="";
        String uri = RESOURCE_URL + "?" + query;
       

        try {
            int  response = 1;

            String entity = "";
            switch (response) {
                case 204:
                    return AdResponse.noAdAvailable();
                case 200:
                    long contentLength = entity.length();
                    if (contentLength > MAX_CONTENT_LENGTH) {
                        //entity.consumeContent();
                        throw new IOException("Content length of " + contentLength + " bytes is unacceptably long. Rejecting it.");
                    } else if (contentLength == 0) {
                        return AdResponse.noAdAvailable();
                    } else {
                        //String markup = IoUtil.inputStreamToString(entity.getContent(), (int) contentLength);
                        return AdResponse.noAdAvailable();
                    }
                default:
                    //throw new IOException("Got HTTP response error: " + response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase());
            }
        } catch (IOException e) {
            
            return AdResponse.error(e.getMessage());
        }
        
        return AdResponse.success(uri);
    }
   

}