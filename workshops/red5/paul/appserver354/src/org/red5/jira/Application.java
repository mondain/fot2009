package org.red5.jira;

import org.red5.compatibility.flex.messaging.messages.AsyncMessage;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;

/**
 * Main application.
 * 
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements	IStreamAwareScopeHandler {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "appserver354");

	public void sendMessage(String token, AsyncMessage msg) {
		log.debug("sendMessage - token: {} async msg: {}", token, msg);
	}

	public void sendMessage(Object[] params) {
		log.debug("sendMessage - token: {} async msg: {}", params[0], params[1]);
	}

}
