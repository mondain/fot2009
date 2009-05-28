package org.gregoire.demo;

import javax.servlet.ServletContext;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Main application.
 * 
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements	IStreamAwareScopeHandler {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "paramdemo");

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
