package org.red5.jira;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;

/**
 * Implements the special spring shutdown bean interface.
 *  
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class TestDestroy implements DisposableBean {

	private static Logger log = Red5LoggerFactory.getLogger(TestDestroy.class, "shutdown");
	
	/**
	 * Used to cleanly stop all the listener notification threads.
	 */	
	@Override
	public void destroy() throws Exception {
        log.info("Destroy fired");
	}
	
}
