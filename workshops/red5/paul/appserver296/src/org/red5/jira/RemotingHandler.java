package org.red5.jira;

import org.slf4j.Logger;

public class RemotingHandler {

	private static Logger log;
	
	static {
		log = Application.loggerContext.getLogger(RemotingHandler.class);
	}
	
	public static boolean test() {
		log.debug("Method test called");
		return true;
	}

	public static boolean test(Object o) {
		log.debug("Method test(Object) called with param: {}", o);
		return true;
	}
	
}
