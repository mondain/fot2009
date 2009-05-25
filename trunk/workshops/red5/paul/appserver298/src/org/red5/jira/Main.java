package org.red5.jira;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.red5.server.WebScope;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

/**
 * Servlet implementation class 
 */
public class Main extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static Logger log;
	
	static {
		log = Application.loggerContext.getLogger(Main.class);
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print(test(request.getParameter("param")));
	}
	
	private boolean test(String param) {
		boolean result = false;
		
		Object[] params = null;
		
		if (param != null) {
			params = new Object[]{param};
		}
		
		
		try {
			//get the app context
			ApplicationContext appCtx = (ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
			log.debug("ApplicationContext: {}", appCtx);
			//get the web scope
			WebScope webScope = (WebScope) appCtx.getBean("web.scope");			
			log.info("WebScope: {}", webScope);
			
        	Iterator<String> it = webScope.getScopeNames();
        	for (;it.hasNext();) {
        		String name = it.next();
        		log.debug("Web scope children: {}", name);
        	}
        	it = null;

        	result = webScope.hasChildScope("test.scope");
        	if (result) {
        		log.info("Child scope: {}", webScope.getScope("test.scope"));
        	}
        	
        	//get the global scope
        	it = webScope.getParent().getScopeNames();
        	for (;it.hasNext();) {
        		String name = it.next();
        		log.debug("Global scope children: {}", name);
        	}
        	it = null;
        	
        } catch (Exception e) { 
        	log.error("Client call failed", e);
        } 
		
        return result;
	}
}
