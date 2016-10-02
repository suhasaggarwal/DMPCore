
import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.maxmind.geoip.LookupService;

import net.sourceforge.wurfl.core.WURFLHolder;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;


public class MemcachedKeyCodeRepository {

    public static MemcachedClient memcache;

    private static MemcachedKeyCodeRepository INSTANCE=null;

	public static synchronized MemcachedKeyCodeRepository getInstance() {

		if(INSTANCE == null){
			  return new MemcachedKeyCodeRepository();
			 }
			  else
			 {
				  return INSTANCE ;
			 }

	}


    public Logger logger = Logger.getLogger(MemcachedKeyCodeRepository.class);

    public MemcachedKeyCodeRepository(){
        try {
            init();
        } catch (IOException ex) {
            logger.fatal(ex.getMessage(), ex);
        }

    }

    protected void init() throws IOException{
        memcache  = new MemcachedClient(AddrUtil.getAddresses("127.0.0.1:11211"));

    }


	public LookupService getGeoInfo(String key) {
        return (LookupService)memcache.get(key);
	}

	public String[][] getcampInfo(String key) {
        return (String[][])memcache.get(key);
	}

	public String[] getsiteInfo(String key){
		return (String[])memcache.get(key);

    }


	public WURFLHolder getwurfldata(String key){
		return (WURFLHolder)memcache.get(key);

    }


	public void putGeoInfo(String key, LookupService cl) {
		Future fs=memcache.set(key, 60*24*60, cl);
	}


    public void putcampInfo(String key, String[][] campInfo) {
	  Future fs=memcache.set(key, 60*24*60, campInfo);
	}

    public void putsiteInfo(String key, String[] siteInfo) {
		Future fs=memcache.set(key, 60*24*60, siteInfo);
	}

    public void putwurfldata(String key, WURFLHolder wurfldata) {
		Future fs=memcache.set(key, 60*24*60, wurfldata);
	}







    public void deletecampInfo(String key) {
		memcache.delete(key);
	}

    public void deletesiteInfo(String key) {
		memcache.delete(key);
	}



}