package org.red5.jira;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Used to test init and destroy bean attributes
 *  
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements
		ApplicationContextAware, IStreamAwareScopeHandler {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "shutdown");

	private static ApplicationContext applicationContext;
	
	private static boolean debug = false;
	
	public Application() {
		log.info("Started");
	}

	public void init() {
		log.info("Initializing");
	}

	public void destroy() {
		log.info("Destroying");
	}

	/**
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		Application.applicationContext = applicationContext;
		log.debug("setApplicationContext {}", Application.applicationContext);
	}

	/**
	 * @return
	 */
	public static boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		Application.debug = debug;
	}
	
}
