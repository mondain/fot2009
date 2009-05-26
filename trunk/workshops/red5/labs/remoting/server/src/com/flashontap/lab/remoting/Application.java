package com.flashontap.lab.remoting;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Simple ApplicationAdapter
 *  
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements
		ApplicationContextAware, IStreamAwareScopeHandler {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "remoting");

	private static ApplicationContext applicationContext;
	
	private static boolean debug = false;
	
	public Application() {
		log.info("Instanced");
	}

	/**
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		Application.applicationContext = applicationContext;
		log.debug("setApplicationContext {}", Application.applicationContext);
	}
	
}
