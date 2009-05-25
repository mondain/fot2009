package org.gregoire.demo;

import javax.servlet.ServletContext;

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

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
	    loggerContext = selector.getLoggerContext("parameterdemo");
	    //get the logger for this class only
		log = loggerContext.getLogger(Application.class);
	}
	
	public String getParameter(String paramName) {
		//Grab a reference to the application context
		ApplicationContext appCtx = getContext().getApplicationContext();
		log.info("Got the application context: {}", appCtx);

		//Get the servlet context
		ServletContext ctx = ((XmlWebApplicationContext) appCtx).getServletContext();
		log.info("Got the servlet context: {}", ctx);

		//Get the context parameter
		String param = ctx.getInitParameter(paramName);	
		log.info("Got the parameter - {} = {}", paramName, param);
		
		return param;
	}
	
}
