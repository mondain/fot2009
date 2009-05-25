package org.red5.jira;

/*
 * RED5 Open Source Flash Server - http://www.osflash.org/red5
 * 
 * Copyright (c) 2006-2007 by respective authors (see below). All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or modify it under the 
 * terms of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either version 2.1 of the License, or (at your option) any later 
 * version. 
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along 
 * with this library; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 */

import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.service.ServiceUtils;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;

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

	private static Foo foo;
	
	static {
	    ContextSelector selector = StaticLoggerBinder.SINGLETON.getContextSelector();
	    //get the logger context for the servlet / app context
	    loggerContext = selector.getLoggerContext("appserver283");
	    //get the logger for this class only
		log = loggerContext.getLogger(Application.class);
	}
	
	@Override
	public boolean connect(IConnection conn, IScope scope, Object[] params) {
		log.debug("Connect called");
		// call original method of parent class
		if (!super.connect(conn, scope, params)) {
			return false;
		}	
		// get the connections client id
		String clientId = conn.getClient().getId();
		log.debug("Client id: {}", clientId);
		if (!ServiceUtils.invokeOnConnection(conn, "setClientId", new Object[] { clientId })) {
			log.info("Client call to setClientId failed");
		}
		return true;
	}    
    
	public Foo getObject(Object nullObject) {
		return getObject();
	}
	
	/**
	 * Sends back a Foo
	 * 
	 * @return
	 */
	public Foo getObject() {
		log.debug("getObject called");	

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
	
	/**
	 * Receives a Foo
	 * 
	 * @param foo
	 */
	public void setObject(Foo fooIn) {
		log.debug("setObject called - foo: {}", fooIn);	
		foo = fooIn;
	}

}
