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
	
	public void setObject(Foo fooIn) {
		log.debug("Set object: {}", fooIn);
		this.foo = fooIn;
	}

	public Foo getObject() {
		log.debug("Get object with no params");
		Foo fooOut = null;

		//if we have a foo already set then return it
		if (foo != null) {
			fooOut = foo;
		} else {
			//create a new foo
			fooOut = new Foo();
			fooOut.setArray(new Object[]{"test1", "test2", "test3"});
			fooOut.setId(42);
			fooOut.setName("testfoo");
			fooOut.setReady(true);
		}

		return fooOut;
	}
	
	public Foo getObject(Foo foo2) {
		log.debug("Get object: {}", foo2);
		return foo2;
	}	
	
}
