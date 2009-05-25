package org.red5.jira;

import java.util.Iterator;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IScope;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
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
		
	static {
	    ContextSelector selector = StaticLoggerBinder.SINGLETON.getContextSelector();
	    //get the logger context for the servlet / app context
	    loggerContext = selector.getLoggerContext("appserver298");
	    //get the logger for this class only
		log = loggerContext.getLogger(Application.class);
	}
	
	@Override
	public boolean start(IScope scope) {
		log.debug("start: {}", scope);

		return super.start(scope);
	}
	
	@Override
	public boolean appStart(IScope app) {

		log.debug("appStart: {}", app);
		
		//list scopes
    	Iterator<String> it = scope.getScopeNames();
    	for (;it.hasNext();) {
    		String name = it.next();
    		log.debug("Scope: {}", name);
    	}
		
        try { 
        	//create a scope
        	if (createChildScope("test.scope")) {
	        	//get the scope we created
	        	IScope child = scope.getScope("test.scope");
	        	log.info("Child scope: {}", child);
        	} else {
        		log.warn("Child scope was not created");
        	}
        } catch (Exception e) { 
        	log.error("", e);
        } 
        			
		return super.appStart(app);
	}

	public boolean test() {
		boolean result = false;
		
		return result;
	}
		
	public boolean test(String str) {
		boolean result = false;
		
		Object[] params = null;
		
		if (str != null) {
			params = new Object[]{str};
		}
		
        try { 

        	result = true;
        } catch (Exception e) { 
        	log.error("Client call failed", e);
        } 
        	
		return result;
	}

}
