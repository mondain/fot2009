package org.red5.jira;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.red5.server.net.remoting.RemotingClient;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.selector.ContextSelector;

/**
 * Main application.
 * 
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements	IStreamAwareScopeHandler {

	private static Logger log;
	
	//provide the logger context for other parts of the app
	public static LoggerContext loggerContext;
	
	private static String url = "http://localhost/appserver296/";
	
	static {
	    ContextSelector selector = StaticLoggerBinder.SINGLETON.getContextSelector();
	    //get the logger context for the servlet / app context
	    loggerContext = selector.getLoggerContext("appserver296");
	    //get the logger for this class only
		log = loggerContext.getLogger(Application.class);
	}
	
	public boolean test() {
		boolean result = false;
		
        try { 
        	RemotingClient client = new RemotingClient(url); 
        	log.info("Client: {}", client);
        	log.info("Remoting handler response: {}", client.invokeMethod("remotinghandler.test", null).getClass()); 

        	result = true;
        } catch (Exception e) { 
        	log.error("Client call failed", e);
        } 
        	
		return result;
	}
		
	public boolean test(String str) {
		boolean result = false;
		
		Object[] params = null;
		
		if (str != null) {
			params = new Object[]{str};
		}
		
        try { 
        	RemotingClient client = new RemotingClient(url); 
        	log.info("Client: {}", client);
        	log.info("Remoting handler response: {}", client.invokeMethod("remotinghandler.test", params).getClass()); 

        	result = true;
        } catch (Exception e) { 
        	log.error("Client call failed", e);
        } 
        	
		return result;
	}

	public static String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		Application.url = url;
	}	
	
}
