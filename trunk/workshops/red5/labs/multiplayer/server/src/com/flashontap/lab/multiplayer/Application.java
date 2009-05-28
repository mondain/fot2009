package com.flashontap.lab.multiplayer;

import java.util.Map;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.service.ServiceUtils;
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

	private static Logger log = Red5LoggerFactory.getLogger(Application.class,
			"multiplayer");

	private static ApplicationContext applicationContext;

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

	@Override
	public boolean connect(IConnection conn, IScope scope, Object[] params) {
		log.debug("Connect called");
		// check if the user passed valid parameters
		if (params == null || params.length == 0) {
			rejectClient("No parameters passed");
			return false;
		}

		log.debug("Params length: {}", params.length);
		// call original method of parent class
		if (!super.connect(conn, scope, params)) {
			return false;
		}

		// get the connections client id
		String clientId = conn.getClient().getId();
		log.debug("Client id: {}", clientId);
		log.warn("Client id {} already exists in connection map", clientId);
		if (!ServiceUtils.invokeOnConnection(conn, "setClientId",
				new Object[] { clientId })) {
			log.info("Client call to setClientId failed");
		}
		return true;
	}

	public boolean appConnect(IConnection conn, Object[] params) {
		log.debug("appConnect - client id: {}", conn.getClient().getId());
		if (log.isDebugEnabled()) {
			// getting client parameters
			Map<String, Object> properties = conn.getConnectParams();
			for (Map.Entry<String, Object> e : properties.entrySet()) {
				log.debug("{} = {}", e.getKey(), e.getValue());
			}
		}
		return true;
	}

	@Override
	public void disconnect(IConnection conn, IScope scope) {
		log.debug("Disconnect called");
		// get the previously stored id
		String clientId = conn.getClient().getId();
		// Call original method of parent class
		super.disconnect(conn, scope);
		// remove all conn attributes
		conn.removeAttributes();
	}

}
