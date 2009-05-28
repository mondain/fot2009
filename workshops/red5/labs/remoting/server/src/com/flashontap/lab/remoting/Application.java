package com.flashontap.lab.remoting;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.flashontap.Foo;

/**
 * Simple ApplicationAdapter
 *  
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements
		ApplicationContextAware, IStreamAwareScopeHandler {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "remoting");

	private static ApplicationContext applicationContext;
	
	private Foo foo;
	
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
	
	public void setObject(Foo foo) {
		log.debug("Set object: {}", foo);
		this.foo = foo;
	}

	public Foo getObject() {
		log.debug("Get object with no params");
		return foo;
	}
	
	public Foo getObject(Foo foo2) {
		log.debug("Get object: {}", foo2);
		return foo2;
	}	
	
}
