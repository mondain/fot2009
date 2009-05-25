package org.red5.jira;

import org.red5.logging.Red5LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * Implements the special spring init bean interface.
 *  
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class TestInit implements InitializingBean {

	private static Logger log = Red5LoggerFactory.getLogger(TestInit.class, "shutdown");

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("afterPropertiesSet fired");	
	}
	
}
	

